package particle;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class ParticleBatcher {
	private HashSet<Particle> particles;
	Random rand;

	public ParticleBatcher() {
		particles = new HashSet<Particle>();
		rand = new Random();
	}

	public synchronized void doRain(int offset) {
		Color c = Color.RED;
		particles.add(new Particle(rand.nextInt((int) (HakkStage.WIDTH*1.4))-HakkStage.WIDTH*0.2 + offset, -100, 5, 0, .3, 3, 0,
				0.1, 100, c));

	}

	public synchronized void doDeath(double x, double y) {
		Color c = Color.RED;
		for (int i = 0; i < 100; i++) {

			particles.add(new Particle(x, y, rand.nextInt(21) - 10, rand
					.nextInt(41) - 19, .5, rand.nextInt(25), rand.nextDouble()
					* -0.2 - 0.2, 0.9, 150 + rand.nextInt(50), c));

		}
	}

	public synchronized void update() {
		Iterator<Particle> iter = particles.iterator();
		while (iter.hasNext()) {
			if (iter.next().update())
				iter.remove();
		}

	}

	public synchronized void draw(Graphics g, int offset) {
		for (Particle particle : particles) {
			particle.draw(g, offset);
		}
	}
}
