package game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Platform {
	private int x;
	private int y;
	private static BufferedImage image;
	private int width;
	private int height;

	static {
		try {
			image = ImageIO.read(Platform.class
					.getResource("/resources/sprites/platform.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Platform(int x, int y) {
		this.x = x;
		this.y = y;
		width = 110;
		height = 29;
	}

	public void draw(Graphics2D g2d, int xOffset, int yOffset) {
		g2d.drawImage(image, x - xOffset, y + yOffset, null);
		if (xOffset > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(image, x - xOffset + HakkStage.LEVEL_WIDTH, y
					+ yOffset, null);
		} else if (xOffset < 0) {
			g2d.drawImage(image, x - xOffset - HakkStage.LEVEL_WIDTH,
					HakkStage.HEIGHT - height + y + yOffset, null);
		}
	}

	public int hitPlatform(double charX, double charY, int charWidth) {
		if (charX + charWidth > x && charX < x + width) {
			if (charY > y && charY < y + height) {
				return y;
			}
		}
		return 0;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
