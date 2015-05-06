package game;

import graphics.Level;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;

import Music.*;

public class Opponent extends Character {
	
	private SoundEffect jumpEffect;

	public Opponent(HakkStage stage, String playerName) {
		super(playerName, "player2");
		nameColour = Color.RED;
		jumpEffect = new SoundEffect("bgm/jump2.mp3");
		jumpEffect.start();
	}

	protected void doAction() {
		switch (charState.action) {
		case JUMPING:
			charState.yspeed -= 17;
			charState.action = Action.IN_AIR;
			jumpEffect.notifyPlayer();
			break;
		case RUNNING_LEFT:
			charAnimation.run();

			if (charState.xspeed > -7)
				charState.xspeed -= 1;
			else
				charState.xspeed = -7;

			break;
		case RUNNING_RIGHT:
			charAnimation.run();

			if (charState.xspeed < 7)
				charState.xspeed += 1;
			else
				charState.xspeed = 7;

			break;
		case STOPPING:
			if (charState.xspeed != 0) {
				charState.xspeed += charState.xspeed * -0.7;
			}

			break;
		case IN_AIR:
			if (charState.y > HakkStage.GROUNDLEVEL)
				charState.action = Action.STOPPING;
			break;
		default:
			break;

		}

	}

	@Override
	protected void doAction(Level level) {
		// TODO Auto-generated method stub
		
	}
}
