package game;

import graphics.SwordAnimation;

import java.awt.Rectangle;

import networking.BitMask;
import networking.BitMaskResources;
import networking.Networking;

public class CharacterState {
	public double x;
	public double y;

	public double xspeed;
	public double yspeed;
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
		x = Math.random() * HakkStage.LEVEL_WIDTH;
		y = HakkStage.HEIGHT - HakkStage.LEVEL_HEIGHT -200;
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

	public boolean isHit(SwordState s) {
		if (x < HakkStage.WIDTH) {
			return (isHit(x, y, s) || isHit(x + HakkStage.LEVEL_WIDTH, y, s));

		} else if (x > HakkStage.LEVEL_WIDTH - HakkStage.WIDTH) {
			return (isHit(x, y, s) || isHit(x - HakkStage.LEVEL_WIDTH, y, s));
		} else {
			return isHit(x, y, s);
		}

	}

	private boolean isHit(double chx, double chy, SwordState s) {
		BitMask characterMask = BitMaskResources.getBitmask(currentImage);
		BitMask swordMask = BitMaskResources.getBitmask(s.currentImage());

		int intx = (int) Math.round(chx);
		int inty = (int) Math.round(chy) - characterMask.getHeight();
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
		x = Math.random() * HakkStage.LEVEL_WIDTH;
		y = HakkStage.HEIGHT - HakkStage.LEVEL_HEIGHT-200;
		xspeed = 0;
		yspeed = 0;
		action = Action.STOPPING;

	}
}
