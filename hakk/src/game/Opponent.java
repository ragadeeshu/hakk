package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Opponent extends Character {

	public Opponent(HakkStage stage) {
		super();
		state.action = Action.STOPPING;
	}

	protected void doAction() {
		switch (state.action) {
		case JUMPING:

			state.yspeed -= 10;
			state.action = Action.IN_AIR;

			break;
		case RUNNING_LEFT:

			if (state.xspeed > -5)
				state.xspeed -= 1;
			else
				state.xspeed = -5;

			break;
		case RUNNING_RIGHT:

			if (state.xspeed < 5)
				state.xspeed += 1;
			else
				state.xspeed = 5;

			break;
		case STOPPING:
			if (state.xspeed != 0) {
				state.xspeed += state.xspeed * -0.7;
			}

			break;
		case IN_AIR:
			if (state.y > 299.99)
				state.action = Action.STOPPING;
			break;
		default:
			break;

		}

	}

	@Override
	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(state.x);
		int inty = (int) Math.round(state.y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.RED);
		g2d.fillOval(intx, inty, 30, 50);
	}
}
