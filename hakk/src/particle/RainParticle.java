package particle;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RainParticle extends Particle {
	private static BufferedImage IMAGE;
	private static final Color C = Color.CYAN;
	private double ground;

	static {
		IMAGE = null;
		try {
			String path = "sprites/rain.png";
			IMAGE = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public RainParticle(double x, double y, double ground) {
		super(x, y, 0, 12, .2, 2, 0, 0.1, 100, C);
		this.ground = ground;
	}

	public boolean update(ParticleBatcher particleBatcher) {
		vely += acc;
		locx += velx;
		locy += vely;
		if (locy >= ground) {
			locy = ground - 0.1;
			particleBatcher.doSplash(locx, locy, vely, ground);
			return true;
		}

		return false;
	}

	@Override
	public void draw(Graphics2D g2d, int xOffset, int yOffset) {

		// g2d.setColor(color);
		// g2d.fillOval((int) (locx - (size / 2)) - xOffset,
		// (int) (locy - (size / 2)) + yOffset, (int) size, (int) size * 4);
		g2d.drawImage(IMAGE, (int) locx - xOffset, (int) locy + yOffset, null);
		if (locx < HakkStage.WIDTH) {
			g2d.drawImage(IMAGE, (int) locx - xOffset + HakkStage.LEVEL_WIDTH,
					(int) locy + yOffset, null);

			// g2d.fillOval((int) (locx - (size / 2)) - xOffset
			// + HakkStage.LEVEL_WIDTH, (int) (locy - (size / 2))
			// + yOffset, (int) size, (int) size * 4);

		} else if (locx > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.drawImage(IMAGE, (int) locx - xOffset - HakkStage.LEVEL_WIDTH,
					(int) locy + yOffset, null);
			// g2d.drawImage(IMAGE, (int) locx, (int) locy, null);
			//
			// g2d.fillOval((int) (locx - (size / 2)) - xOffset
			// - HakkStage.LEVEL_WIDTH, (int) (locy - (size / 2))
			// + yOffset, (int) size, (int) size * 4);
		}

	}
}
