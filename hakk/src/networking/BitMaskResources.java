package networking;

import graphics.CharacterAnimation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public abstract class BitMaskResources {
	private static HashMap<String, BitMask> bitmasks;

	static {
		bitmasks = new HashMap<>();
		BufferedImage img = null;
		for (int i = 0; i < CharacterAnimation.BASENAMES.length; i++) {
			for (int j = 0; j < CharacterAnimation.NUM_RUNNING; j++) {
				try {
					String name = "sprites/" + CharacterAnimation.BASENAMES[i]
							+ "runni__" + String.format("%03d", j) + ".png";
					img = ImageIO.read(new File(name));
					bitmasks.put(CharacterAnimation.BASENAMES[i] + "runni__"
							+ String.format("%03d", j) + ".png", new BitMask(
							img));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String currentImage = "sword_attacking_left___000.png";
		try {
			String name = "sprites/" + currentImage;
			BufferedImage image = ImageIO.read(new File(name));
			bitmasks.put(currentImage, new BitMask(image));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void init() {
		System.out.println("Loaded : " + bitmasks.size() + " bitmasks.");
	}

	public static BitMask getBitmask(String currentImage) {
//		System.out.println(bitmasks.keySet());
//		System.out.println(currentImage);
		return bitmasks.get(currentImage);
	}
}
