package game;

import graphics.CharacterAnimation;
import graphics.Level;

import java.awt.Color;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import Music.SoundEffect;

public class Player extends Character {
	private boolean tryingToRunLeft;
	private boolean tryingToRunRight;

	private SoundEffect jumpEffect;

	public Player(HakkStage stage, String playerName) {
		super(playerName, "player");
		nameColour = Color.GREEN;
		jumpEffect = new SoundEffect("bgm/jump.mp3");
		jumpEffect.start();
		// jumpEffect.initiateSound();

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
							case KeyEvent.VK_SPACE:
								swordSwing(); //swing 360 degrees
								break;
							case KeyEvent.VK_A:
								swordRotateLeft(); // tba
								break;
							case KeyEvent.VK_D:
								swordRotateRight(); // tba
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
		swordState.left = 1;
		swordAnimation.left = 1;
		tryingToRunLeft = true;
		if (charState.action != Action.IN_AIR) {
			charState.action = Action.RUNNING_LEFT;
		}
	}

	protected void runRight() {
		swordState.left = 0;
		swordAnimation.left = 0;
		tryingToRunRight = true;
		if (charState.action != Action.IN_AIR) {
			charState.action = Action.RUNNING_RIGHT;
		}
	}

	protected void jump() {
		if (charState.action != Action.IN_AIR) {
			charState.action = Action.JUMPING;
		}
	}

	private void swordSwing() {
		if(!swordAnimation.doingSwing){
		swordState.action = Action.SWINGING;
		swordAnimation.swing();
		}
	}
	
	private void swordRotateLeft() {
//		if(swordState.action != )
		swordState.action = Action.ROT_LEFT;
		swordAnimation.rotateLeft();
	}
	
	private void swordRotateRight() {
		swordState.action = Action.ROT_RIGHT;
		swordAnimation.rotateRight();
	}
	

	protected void doAction(Level level) {
		switch (charState.action) {
		case JUMPING:
			charState.yspeed -= 17;
			charState.action = Action.IN_AIR;
			// jumpEffect.notifyPlayer();
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
			if (charState.y >= HakkStage.GROUNDLEVEL
					|| charState.yspeed >=0
					&& level.hitPlatform(charState.x, charState.y,
							CharacterAnimation.getImage(charState.currentImage)
									.getWidth(null)) != 0) {
				if (tryingToRunLeft && tryingToRunRight
						|| !(tryingToRunLeft || tryingToRunRight)) {
					charState.action = Action.STOPPING;
				} else if (tryingToRunLeft) {
					charState.action = Action.RUNNING_LEFT;
				} else {
					charState.action = Action.RUNNING_RIGHT;
				}
			}
			break;
		default:
			break;

		}
		charState.currentImage = charAnimation.getCurrentImageName();
		swordState.currentImage = swordAnimation.getCurrentImageName();
	}
}
