package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class FlyingPlane {
	private JPanel panel;
	private BufferedImage flyingPlane;
	int x, y;

	public FlyingPlane(int x, int y) {
		try {
			String plane = "sprites/flyingplane.png";
			flyingPlane = ImageIO.read(new File(plane));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}

	public void drawPlane(Graphics2D g2d, int xOffset, int yOffset) {
		g2d.drawImage(flyingPlane, x - xOffset, y + yOffset, null);
		if (xOffset > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(flyingPlane, x - xOffset + HakkStage.LEVEL_WIDTH, y
					+ yOffset, null);
		} else if (xOffset < 0) {
			g2d.drawImage(flyingPlane, -xOffset - HakkStage.LEVEL_WIDTH,
					HakkStage.HEIGHT + yOffset, null);
		}
	}

	public void move() {
		if (y == -950) {
			x = -50;
			y = 150;
		}
		x += 2;
		y -= 1;
	}

}
