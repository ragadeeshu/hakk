package game;

import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;

public class Player extends Character {

	public Player(HakkStage stage) {
		super();
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent e) {
						System.out.println("Got key event!");
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
								
								break;
							case KeyEvent.VK_RIGHT:
								
								break;
							}
						}
						return false;
					}

				});
		// addBindings(stage);
	}

	private void runLeft() {
		if (y >= 299) {
			if (xspeed > -5)
				xspeed -= 1;
			else
				xspeed = -5;
		}
	}

	protected void runRight() {
		if (y >= 299) {
			if (xspeed < 5)
				xspeed += 1;
			else
				xspeed = 5;
		}
	}

	protected void jump() {
		if (y >= 299) {
			yspeed -= 10;
			System.out.println("did a jump");
			System.out.println("yspeed is:" + yspeed);
		}

	}

	@Override
	public void doPhysics() {
		doGravity();
		doMovement();
	}

	@Override
	public void draw(Graphics2D g2d) {
		int intx = (int) Math.round(x);
		int inty = (int) Math.round(y);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.fillOval(intx, inty, 30, 30);
	}

	// protected void addBindings(HakkStage stage) {
	// InputMap inputMap = stage.getInputMap();
	//
	// // Ctrl-b to go backward one character
	// KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
	// inputMap.put(key, this.BackAction());
	//
	// // Ctrl-f to go forward one character
	// key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
	// inputMap.put(key, DefaultEditorKit.forwardAction);
	//
	// // Ctrl-p to go up one line
	// key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
	// inputMap.put(key, DefaultEditorKit.upAction);
	//
	// // Ctrl-n to go down one line
	// key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
	// inputMap.put(key, DefaultEditorKit.downAction);
	// }
	//
	// private Action BackAction() {
	// // TODO Auto-generated method stub
	// return new Action;
	// }
}
