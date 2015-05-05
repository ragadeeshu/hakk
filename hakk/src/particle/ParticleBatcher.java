package particle;

import game.HakkStage;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class ParticleBatcher {
	private LinkedList<Particle> particles;
	Random rand;
	private LinkedList<Particle> splashParticles;

	public ParticleBatcher() {
		particles = new LinkedList<Particle>();
		splashParticles = new LinkedList<Particle>();
		rand = new Random();
	}

	public synchronized void doRain(int offset) {
		particles.add(new RainParticle(rand
				.nextInt((int) (HakkStage.WIDTH * 1.8))
				- HakkStage.WIDTH
				* 0.4
				+ offset, -100, HakkStage.GROUNDLEVEL + rand.nextInt(81) - 40));

	}

	public synchronized void doDeath(double x, double y) {
		Color c = Color.RED;
		for (int i = 0; i < 100; i++) {

			particles.add(new Particle(x, y, rand.nextInt(21) - 10, rand
					.nextInt(61) - 19, .7, rand.nextInt(30), rand.nextDouble()
					* -0.2 - 0.2, 0.9, 300, c));

		}
	}

	public synchronized void update() {
		Iterator<Particle> iter = particles.iterator();
		while (iter.hasNext()) {
			if (iter.next().update(this))
				iter.remove();
		}
		particles.addAll(splashParticles);
		splashParticles.clear();

	}

	public synchronized void draw(Graphics2D g, int offset) {
		for (Particle particle : particles) {
			particle.draw(g, offset);
		}
	}

	public void doSplash(double locx, double locy, double vely, double ground) {
		int num = rand.nextInt(6);
		for (int i = 0; i < num; i++) {
			splashParticles.add(new SplashParticle(locx, locy, rand
					.nextDouble() * 2 - 1, -vely * .2 + rand.nextDouble(),
					ground));

		}

	}
}
