package game;

import graphics.CharacterAnimation;
import graphics.FlyingBird;
import graphics.FlyingPlane;
import graphics.Level;
import graphics.LevelOne;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import javax.swing.JPanel;

import networking.Client;
import networking.Networking;
import notifications.ConnectNotification;
import notifications.DeathNotification;
import notifications.DisconnectNotification;
import notifications.NotificationHandler;
import particle.ParticleBatcher;
import Music.SoundEffect;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	public static final int GROUNDLEVEL = 510;
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 600;
	public static final int LEVEL_WIDTH = 8100;
	public static final int LEVEL_HEIGHT = 900;

	private static final Font NAME_FONT = new Font("Names", Font.BOLD, 12);

	private String identification;
	private HashMap<String, Character> characters;
	private HashMap<String, String> playerNames;
	private NotificationHandler notifications;
	private Level level;
	private ParticleBatcher pb;

	private FlyingPlane flyingPlane;
	private FlyingBird flyingBird;
	private Character player;

	private SoundEffect deathEffect;

	public HakkStage() {
		super();
		level = new LevelOne();
		characters = new HashMap<String, Character>();
		playerNames = new HashMap<String, String>();
		pb = new ParticleBatcher();
		notifications = new NotificationHandler();

		flyingPlane = new FlyingPlane(-50, -500);
		flyingBird = new FlyingBird(920, -300);

		// deathEffect = new SoundEffect("bgm/scream.mp3");
		// deathEffect.start();
	}

	@Override
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setFont(NAME_FONT);
		level.drawBackground(g2d);
		level.drawGround(g2d);
		int xOffset = level.getXOffset();
		int yOffset = level.getYOffset();
		flyingPlane.drawPlane(g2d, yOffset, yOffset);
		flyingBird.drawBird(g2d, xOffset, yOffset);
		level.drawPlatforms(g2d, xOffset, yOffset);
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d, xOffset, yOffset);
		}
		pb.draw(g2d, xOffset, yOffset);
		level.drawForeground(g2d);
		notifications.draw(g2d);

		g2d.dispose();
	}

	public synchronized void doPhysics() {
		flyingPlane.move();
		flyingBird.move();
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics(level);
		}
		level.computeOffset(player.charState.x, player.charState.y);
		pb.update();
		notifications.update();
		pb.doRain(level.getXOffset());
	}

	public synchronized void addCharacter(String address, Character character) {
		characters.put(address, character);
	}

	public synchronized void addPlayerCharacter(String address,
			Player playerCharacter) {
		identification = address;
		this.player = playerCharacter;
		System.out.println(player.toString());
		characters.put(address, playerCharacter);

	}

	// public synchronized void addSword(String address, SwordState sword) {
	// swords.put(address, sword);
	// }

	public boolean addName(String address, String name) {
		playerNames.put(address, name);
		Character character = characters.get(address);
		if (character != null) {
			characters.get(address).rename(name);
			return false;
		}
		return true;
	}

	public void update(Client client) {
		client.send(player.charState.toString() + Networking.SEPARATOR_SWORD
				+ player.getSwordState().toString());
		String clientUpdate = client.getUpdate();
		// System.out.println("Update from server: " + clientUpdate);
		String[] statesAndMsgs = clientUpdate
				.split(Networking.SEPARATOR_MESSAGES);
		if (statesAndMsgs.length > 1)
			readMessages(statesAndMsgs[1]);

		String[] chStsAndSwSts = statesAndMsgs[0]
				.split(Networking.SEPARATOR_SWORD);

		for (String ipState : chStsAndSwSts[0]
				.split(Networking.SEPARATOR_PLAYER)) {
			String[] ipAndState = ipState.split(Networking.SEPARATOR_STATE);
			Character character = getCharacter(ipAndState[0]);
			if (!ipAndState[0].equals(client.getAddress())) {
				character.setState(new CharacterState(ipAndState[1]));
			}
		}

		for (String ipState : chStsAndSwSts[1]
				.split(Networking.SEPARATOR_PLAYER)) {
			String[] ipAndState = ipState.split(Networking.SEPARATOR_STATE);
			// Character character = getCharacter(ipAndState[0]);

			Character character = characters.get(ipAndState[0]);
			if (character != null) {
				if (!ipAndState[0].equals(client.getAddress())) {
					character.swordState = new SwordState(ipAndState[1]);
				}
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
					if (addName(attributes[0], attributes[1])) {
						notifications
								.put(new ConnectNotification(attributes[1]));
					}
					getCharacter(attributes[0]).charAnimation = new CharacterAnimation(
							attributes[2]);
				} else if (typeAndData[0].equals(Networking.MESSAGE_DEATH)) {
					if (attributes[0].equals(identification))
						characters.get(identification).charState.reSpawn();
					double x = Double.parseDouble(attributes[2])
							+ CharacterAnimation.getImage(
									characters.get(attributes[0]).charAnimation
											.getCurrentImageName()).getWidth(
									null) / 2;
					double y = Double.parseDouble(attributes[3])
							- CharacterAnimation.getImage(
									characters.get(attributes[0]).charAnimation
											.getCurrentImageName()).getHeight(
									null) / 2;
					pb.doDeath(x, y);
					notifications
							.put(new DeathNotification(playerNames
									.get(attributes[0]), playerNames
									.get(attributes[1])));

					// deathEffect.notifyPlayer();

				} else if (typeAndData[0].equals(Networking.MESSAGE_DISCONNECT)) {
					disconnect = true;
					keyset.remove(attributes[0]);
				}
			}
			if (disconnect)
				for (String key : keyset) {
					notifications.put(new DisconnectNotification(playerNames
							.get(key)));
					removeCharacter(key);
				}
		}
	}

}
