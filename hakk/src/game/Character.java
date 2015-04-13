package game;

import java.awt.Graphics2D;

public abstract class Character {
	public Character(){
		state = new CharacterState();
	}

	protected CharacterState state;

	public abstract void draw(Graphics2D g2d);

	public abstract void doPhysics();

	protected void doGravity() {
		state.yspeed -= -.5;
	}

	protected void doMovement() {
		state.x += state.xspeed;
		state.y += state.yspeed;
		if (state.y >= 300) {
			state.y = 300;
			state.yspeed = 0;
		}
	}
}
