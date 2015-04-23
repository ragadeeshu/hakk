package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public abstract class Character {
	protected CharacterState state;
	protected CharacterAnimation animation;


	public Character() {
		state = new CharacterState();
	}

	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(state.x);
		int inty = (int) Math.round(state.y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		int height = animation.getImage().getHeight(null);
		g2d.drawImage(animation.getImage(), intx, inty-height, null);
	}

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
