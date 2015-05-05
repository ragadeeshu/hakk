package particle;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

public class Particle {

	protected double locx;
	protected double locy;
	protected double velx;
	protected double vely;
	protected double acc;
	protected double size;
	private double growth;
	private double bounce;
	private int life;
	protected Color color;

	public Particle(double x, double y, double dx, double dy, double acc,
			double size, double growth, double bounce, int life, Color c) {
		this.locx = x;
		this.locy = y;
		this.velx = dx;
		this.vely = dy;
		this.acc = acc;
		this.life = life;
		this.size = size;
		this.growth = growth;
		this.bounce = bounce;
		this.color = c;
	}

	public boolean update(ParticleBatcher particleBatcher) {
		size += growth;
		life--;

		if (life <= 0)
			return true;

		if (size <= 0)

			return true;
		vely += acc;
		locx += velx;
		locy += vely;
		if (locy >= HakkStage.GROUNDLEVEL) {
			locy = HakkStage.GROUNDLEVEL;
			vely *= -bounce;
		}

		return false;
	}

	public void draw(Graphics2D g2d, int offset) {
		g2d.setColor(color);
		g2d.fillOval((int) (locx - (size / 2)) - offset,
				(int) (locy - (size / 2)), (int) size, (int) size);
		if (locx < HakkStage.WIDTH) {
			g2d.fillOval((int) (locx - (size / 2)) - offset
					+ HakkStage.LEVEL_WIDTH, (int) (locy - (size / 2)),
					(int) size, (int) size);

		} else if (locx > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			g2d.fillOval((int) (locx - (size / 2)) - offset
					- HakkStage.LEVEL_WIDTH, (int) (locy - (size / 2)),
					(int) size, (int) size);
		}

	}

}