package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PlayerAnimation {
	private static final int NUM_RUNNING = 11;
	private ArrayList<Image> running;
	private int current;

	public PlayerAnimation() {
		running = new ArrayList<Image>();
		BufferedImage img = null;
		for (int i = 0; i < NUM_RUNNING; i++) {

			try {
				System.out.println("sprites"+File.pathSeparator+"playerrunni__"+String.format("%03d", i)+".png");
				img = ImageIO.read(new File("sprites/playerrunni__"+String.format("%03d", i)+".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			running.add(img);
		}
		current = 0;
	}

	public void run() {
		current = (current + 1) % NUM_RUNNING;
	}

	public Image getImage() {
		return running.get(current);
	}

}
