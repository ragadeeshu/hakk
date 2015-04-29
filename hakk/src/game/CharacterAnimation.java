package game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class CharacterAnimation {
	public static final int GROUND_OFFSET = 6;
	public static final int NUM_RUNNING = 11;
	public static String[] BASENAMES = { "player", "player2", "player3",
			"player4" };
	private static HashMap<String, Image> IMAGES;
	private int current;
	private String baseName;

	// public static BufferedImage getImage(String name) {
	// return bitmasks.get(name);
	// }
	static {

		IMAGES = new HashMap<String, Image>();
		BufferedImage img = null;
		for (int i = 0; i < BASENAMES.length; i++) {

			for (int j = 0; j < NUM_RUNNING; j++) {
				// System.out.println("sprites"+File.pathSeparator+baseName+"runni__"+String.format("%03d",
				// i)+".png");
				try {
					String name = "sprites/" + BASENAMES[i] + "runni__"
							+ String.format("%03d", j) + ".png";
					img = ImageIO.read(new File(name));
				} catch (IOException e) {
					e.printStackTrace();
				}
				IMAGES.put(BASENAMES[i] + "runni__" + String.format("%03d", j)
						+ ".png", img);
			}
		}
	}

	public CharacterAnimation(String baseName) {
		this.baseName = baseName;
		current = 0;
	}

	public void run() {
		current = (current + 1) % NUM_RUNNING;
	}

	public String getCurrentImageName() {
		return baseName + String.format("runni__%03d.png", current);
	}

	public static Image getImage(String imgName) {
		if (IMAGES.get(imgName) == null) {
			System.out.println(imgName);
			System.out.println(IMAGES.keySet());
		}
		return IMAGES.get(imgName);
	}

}
