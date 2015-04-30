package particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import game.HakkStage;

public class RainParticle extends Particle {
	private static Color c = Color.CYAN;
	private double ground;

	public RainParticle(double x, double y, double ground) {
		super(x, y, 0, 1, .3, 2, 0, 0.1, 100, c);
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

	public void draw(Graphics g, int offset) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(color);
		g2d.fillOval((int) (locx - (size / 2)) - offset,
				(int) (locy - (size / 2)), (int) size, (int) size * 3);

		g2d.dispose();
	}
}
