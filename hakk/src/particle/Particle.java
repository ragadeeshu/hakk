package particle;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Particle {

	private double locx;
	private double locy;
	private double velx;
	private double vely;
	private double acc;
	private double size;
	private double growth;
	private int life;
	private Color color;

	public Particle(double x, double y, double dx, double dy, double acc,
			double size, double growth, int life, Color c) {
		this.locx = x;
		this.locy = y;
		this.velx = dx;
		this.vely = dy;
		this.acc = acc;
		this.life = life;
		this.size = size;
		this.growth = growth;
		this.color = c;
	}

	public boolean update() {
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
			vely *= -0.5;
		}

		return false;
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();

		g2d.setColor(color);
		g2d.fillOval((int) (locx - (size / 2)), (int) (locy - (size / 2)),
				(int) size, (int) size);

		g2d.dispose();
	}

}