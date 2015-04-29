package networking;

import graphics.CharacterAnimation;
import graphics.SwordAnimation;

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
		
		for (int i = 0; i < SwordAnimation.BASENAMES.length; i++) {
			for (int j = 0; j < SwordAnimation.NUM_ATTACK; j++) {
				try {
					String name = "sprites/" + SwordAnimation.BASENAMES[i] + String.format("%03d", j) + ".png";
					BufferedImage image = ImageIO.read(new File(name));
					bitmasks.put(SwordAnimation.BASENAMES[i] + String.format("%03d", j) + ".png", new BitMask(image));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
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
