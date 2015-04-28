package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JPanel;

import networking.Client;
import networking.Networking;
import particle.ParticleBatcher;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	public static final int GROUNDLEVEL = 293;

	private HashMap<String, Character> characters;
	private HashMap<String, Sword> swords;
	private HashMap<String, String> playerNames;
	private ParticleBatcher pb;

	public HakkStage() {
		super();
		characters = new HashMap<String, Character>();
		swords = new HashMap<String, Sword>();
		playerNames = new HashMap<String, String>();
		pb = new ParticleBatcher();
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
		pb.draw(g);
		g2d.fillRect(0, GROUNDLEVEL, this.getWidth(), this.getHeight()
				- GROUNDLEVEL);
		g2d.dispose();
	}

	public synchronized void doPhysics() {
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics();
		}
		pb.update();
	}

	public synchronized void addCharacter(String address, Character character) {
		characters.put(address.trim(), character);
	}

	public synchronized void addSword(String address, Sword sword) {
		swords.put(address, sword);
	}

	public void addName(String address, String name) {
		playerNames.put(address, name);
		Character character = characters.get(address);
		if (character != null) {
			characters.get(address).rename(name);
		}
	}

	public void update(Client client) {
		HashSet<String> keyset = new HashSet<String>(characters.keySet());

		client.send(characters.get(client.getAddress()).state.toString()
				+ Networking.SEPARATOR_SWORD
				+ swords.get(client.getAddress()).toString());
		String clientUpdate = client.getUpdate();
		// System.out.println("Update from server: " + clientUpdate);
		String[] gup = clientUpdate.split(Networking.SEPARATOR_MESSAGE);
		if (!gup[1].trim().equals(""))
			readMessages(gup[1].trim());
		for (String ent : gup[0].split(Networking.SEPARATOR_PLAYER)) {
			String[] splatEnt = ent.split(Networking.SEPARATOR_STATE);
			keyset.remove(splatEnt[0].trim());
			Character character = getCharacter(splatEnt[0]);

			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
//		for (String key : keyset) {
//			if (removeCharacter(key)) {
//				playerNames.remove(key);
//				System.out.println("Removing player " + key);
//			}
//		}
	}

	private synchronized Character getCharacter(String identification) {
		Character character = characters.get(identification);
		if (character == null) {
			String name = playerNames.get(identification);
			if (name == null)
				name = "n00b";
			character = new Opponent(this, name);
			addCharacter(identification, character);
		}
		return character;
	}

	private synchronized boolean removeCharacter(String key) {
		return characters.remove(key) != null;
	}

	private void readMessages(String messages) {
		System.out.println("readMessages: " + messages);
		for (String msg : messages.split(Networking.SEPARATOR_PLAYER)) {
			String[] splatMsg = msg.split(Networking.SEPARATOR_STATE);
			if (splatMsg[0].equals(Networking.MESSAGE_NEWPLAYER)) {
				addName(splatMsg[1].trim(), splatMsg[2]);
				getCharacter(splatMsg[1]).animation = new CharacterAnimation(
						splatMsg[3]);
			} else if(splatMsg[0].equals(Networking.MESSAGE_DISCONNECT)) {
				removeCharacter(splatMsg[1].trim());
			}
		}
	}
}
