package game;

import java.awt.Rectangle;

import networking.BitMask;
import networking.BitMaskResources;
import networking.Networking;

public class CharacterState {
	public double x = Math.random() * 900;
	public double y = -200;

	public double xspeed = 0;
	public double yspeed = 0;
	public Action action;
	public String currentImage;

	public CharacterState(String state) {
		// System.out.println("State: " + state);
		String[] s = state.split(Networking.SEPARATOR_ATTRIBUTE);

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.xspeed = Double.parseDouble(s[2]);
		this.yspeed = Double.parseDouble(s[3]);
		this.action = Action.values()[Integer.parseInt(s[4])];
		this.currentImage = s[5];
	}

	public CharacterState(Action action, String currentImage) {
		this.action = action;
		this.currentImage = currentImage;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x + Networking.SEPARATOR_ATTRIBUTE);
		sb.append(y + Networking.SEPARATOR_ATTRIBUTE);
		sb.append(xspeed + Networking.SEPARATOR_ATTRIBUTE);
		sb.append(yspeed + Networking.SEPARATOR_ATTRIBUTE);
		sb.append(action.ordinal() + Networking.SEPARATOR_ATTRIBUTE);
		sb.append(currentImage);
		return sb.toString();
	}

	public boolean isHit(Sword s) {

		BitMask characterMask = BitMaskResources.getBitmask(currentImage);
		BitMask swordMask = BitMaskResources.getBitmask(s.getImage());

		int intx = (int) Math.round(x);
		int inty = (int) Math.round(y) - characterMask.getHeight();
		int intsx = (int) Math.round(s.getX());
		int intsy = (int) Math.round(s.getY()) - swordMask.getHeight();

		Rectangle box1 = new Rectangle(intx, inty, characterMask.getWidth(),
				characterMask.getHeight());
		Rectangle box2 = new Rectangle(intsx, intsy, swordMask.getWidth(),
				swordMask.getHeight());
		if (box1.intersects(box2)) {
			return characterMask.overlaps(intx, inty, intsx, intsy, swordMask);
		}
		return false;
	}

	public void reSpawn() {
		x = Math.random() * 900;
		y = -200;
		xspeed = 0;
		yspeed = 0;
		action = Action.STOPPING;

	}
}
