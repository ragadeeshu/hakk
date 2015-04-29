package graphics;

import java.awt.Graphics;
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

	public void drawBird(Graphics g) {
		g.drawImage(flyingBird, x, y, panel);
	}

	public void move() {
		if(y == -450) {
			x = 920;
			y = 150;
		}
		x -= 2;
		y -= 1;
	}

}