package networking;

import java.awt.image.BufferedImage;

public class NativeBitMask {
	private static final int NUM_ARRAYS = 3;

	private int height;
	private int width;
	private int[] mask;

	public NativeBitMask(BufferedImage img) {
		height = img.getHeight();
		width = img.getWidth();
		mask = new int[height * NUM_ARRAYS];
		for (int count = 0; count < NUM_ARRAYS; count++) {

			for (int i = 0; i < height; i++) {
				// System.out.println("begin");
				int lineMask = 0x0;
				// System.out.println(Integer.toBinaryString(lineMask));
				for (int j = 0; j < 32; j++) {
					if (j + 32 * count < width) {
						if (!isTransparent(j + 32 * count, i, img)) {
							lineMask = lineMask | (0x1 << 31 - j);
							// System.out
							// .println(Integer.toBinaryString(lineMask));
						}
					}
				}
				mask[i + height * count] = lineMask;
			}
		}

	}

	private boolean isTransparent(int x, int y, BufferedImage img) {
		int pixel = img.getRGB(x, y);
		if ((pixel >> 24) == 0x00) {
			// if ((pixel >> 24) != -1) {
			// System.out.println(Integer.toBinaryString(pixel >> 24));
			return true;
		}
		return false;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < height; i++) {
			for (int count = 0; count < NUM_ARRAYS; count++) {
				sb.append(String.format("%32s",
						Integer.toBinaryString(mask[i + count * height]))
						.replace(' ', '0'));
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean overlaps(int x, int y, int ox, int oy, NativeBitMask oMask) {
		int linePlayer, lineEnemy;
		if (y <= oy) {
			linePlayer = oy - y;
			lineEnemy = 0;
		} else {
			linePlayer = 0;
			lineEnemy = y - oy;
		}
		// int line = Math.max(linePlayer, lineEnemy);

		// Get the shift between the two
		x = x - ox;
		// int maxLines = Math.max(height, oMask.height);

		// System.out.println("Maxlines: " + maxLines);
		// System.out.println("Line to start with: " + line);
		System.out.println("Shift: " + x);
		System.out.println("StartLinePlayer: " + linePlayer);
		System.out.println("StartLinePlayer: " + lineEnemy);
		while (linePlayer < height && lineEnemy < oMask.height) {
			for (int count = 0; count < NUM_ARRAYS; count++) {
				// if width > 32, then you need a second loop here
				int playerMask = mask[linePlayer + count * height];
				int enemyMask = oMask.mask[lineEnemy + count * oMask.height];
				// System.out.println("Masks:");
				// System.out.println(Integer.toBinaryString(playerMask));
				// System.out.println(Long.toBinaryString(enemyMask));
				// System.out.println(String.format("%32s",
				System.out.println("Before shift");
				System.out.println(String.format("%32s",
						Integer.toBinaryString(playerMask)).replace(' ', '0'));
				System.out.println(String.format("%32s",
						Integer.toBinaryString(enemyMask)).replace(' ', '0'));

				// Reproduce the shift between the two sprites
				if (x < 0)
					playerMask = playerMask << (-x);
				else
					enemyMask = enemyMask << x;
				System.out.println("After shift");
				System.out.println(String.format("%32s",
						Integer.toBinaryString(playerMask)).replace(' ', '0'));
				System.out.println(String.format("%32s",
						Integer.toBinaryString(enemyMask)).replace(' ', '0'));
				// If the two masks have common bits, binary AND will return !=
				// 0
				if ((playerMask & enemyMask) != 0) {
					System.out.println("WOHOO");
					return true;
				}

			}
			lineEnemy++;
			linePlayer++;
			// System.out.println(linePlayer);
			// System.out.println(lineEnemy);
		}
		return false;
	}
}
