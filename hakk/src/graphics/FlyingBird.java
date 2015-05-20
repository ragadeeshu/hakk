package graphics;

import game.HakkStage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class FlyingBird {
	private BufferedImage flyingBird;
	int x, y;

	public FlyingBird(int x, int y) {
		try {
			String bird = "sprites/flyingbird.png";
			flyingBird = ImageIO.read(getClass().getResource("/resources/" + bird));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.x = x;
		this.y = y;
	}

	public void drawBird(Graphics2D g2d, int xOffset, int yOffset) {
		g2d.drawImage(flyingBird, x - xOffset, y + yOffset, null);
		if (x> HakkStage.LEVEL_WIDTH-HakkStage.WIDTH) {
			g2d.drawImage(flyingBird, x - xOffset - HakkStage.LEVEL_WIDTH, y
					+ yOffset, null);
		} else if (x<HakkStage.WIDTH) {
			g2d.drawImage(flyingBird,x -xOffset + HakkStage.LEVEL_WIDTH,y +
					HakkStage.HEIGHT + yOffset, null);
		}
	}

	public void move() {
		x -= 4;
		if(x< 0)
			x+= HakkStage.LEVEL_WIDTH;
	}

}