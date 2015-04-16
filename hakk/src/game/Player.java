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
	private PlayerAnimation animation;

	public Player(HakkStage stage) {
		super();
		animation = new PlayerAnimation();
		state.action = Action.STOPPING;
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
		if (state.action != Action.IN_AIR)
			state.action = Action.RUNNING_LEFT;
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
			if (state.y > 299.99)
				if (tryingToRunLeft && tryingToRunRight
						|| !(tryingToRunLeft || tryingToRunRight)) {
					state.action = Action.STOPPING;
				} else if (tryingToRunLeft)
					state.action = Action.RUNNING_LEFT;
				else
					state.action = Action.RUNNING_RIGHT;
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
		g2d.setColor(Color.BLACK);
		g2d.drawImage(animation.getImage(), intx, inty, null);
		// g2d.fillOval(intx, inty, 30, 50);
	}
}
