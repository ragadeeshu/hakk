package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PlayerAnimation {
	private ArrayList<Image> running;

	public PlayerAnimation() {
		running = new ArrayList<Image>();
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("sprites/playerrunni__000.png"));
		} catch (IOException e) {
		}
		running.add(img);
	}

	public void run() {

	}

	public Image getImage() {
		return running.get(0);
	}

}
