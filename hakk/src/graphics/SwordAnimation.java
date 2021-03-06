package graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SwordAnimation {
	public static final int NUM_ATTACK = 16;
	public static String[] BASENAMES = { "sword_swing_right__",
			"sword_swing_left__" };
	private static HashMap<String, Image> IMAGES;
	private int current;

	public boolean doingSwing = false;
	public int swingOrigin = 0;
	public int left = 0;

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
					String name = "sprites/" + BASENAMES[i]
							+ String.format("%03d", j) + ".png";
					img = ImageIO.read(SwordAnimation.class.getResource("/resources/" + name));
				} catch (IOException e) {
					e.printStackTrace();
				}
				IMAGES.put(BASENAMES[i] + String.format("%03d", j) + ".png",
						img);
			}
		}
	}

	public SwordAnimation(String baseName) {
		current = 0;
	}

	public void animate() {
		// switch()

	}

	public void rotateLeft() {
		if (left == 0) {
			if (--current < 0)
				current += NUM_ATTACK;
		} else
			current = (current + 1) % NUM_ATTACK;
	}

	public void rotateRight() {
		if (left == 0)
			current = (current + 1) % NUM_ATTACK;
		else if (--current < 0)
			current += NUM_ATTACK;
	}

	public void swing() {
		doingSwing = true;
		swingOrigin = current;
	}

	public String getCurrentImageName() {
		if (doingSwing)
			current = (current + 1) % NUM_ATTACK;
		if (current == swingOrigin)
			doingSwing = false;
		return BASENAMES[left] + String.format("%03d.png", current);
	}

	public static Image getImage(String imgName) {
		if (IMAGES.get(imgName) == null) {
			System.out.println(imgName);
			System.out.println(IMAGES.keySet());
		}
		return IMAGES.get(imgName);
	}

}
