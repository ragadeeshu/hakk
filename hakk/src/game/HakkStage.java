package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

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
	public synchronized void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().draw(g2d);
		}

	}

	public synchronized void doPhysics() {
		for (Entry<String, Character> character : characters.entrySet()) {
			character.getValue().doPhysics();
		}
	}

	public synchronized void add(String address, Character character) {
		characters.put(address, character);

	}

	public synchronized void update(Client client) {
		HashSet<String> keyset = new HashSet(characters.keySet());
		client.send(characters.get(client.getAddress()).state.toString());
		String gup = client.getUpdate();
//		System.out.println("GUP: " + gup);
		for (String ent : gup.split(";")) {
//			System.out.println("ENT: " + ent);
			String[] splatEnt = ent.split("%");
			
			keyset.remove(splatEnt[0]);

			Character character = characters.get(splatEnt[0]);
			if (character == null) {
				character = new Opponent(this);
				characters.put(splatEnt[0], character);
			}
			// System.out.println("updated character "+ent.getKey());
//			System.out.println(splatEnt[0]);
//			System.out.println(client.getAddress());
			if (!splatEnt[0].equals(client.getAddress())) {
//				System.out.println("Not ignoring");
				character.setState(new CharacterState(splatEnt[1]));
			}
		}
		for(String key : keyset){
			System.out.println("Removing player " + key);
			characters.remove(key);
		}
	}
}
