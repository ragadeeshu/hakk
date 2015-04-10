package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class HakkStage extends JPanel {
	private ArrayList<Character> characters;

	public HakkStage() {
		super();
		characters = new ArrayList<Character>();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		for (Character character : characters) {
			character.draw(g2d);
		}

	}

	public void doPhysics() {
		for (Character character : characters) {
			character.doPhysics();
		}
	}

	public void add(Character character) {
		characters.add(character);

	}

}
