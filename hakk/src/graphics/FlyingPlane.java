package graphics;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlyingPlane {
	private BufferedImage flyingPlane;
	int x, y;

	public FlyingPlane(int x, int y) {
		try {
			String plane = "sprites/flyingplane.png";
			flyingPlane = ImageIO.read(getClass().getResource("/resources/" + plane));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}

	public void drawPlane(Graphics2D g2d, int xOffset, int yOffset) {
		g2d.drawImage(flyingPlane, x - xOffset/2, (int) (y + yOffset*1.5), null);
		if (x<HakkStage.WIDTH) {
			g2d.drawImage(flyingPlane, x - xOffset/2 + HakkStage.LEVEL_WIDTH, (int) (y
					+ yOffset*1.5), null);
		} else if (x> HakkStage.LEVEL_WIDTH-HakkStage.WIDTH) {
			g2d.drawImage(flyingPlane, -xOffset/2 - HakkStage.LEVEL_WIDTH,
					(int) (HakkStage.HEIGHT + yOffset*1.5), null);
		}
		
	}

	public void move() {
		x += 8;
		if(x> 0)
			x-= HakkStage.LEVEL_WIDTH;
	}

}
