package game;

import java.awt.Graphics2D;

public abstract class Character {
	protected double x = 0;
	protected double y = 0;
	protected double xspeed = 0;
	protected double yspeed = 0;

	public abstract void draw(Graphics2D g2d);

	public abstract void doPhysics();

	protected void doGravity() {
		yspeed -= -.5;
	}

	protected void doMovement() {
		x += xspeed;
		y += yspeed;
		if (y >= 300) {
			y = 300;
			yspeed = 0;
		}
	}
}
