package graphics;

import game.HakkStage;
import game.Platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class LevelOne extends Level {
	public static final int BACKGROURND_PARTS = 10;
	public static final int GROURND_PARTS = 20;
	public static final int FOREGROURND_PARTS = 30;
	public static final int BACKGROURND_WIDTH = 4050;
	public static final int GROURND_WIDTH = 8100;
	public static final int FOREGROURND_WIDTH = 16200;
	public static final int BACKGROURND_HEIGHT = 900;
	public static final int GROURND_HEIGHT = 180;
	public static final int FOREGROURND_HEIGHT = 101;

	public LevelOne() {
		background = new BufferedImage[BACKGROURND_PARTS];
		ground = new BufferedImage[GROURND_PARTS];
		foreground = new BufferedImage[FOREGROURND_PARTS];
		try {
			for (int j = 0; j < BACKGROURND_PARTS; j++) {
				String name = "sprites/background_long [www.imagesplitter.net]-0-"
						+ j + ".png";
				background[j] = ImageIO.read(new File(name));
			}
			for (int j = 0; j < GROURND_PARTS; j++) {
				String name = "sprites/ground_long [www.imagesplitter.net]-0-"
						+ j + ".png";
				ground[j] = ImageIO.read(new File(name));
			}
			for (int j = 0; j < FOREGROURND_PARTS; j++) {
				String name = "sprites/foreground [www.imagesplitter.net]-0-"
						+ j + ".png";
				foreground[j] = ImageIO.read(new File(name));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		stageWidth = GROURND_WIDTH;
		platforms = new ArrayList<Platform>();
		platforms.add(new Platform(200, HakkStage.GROUNDLEVEL - 100));
		platforms.add(new Platform(500, HakkStage.GROUNDLEVEL - 200));
		platforms.add(new Platform(1500, HakkStage.GROUNDLEVEL - 150));
		platforms.add(new Platform(700, HakkStage.GROUNDLEVEL - 200));
		platforms.add(new Platform(1700, HakkStage.GROUNDLEVEL - 150));
		platforms.add(new Platform(1000, HakkStage.GROUNDLEVEL - 100));
		platforms.add(new Platform(4500, HakkStage.GROUNDLEVEL - 100));
		platforms.add(new Platform(1450, HakkStage.GROUNDLEVEL - 200));
		platforms.add(new Platform(2350, HakkStage.GROUNDLEVEL - 150));
		platforms.add(new Platform(5500, HakkStage.GROUNDLEVEL - 150));
		platforms.add(new Platform(2500, HakkStage.GROUNDLEVEL - 150));
		platforms.add(new Platform(3000, HakkStage.GROUNDLEVEL - 100));
	}

	// @Override
	// public void drawBackground(Graphics2D g2d) {
	// g2d.drawImage(background, -offSetX / 2, (int) (HakkStage.HEIGHT
	// - BACKGROURND_HEIGHT + offSetY * 1.5), null);
	// if (offSetX > HakkStage.LEVEL_WIDTH - 2 * HakkStage.WIDTH) {
	// g2d.drawImage(
	// background,
	// -offSetX / 2 + BACKGROURND_WIDTH,
	// (int) (HakkStage.HEIGHT - BACKGROURND_HEIGHT + offSetY * 1.5),
	// null);
	// } else if (offSetX < 0) {
	// g2d.drawImage(
	// background,
	// -offSetX / 2 - BACKGROURND_WIDTH,
	// (int) (HakkStage.HEIGHT - BACKGROURND_HEIGHT + offSetY * 1.5),
	// null);
	// }
	//
	// }

	@Override
	public void drawBackground(Graphics2D g2d) {
		for (int i = offSetX / 2 / (BACKGROURND_WIDTH / BACKGROURND_PARTS); i < offSetX
				/ 2
				/ (BACKGROURND_WIDTH / BACKGROURND_PARTS)
				+ HakkStage.WIDTH
				/ (BACKGROURND_WIDTH / BACKGROURND_PARTS) + 2; i++) {
			g2d.drawImage(
					background[i % BACKGROURND_PARTS],
					-offSetX / 2 + i * BACKGROURND_WIDTH / BACKGROURND_PARTS,
					(int) (HakkStage.HEIGHT - BACKGROURND_HEIGHT + offSetY * 1.5),
					null);

		}

		if (offSetX < 0) {
			g2d.drawImage(
					background[BACKGROURND_PARTS - 1],
					-offSetX / 2 - BACKGROURND_WIDTH / BACKGROURND_PARTS,
					(int) (HakkStage.HEIGHT - BACKGROURND_HEIGHT + offSetY * 1.5),
					null);
		}

	}

	@Override
	public void drawGround(Graphics2D g2d) {
		for (int i = offSetX / (GROURND_WIDTH / GROURND_PARTS); i < offSetX

		/ (GROURND_WIDTH / GROURND_PARTS) + HakkStage.WIDTH
				/ (GROURND_WIDTH / GROURND_PARTS) + 2; i++) {
			if (i < 0) {
//				g2d.drawImage(ground[i + GROURND_PARTS], -offSetX + i
//						* GROURND_WIDTH / GROURND_PARTS,
//						(int) (HakkStage.HEIGHT - GROURND_HEIGHT + offSetY),
//						null);

			} else {
				g2d.drawImage(ground[i % GROURND_PARTS], -offSetX + i
						* GROURND_WIDTH / GROURND_PARTS,
						(int) (HakkStage.HEIGHT - GROURND_HEIGHT + offSetY),
						null);
			}

		}

		if (offSetX < 0) {
			g2d.drawImage(ground[GROURND_PARTS - 1], -offSetX - GROURND_WIDTH
					/ GROURND_PARTS,
					(int) (HakkStage.HEIGHT - GROURND_HEIGHT + offSetY), null);
			g2d.drawImage(ground[GROURND_PARTS - 2], -offSetX - 2
					* GROURND_WIDTH / GROURND_PARTS, (int) (HakkStage.HEIGHT
					- GROURND_HEIGHT + offSetY), null);
		}

	}

	@Override
	public void drawForeground(Graphics2D g2d) {

		for (int i = offSetX * 2 / (FOREGROURND_WIDTH / FOREGROURND_PARTS); i < offSetX
				* 2

				/ (FOREGROURND_WIDTH / FOREGROURND_PARTS)
				+ HakkStage.WIDTH
				/ (FOREGROURND_WIDTH / FOREGROURND_PARTS) + 2; i++) {
			if (i < 0) {
//				g2d.drawImage(
//						foreground[i + FOREGROURND_PARTS],
//						-offSetX * 2 + i * FOREGROURND_WIDTH
//								/ FOREGROURND_PARTS,
//						(int) (HakkStage.HEIGHT - FOREGROURND_HEIGHT + offSetY),
//						null);

			} else {
				g2d.drawImage(
						foreground[i % FOREGROURND_PARTS],
						-offSetX * 2 + i * FOREGROURND_WIDTH
								/ FOREGROURND_PARTS,
						(int) (HakkStage.HEIGHT - FOREGROURND_HEIGHT + offSetY),
						null);
			}

		}

		if (offSetX < 0) {
			for (int i = 1; i < 4; i++) {
				g2d.drawImage(
						foreground[FOREGROURND_PARTS - i],
						-offSetX * 2 - i * FOREGROURND_WIDTH
								/ FOREGROURND_PARTS,
						(int) (HakkStage.HEIGHT - FOREGROURND_HEIGHT + offSetY),
						null);

			}
		}

	}

	@Override
	public void drawPlatforms(Graphics2D g2d, int xOffset, int yOffset) {
		for (Platform platform : platforms) {
			platform.draw(g2d, xOffset, yOffset);
		}
	}

	@Override
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}

	@Override
	public int hitPlatform(double charX, double charY, int charWidth) {
		for (Platform platform : platforms) {
			int y = platform.hitPlatform(charX, charY, charWidth);
			if (y != 0) {
				return y;
			}
		}
		return 0;
	}

}
