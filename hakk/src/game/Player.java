package game;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Player extends Character {
	private boolean tryingToRunLeft;
	private boolean tryingToRunRight;

	public Player(HakkStage stage, String playerName) {
		super(playerName, "player");
		nameColour = Color.GREEN;

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
							case KeyEvent.VK_Z:
							case KeyEvent.VK_X:
							case KeyEvent.VK_C:
							case KeyEvent.VK_V:
							case KeyEvent.VK_NUMPAD0:
								swing();
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
		if (charState.action != Action.IN_AIR) {
			if (!tryingToRunRight) {
				charState.action = Action.STOPPING;
			} else
				charState.action = Action.RUNNING_RIGHT;
		}

	}

	private void stopRight() {
		tryingToRunRight = false;
		if (charState.action != Action.IN_AIR) {
			if (!tryingToRunLeft) {
				charState.action = Action.STOPPING;
			} else
				charState.action = Action.RUNNING_LEFT;
		}

	}

	private void runLeft() {
		tryingToRunLeft = true;
		if (charState.action != Action.IN_AIR) {
			charState.action = Action.RUNNING_LEFT;
		}
	}

	protected void runRight() {
		tryingToRunRight = true;
		if (charState.action != Action.IN_AIR)
			charState.action = Action.RUNNING_RIGHT;
	}

	protected void jump() {
		if (charState.action != Action.IN_AIR)
			charState.action = Action.JUMPING;
	}
	
	private void swing() {
		swordState.action = Action.SWINGING;
		swordAnimation.swing();
	}

	protected void doAction() {
		switch (charState.action) {
		case JUMPING:

			charState.yspeed -= 10;
			charState.action = Action.IN_AIR;

			break;
		case RUNNING_LEFT:
			if (hitLeftWall()) {
				charState.action = Action.STOPPING;
				break;
			}
			charAnimation.run();
			
			if (charState.xspeed > -5)
				charState.xspeed -= 1;
			else
				charState.xspeed = -5;

			break;
		case RUNNING_RIGHT:
			if (hitRightWall()) {
				charState.action = Action.STOPPING;
				break;
			}
			charAnimation.run();
			if (charState.xspeed < 5)
				charState.xspeed += 1;
			else
				charState.xspeed = 5;

			break;
		case STOPPING:
			if (charState.xspeed != 0) {
				charState.xspeed += charState.xspeed * -0.7;
			}

			break;
		case IN_AIR:
			if (charState.y >= HakkStage.GROUNDLEVEL){
				if (tryingToRunLeft && tryingToRunRight || !(tryingToRunLeft || tryingToRunRight)) {
					charState.action = Action.STOPPING;
				} else if (tryingToRunLeft){
					charState.action = Action.RUNNING_LEFT;
				} else {
					charState.action = Action.RUNNING_RIGHT;
				}
			} else if(hitLeftWall() || hitRightWall()){
				charState.xspeed = 0;
			}
			break;
		default:
			break;

		}
		charState.currentImage = charAnimation.getCurrentImageName();
		swordState.currentImage = swordAnimation.getCurrentImageName();
	}
}
