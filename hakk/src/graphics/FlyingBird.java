package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FlyingBird {
	private JPanel panel;
	private BufferedImage flyingBird;
	int x, y;

	public FlyingBird(int x, int y) {
		try {
			String bird = "sprites/flyingbird.png";
			flyingBird = ImageIO.read(new File(bird));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}

	public void drawBird(Graphics2D g2d, int xOffset, int yOffset) {
		g2d.drawImage(flyingBird, x - xOffset, y + yOffset, null);
		if (xOffset > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(flyingBird, x - xOffset + HakkStage.LEVEL_WIDTH, y
					+ yOffset, null);
		} else if (xOffset < 0) {
			g2d.drawImage(flyingBird, -xOffset - HakkStage.LEVEL_WIDTH,
					HakkStage.HEIGHT  + yOffset, null);
		}
	}

	public void move() {
		if (y == -450) {
			x = 920;
			y = 150;
		}
		x -= 2;
		y -= 1;
	}

}