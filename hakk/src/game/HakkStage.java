package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import networking.Client;
import networking.Networking;
import particle.ParticleBatcher;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	public static final int GROUNDLEVEL = 293;

	private String currentImage;
	private String identification;
	private BufferedImage background;
	private BufferedImage ground;
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
		currentImage = "background.png";
		
		try {
			String name = "sprites/" + currentImage;
			background = ImageIO.read(new File(name));
			ground = ImageIO.read(new File("sprites/ground.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(background, 0, 0, null);
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d);
		}
		for (Sword sword : swords.values()) {
			sword.draw(g2d);
		}
		pb.draw(g);
		g2d.drawImage(ground, 0, GROUNDLEVEL, null);
		
		
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
		identification = client.getAddress();
		// HashSet<String> keyset = new HashSet<String>(characters.keySet());

		client.send(characters.get(client.getAddress()).state.toString()
				+ Networking.SEPARATOR_SWORD
				+ swords.get(client.getAddress()).toString());
		String clientUpdate = client.getUpdate();
		// System.out.println("Update from server: " + clientUpdate);
		String[] gup = clientUpdate.split(Networking.SEPARATOR_MESSAGES);
		if (!gup[1].trim().equals(""))
			readMessages(gup[1].trim());
		for (String ent : gup[0].split(Networking.SEPARATOR_PLAYER)) {
			String[] splatEnt = ent.split(Networking.SEPARATOR_STATE);
			// keyset.remove(splatEnt[0].trim());
			Character character = getCharacter(splatEnt[0]);

			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
		// for (String key : keyset) {
		// if (removeCharacter(key)) {
		// playerNames.remove(key);
		// System.out.println("Removing player " + key);
		// }
		// }
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
		boolean disconnect = false;
		System.out.println("readMessages: " + messages);
		for (String msg : messages.split(Networking.SEPARATOR_MESSAGE)) {
			String[] typeAndData = msg.split(Networking.SEPARATOR_STATE);

			HashSet<String> keyset = new HashSet<String>(characters.keySet());

			for (String player : typeAndData[1]
					.split(Networking.SEPARATOR_PLAYER)) {

				String[] attributes = player
						.split(Networking.SEPARATOR_ATTRIBUTE);

				if (typeAndData[0].equals(Networking.MESSAGE_NEWPLAYER)) {
					addName(attributes[0].trim(), attributes[1]);
					getCharacter(attributes[0].trim()).animation = new CharacterAnimation(
							attributes[2]);
				}else if(typeAndData[0].equals(Networking.MESSAGE_DEATH)){
					if(attributes[0].equals(identification))
						characters.get(identification).state.reSpawn();
					pb.doDeath(Double.parseDouble(attributes[1]), Double.parseDouble(attributes[2]));

				}else if (typeAndData[0].equals(Networking.MESSAGE_DISCONNECT)) {
					disconnect = true;
					keyset.remove(attributes[0].trim());
				}
			}
			if (disconnect)
				for (String key : keyset)
					removeCharacter(key);
		}
	}
}
