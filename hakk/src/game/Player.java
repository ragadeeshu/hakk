package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

public class Player extends Character {
	private boolean tryingToRunLeft;
	private boolean tryingToRunRight;

	public Player(HakkStage stage, String playerName) {
		super(playerName, "player");

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						if (e.getID() == KeyEvent.KEY_PRESSED) {
							int keyCode = e.getKeyCode();
							switch (keyCode) {
							case KeyEvent.VK_UP:
								jump();
								break;
							case KeyEvent.VK_LEFT:
								runLeft();
								break;
							case KeyEvent.VK_RIGHT:
								runRight();
								break;
							}
						}
						if (e.getID() == KeyEvent.KEY_RELEASED) {
							int keyCode = e.getKeyCode();
							switch (keyCode) {
							case KeyEvent.VK_LEFT:
								stopLeft();
								break;
							case KeyEvent.VK_RIGHT:
								stopRight();
								break;
							}
						}
						return false;
					}

				});
	}

	private void stopLeft() {
		tryingToRunLeft = false;
		if (state.action != Action.IN_AIR) {
			if (!tryingToRunRight) {
				state.action = Action.STOPPING;
			} else
				state.action = Action.RUNNING_RIGHT;
		}

	}

	private void stopRight() {
		tryingToRunRight = false;
		if (state.action != Action.IN_AIR) {
			if (!tryingToRunLeft) {
				state.action = Action.STOPPING;
			} else
				state.action = Action.RUNNING_LEFT;
		}

	}

	private void runLeft() {
		tryingToRunLeft = true;
		if (state.action != Action.IN_AIR) {
			state.action = Action.RUNNING_LEFT;
		}
	}

	protected void runRight() {
		tryingToRunRight = true;
		if (state.action != Action.IN_AIR)
			state.action = Action.RUNNING_RIGHT;
	}

	protected void jump() {
		if (state.action != Action.IN_AIR)
			state.action = Action.JUMPING;

	}

	protected void doAction() {
		switch (state.action) {
		case JUMPING:

			state.yspeed -= 10;
			state.action = Action.IN_AIR;

			break;
		case RUNNING_LEFT:
			if (hitLeftWall()) {
				state.action = Action.STOPPING;
				break;
			}
			animation.run();
			state.currentImage = animation.getCurrentImageName();
			if (state.xspeed > -5)
				state.xspeed -= 1;
			else
				state.xspeed = -5;

			break;
		case RUNNING_RIGHT:
			if (hitRightWall()) {
				state.action = Action.STOPPING;
				break;
			}
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
			if (state.y >= HakkStage.GROUNDLEVEL){
				if (tryingToRunLeft && tryingToRunRight || !(tryingToRunLeft || tryingToRunRight)) {
					state.action = Action.STOPPING;
				} else if (tryingToRunLeft){
					state.action = Action.RUNNING_LEFT;
				} else {
					state.action = Action.RUNNING_RIGHT;
				}
			} else if(hitLeftWall() || hitRightWall()){
				state.xspeed = 0;
			}
			break;
		default:
			break;

		}

	}
}
