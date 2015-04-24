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
	public void paint(Graphics g) {
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
		characters.put(address, character);
	}

	public synchronized void addSword(String address, Sword sword) {
		swords.put(address, sword);
	}

	public synchronized void addName(String address, String name) {
		playerNames.put(address, name);
	}

	public synchronized void update(Client client) {
		HashSet<String> keyset = new HashSet<String>(characters.keySet());
		client.send(characters.get(client.getAddress()).state.toString() + "&"
				+ swords.get(client.getAddress()).toString());
		String gup = client.getUpdate();
		for (String ent : gup.split(";")) {
			String[] splatEnt = ent.split("%");

			keyset.remove(splatEnt[0]);
//			System.out.println("splat" + splatEnt[0]);

			Character character = characters.get(splatEnt[0]);
			if (character == null) {
				// client.send(Networking.REQUEST_NAME);
				// String name = client.getUpdate().trim();
				character = new Opponent(this, "Opponent");
				characters.put(splatEnt[0], character);
				playerNames.put(splatEnt[0], "Opponent");
			}
			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
		for (String key : keyset) {
			System.out.println("Removing player " + key);
			characters.remove(key);
		}
	}
}
