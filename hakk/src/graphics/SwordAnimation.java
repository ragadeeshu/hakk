package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SwordAnimation {
	public static final int NUM_ATTACK = 29;
	public static String[] BASENAMES = { "sword_attacking_left___" };
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

			for (int j = 0; j < NUM_ATTACK; j++) {
				// System.out.println("sprites"+File.pathSeparator+baseName+"runni__"+String.format("%03d",
				// i)+".png");
				try {
					String name = "sprites/" + BASENAMES[i] + String.format("%03d", j) + ".png";
					img = ImageIO.read(new File(name));
				} catch (IOException e) {
					e.printStackTrace();
				}
				IMAGES.put(BASENAMES[i] + String.format("%03d", j)
						+ ".png", img);
			}
		}
	}

	public SwordAnimation(String baseName) {
		this.baseName = baseName;
		current = 0;
	}

	public void swing() {
		current = (current + 1) % NUM_ATTACK;
	}

	public String getCurrentImageName() {
		return baseName + String.format("%03d.png", current);
	}

	public static Image getImage(String imgName) {
		if (IMAGES.get(imgName) == null) {
			System.out.println(imgName);
			System.out.println(IMAGES.keySet());
		}
		return IMAGES.get(imgName);
	}

}
