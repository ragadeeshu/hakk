package game;

import java.awt.Graphics2D;

public abstract class Character {
	protected CharacterState state;

	public Character() {
		state = new CharacterState();
	}

	public abstract void draw(Graphics2D g2d);

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

	public void setState(CharacterState value) {
		state = value;
	}

	public void doPhysics() {
		doAction();
		doGravity();
		doMovement();
		System.out.println(state.action);
	}

	protected abstract void doAction();

}
