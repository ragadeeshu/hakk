package graphics;

import java.awt.Graphics;
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

	public void drawPlane(Graphics g) {
		g.drawImage(flyingPlane, x, y, panel);
	}

	public void move() {
		if(y == -950) {
			x = -50;
			y = 150;
		}
		x += 2;
		y -= 1;
	}

}
