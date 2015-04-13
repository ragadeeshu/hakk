package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.JPanel;

import networking.Client;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	private HashMap<String, Character> characters;

	public HakkStage() {
		super();
		characters = new HashMap<String, Character>();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d);
		}

	}

	public void doPhysics() {
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics();
		}
	}

	public void add(String address, Character character) {
		characters.put(address, character);

	}

	public void update(Client client) {
		client.send(characters.get(client.getAddress()).state);
		for(Entry<String, CharacterState> ent:
			client.getUpdate().entrySet()){
			characters.get(ent.getKey()).setState(ent.getValue());
			
		}
				
	}
}
