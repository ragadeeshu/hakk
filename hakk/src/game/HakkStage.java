package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javafx.scene.shape.Rectangle;

import javax.swing.JPanel;

import networking.Client;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	private HashMap<String, Character> characters;
	private HashMap<String, Sword> swords;

	public HakkStage() {
		super();
		characters = new HashMap<String, Character>();
		swords = new HashMap<String, Sword>();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d);
		}
		for (Sword sword : swords.values()){
			sword.draw(g2d);
		}
		int height = 293;
	    g2d.fillRect(0, height, this.getWidth(), this.getHeight()-height);
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

	public synchronized void update(Client client) {
		client.send(characters.get(client.getAddress()).state.toString()+"&"+swords.get(client.getAddress()).toString());
		String gup = client.getUpdate();
		for (String ent : gup.split(";")) {
			String[] splatEnt = ent.split("%");
			Character character = characters.get(splatEnt[0]);
			if (character == null) {
				character = new Opponent(this);
				characters.put(splatEnt[0], character);
			}
			if (!splatEnt[0].equals(client.getAddress())) {
				character.setState(new CharacterState(splatEnt[1]));
			}

		}

	}
}
