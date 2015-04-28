package particle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
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

	public void doDeath(double x, double y) {
		Color c = Color.RED;
		for (int i = 0; i < 100; i++) {

			particles.add(new Particle(x, y, rand.nextInt(11) - 5, rand
					.nextInt(19) - 9, .5, rand.nextInt(20), rand.nextDouble()
					* -0.2 - 0.1, 150 + rand.nextInt(150), c));

		}
	}

	public void update() {
		Iterator<Particle> iter = particles.iterator();
		while (iter.hasNext()) {
			if (iter.next().update())
				iter.remove();
		}

	}

	public void draw(Graphics g) {
		for (Particle particle : particles) {
			particle.draw(g);
		}
	}
}