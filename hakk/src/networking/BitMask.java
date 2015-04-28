package networking;

import java.awt.image.BufferedImage;
import java.util.BitSet;

public class BitMask {

	private int height;
	private int width;
	private BitSet[] mask;
	private static final int WIDTH = 96;

	public BitMask(BufferedImage img) {
		height = img.getHeight();
		width = img.getWidth();
		mask = new BitSet[height];

		for (int i = 0; i < height; i++) {
			BitSet lineMask = new BitSet(WIDTH);
			for (int j = 0; j < width; j++) {
				if (!isTransparent(j, i, img)) {
					lineMask.set(j);

				}
			}
			mask[i] = lineMask;

		}

	}

	private boolean isTransparent(int x, int y, BufferedImage img) {
		int pixel = img.getRGB(x, y);
		if ((pixel >> 24) == 0x00) {
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
			long[] longs = mask[i].toLongArray();
			for (int j = 0; j < longs.length; j++) {
				sb.append(String.format("%64s", Long.toBinaryString(longs[j]))
						.replace(' ', '0'));

			}
			if (longs.length == 0)
				sb.append(String.format("%64s", Long.toBinaryString(0))
						.replace(' ', '0'));

			sb.append("\n");
		}
		return sb.toString();
	}

	public String shiftedToString(int x, int y) {
		StringBuilder sb = new StringBuilder();

		for (int i = y; i < height; i++) {

			long[] longs = mask[i].get(x, Math.max(x, mask[i].length()))
					.toLongArray();

			for (int j = 0; j < longs.length; j++) {
				sb.append(String.format("%64s", Long.toBinaryString(longs[j]))
						.replace(' ', '0'));
			}
			if (longs.length == 0)
				sb.append(String.format("%64s", Long.toBinaryString(0))
						.replace(' ', '0'));
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean overlaps(int x, int y, int ox, int oy, BitMask oMask) {
		int linePlayer, lineEnemy;
		if (y <= oy) {
			linePlayer = oy - y;
			lineEnemy = 0;
		} else {
			linePlayer = 0;
			lineEnemy = y - oy;
		}

		x = x - ox;
		/*
		 * SNALLA ta inte bort de har raderna !!!
		 * 
		 * System.out.println("Shift: " + x);
		 * System.out.println("StartLinePlayer: " + linePlayer);
		 * System.out.println("StartLinePlayer: " + lineEnemy);
		 * System.out.println(toString()); System.out.println(oMask.toString());
		 * if (x < 0) { System.out.println("Shifted player");
		 * System.out.println(shiftedToString(-x, 0)); } else {
		 * System.out.println("Shifted enemy");
		 * System.out.println(oMask.shiftedToString(WIDTH - x, 0)); }
		 */
		while (linePlayer < height && lineEnemy < oMask.height) {
			BitSet playerMask = (BitSet) mask[linePlayer].clone();
			BitSet enemyMask = (BitSet) oMask.mask[lineEnemy].clone();

			if (x < 0) {
				playerMask = playerMask.get(-x,
						Math.max(-x, playerMask.length()));
			} else {
				enemyMask = enemyMask.get(x, Math.max(x, enemyMask.length()));
			}

			playerMask.and(enemyMask);
			if (!playerMask.isEmpty()) {
				return true;
			}

			lineEnemy++;
			linePlayer++;
		}
		return false;
	}
}
