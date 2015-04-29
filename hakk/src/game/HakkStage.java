package game;

import graphics.CharacterAnimation;
import graphics.FlyingBird;
import graphics.FlyingPlane;
import graphics.Level;
import graphics.LevelOne;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
	public static final int GROUNDLEVEL = 500;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;

	private String identification;
	private HashMap<String, Character> characters;
	private HashMap<String, String> playerNames;
	private Level level;
	private ArrayList<Platform> platforms;
	private ParticleBatcher pb;

	private FlyingPlane flyingPlane;
	private FlyingBird flyingBird;
	private Character player;

	public HakkStage() {
		super();
		level= new LevelOne();
		characters = new HashMap<String, Character>();
		playerNames = new HashMap<String, String>();
		pb = new ParticleBatcher();
	
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(200, GROUNDLEVEL - 160));
		platforms.add(new Platform(500, GROUNDLEVEL - 200));

		flyingPlane = new FlyingPlane(-50, -500);
		flyingBird = new FlyingBird(920, -300);
	}

	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		level.drawBackground(g2d);
		flyingPlane.drawPlane(g2d);
		flyingBird.drawBird(g2d);
		level.drawGround(g2d);
		int offset = level.getXOffset();
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d, offset);
		}
		// for (SwordState sword : swords.values()) {
		// sword.draw(g2d);
		// }
		for (Platform platform : platforms) {
			platform.draw(g2d);
		}
		pb.draw(g);
		level.drawForeground(g2d);
		
		g2d.dispose();
	}

	public synchronized void doPhysics() {
		flyingPlane.move();
		flyingBird.move();
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics(platforms);
		}
		pb.update();
		level.computeOffset(player.charState.x, player.charState.y);
	}

	public synchronized void addCharacter(String address, Character character) {
		characters.put(address.trim(), character);
	}
	public synchronized void addPlayerCharacter(String address, Player player) {
		identification =address.trim();
		this.player = player;
		characters.put(address.trim(), player);
		
	}

//	public synchronized void addSword(String address, SwordState sword) {
//		swords.put(address, sword);
//	}

	public void addName(String address, String name) {
		playerNames.put(address, name);
		Character character = characters.get(address);
		if (character != null) {
			characters.get(address).rename(name);
		}
	}

	public void update(Client client) {
		client.send(player.charState.toString() + Networking.SEPARATOR_SWORD
				+ player.getSwordState().toString());
		String clientUpdate = client.getUpdate();
		// System.out.println("Update from server: " + clientUpdate);
		String[] gup = clientUpdate.split(Networking.SEPARATOR_MESSAGES);
		if (!gup[1].trim().equals(""))
			readMessages(gup[1].trim());
		for (String ent : gup[0].split(Networking.SEPARATOR_PLAYER)) {
			String[] splatEnt = ent.split(Networking.SEPARATOR_STATE);
			Character character = getCharacter(splatEnt[0]);

			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
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
					getCharacter(attributes[0].trim()).charAnimation = new CharacterAnimation(
							attributes[2]);
				} else if (typeAndData[0].equals(Networking.MESSAGE_DEATH)) {
					if (attributes[0].equals(identification))
						characters.get(identification).charState.reSpawn();
					double x = Double.parseDouble(attributes[1])
							+ CharacterAnimation
									.getImage(
											characters.get(identification).charAnimation
													.getCurrentImageName())
									.getWidth(null) / 2;
					double y = Double.parseDouble(attributes[2])
							- CharacterAnimation
									.getImage(
											characters.get(identification).charAnimation
													.getCurrentImageName())
									.getHeight(null) / 2;
					pb.doDeath(x, y);

				} else if (typeAndData[0].equals(Networking.MESSAGE_DISCONNECT)) {
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
