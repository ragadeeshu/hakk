package game;

import java.awt.Color;

public class Opponent extends Character {

	public Opponent(HakkStage stage, String playerName) {
		super(playerName, "player2");
		nameColour = Color.RED;
		// Action.STOPPING;
		// animation = new CharacterAnimation("player2");

	}

	protected void doAction() {
		switch (state.action) {
		case JUMPING:

			state.yspeed -= 10;
			state.action = Action.IN_AIR;

			break;
		case RUNNING_LEFT:
			animation.run();

			if (state.xspeed > -5)
				state.xspeed -= 1;
			else
				state.xspeed = -5;

			break;
		case RUNNING_RIGHT:
			animation.run();

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
			if (state.y > HakkStage.GROUNDLEVEL)
				state.action = Action.STOPPING;
			break;
		default:
			break;

		}

	}
}
