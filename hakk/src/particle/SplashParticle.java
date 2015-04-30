package particle;

import java.awt.Color;

public class SplashParticle extends Particle {
	private static Color c = Color.CYAN;
	private double ground;

	public SplashParticle(double x, double y, double velx, double vely,
			double ground) {
		super(x, y, velx, vely, .3, 1, 0, 0.1, 100, c);
		this.ground = ground;
	}

	public boolean update(ParticleBatcher particleBatcher) {
		vely += acc;
		locx += velx;
		locy += vely;
		if (locy >= ground) {
			return true;
		}

		return false;
	}

}
