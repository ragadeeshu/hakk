package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import networking.Client;
import networking.Networking;
import networking.Server;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	private HashMap<String, Character> characters;
	private HashMap<String, Sword> swords;
	private HashMap<String, String> playerNames;

	public HakkStage() {
		super();
		characters = new HashMap<String, Character>();
		swords = new HashMap<String, Sword>();
		playerNames = new HashMap<String, String>();
	}

	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d);
		}
		for (Sword sword : swords.values()) {
			sword.draw(g2d);
		}
		int height = 293;
		g2d.fillRect(0, height, this.getWidth(), this.getHeight() - height);
	}

	public synchronized void doPhysics() {
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics();
		}
	}

	public synchronized void addCharacter(String address, Character character) {
		characters.put(address.trim(), character);
	}

	public synchronized void addSword(String address, Sword sword) {
		swords.put(address, sword);
	}

	public void addName(String address, String name) {
		System.out.println("HURRDUURR");
		playerNames.put(address, name);
		Character character = characters.get(address);
		if (character != null) {
			characters.get(address).rename(name);
		}
	}

	public void update(Client client) {
		// HashSet<String> keyset = new HashSet<String>(characters.keySet());
		HashSet<String> keyset = new HashSet<String>();
		for (String key : characters.keySet()) {
			keyset.add(key.trim());
		}

		client.send(characters.get(client.getAddress()).state.toString()
				+ Networking.SEPARATOR_SWORD
				+ swords.get(client.getAddress()).toString());
		String clientUpdate = client.getUpdate();
		// System.out.println("Update from server: " + clientUpdate);
		String[] gup = clientUpdate.split(Networking.SEPARATOR_MESSAGE);
		// System.out.println("Got from Server, main:" + gup[0]);
		// if (gup.length > 1)
		if (!gup[1].trim().equals(""))
			readMessages(gup[1].trim());
		// System.out.println("Message:" + gup[1]);
		for (String ent : gup[0].split(Networking.SEPARATOR_PLAYER)) {
			// System.out.println("got ent:" + ent);
			String[] splatEnt = ent.split(Networking.SEPARATOR_STATE);

			keyset.remove(splatEnt[0].trim());
			// System.out.println("splat" + splatEnt[0]);

			Character character = characters.get(splatEnt[0]);
			if (character == null) {
				// client.send(Networking.REQUEST_NAME);
				// String name = client.getUpdate().trim();
				String name = playerNames.get(splatEnt[0]);
				if (name == null)
					name = "n00b";
				character = new Opponent(this, name);
				addCharacter(splatEnt[0], character);
				// playerNames.put(splatEnt[0], "Opponent");
			}
			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
		for (String key : keyset) {
			if (removeCharacter(key)){
				playerNames.remove(key);
				System.out.println("Removing player " + key);
			}
		}
	}

	private synchronized boolean removeCharacter(String key) {
		return characters.remove(key) != null;
	}

	private void readMessages(String string) {
		System.out.println("." + string + ".");
		for (String msg : string.split(Networking.SEPARATOR_PLAYER)) {
			System.out.println(msg);
			String[] splatMsg = msg.split(Networking.SEPARATOR_STATE);
			if (splatMsg[0].equals(Networking.MESSAGE_NAME)) {
				System.out.println("adress =" + splatMsg[1]);
				addName(splatMsg[1].trim(), splatMsg[2]);
			}
		}
	}
}
