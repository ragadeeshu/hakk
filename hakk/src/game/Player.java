package game;

import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

public class Player extends Character {
	private Action action;
	private boolean tryingToRunLeft;
	private boolean tryingToRunRight;

	public Player(HakkStage stage) {
		super();
		action = Action.STOPPING;
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
								System.out.println("Got key event!");
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
		if (action != Action.IN_AIR) {
			if (!tryingToRunRight) {
				action = Action.STOPPING;
			} else
				action = Action.RUNNING_RIGHT;
		}

	}

	private void stopRight() {
		tryingToRunRight = false;
		if (action != Action.IN_AIR) {
			if (!tryingToRunLeft) {
				action = Action.STOPPING;
			} else
				action = Action.RUNNING_LEFT;
		}

	}

	private void runLeft() {
		tryingToRunLeft = true;
		if (action != Action.IN_AIR)
			action = Action.RUNNING_LEFT;
	}

	protected void runRight() {
		tryingToRunRight = true;
		if (action != Action.IN_AIR)
			action = Action.RUNNING_RIGHT;
	}

	protected void jump() {
		if (action != Action.IN_AIR)
			action = Action.JUMPING;

	}

	@Override
	public void doPhysics() {
		doAction();
		doGravity();
		doMovement();
	}

	private void doAction() {
		switch (action) {
		case JUMPING:

			yspeed -= 10;
			System.out.println("did a jump");
			System.out.println("yspeed is:" + yspeed);
			action = Action.IN_AIR;

			break;
		case RUNNING_LEFT:

			if (xspeed > -5)
				xspeed -= 1;
			else
				xspeed = -5;

			break;
		case RUNNING_RIGHT:

			if (xspeed < 5)
				xspeed += 1;
			else
				xspeed = 5;

			break;
		case STOPPING:
			if (xspeed != 0) {
				xspeed += xspeed * -0.7;
			}

			break;
		case IN_AIR:
			if (y > 299.99)
				if (tryingToRunLeft && tryingToRunRight
						|| !(tryingToRunLeft || tryingToRunRight)) {
					action = Action.STOPPING;
				} else if (tryingToRunLeft)
					action = Action.RUNNING_LEFT;
				else
					action = Action.RUNNING_RIGHT;
			break;
		default:
			break;

		}

	}

	@Override
	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(x);
		int inty = (int) Math.round(y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(intx, inty, 30, 50);
	}
}
