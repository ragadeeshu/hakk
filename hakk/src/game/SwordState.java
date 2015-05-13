package game;

import graphics.SwordAnimation;
import networking.Networking;

public class SwordState {
//	private Character wielder;
	private double x;
	private double y;
	public String currentImage;
	private int swordOffsetY = -4;
	private int[] swordOffsetX = new int[]{10, -36};
	public int left = 0;
	
	public Action action = Action.STOPPING;

	public SwordState(double x, double y) {
		this.x = x + swordOffsetX[left];
		this.y = y + swordOffsetY;;
		currentImage = SwordAnimation.BASENAMES[0] + String.format("%03d", 0) + ".png";
	}

	public SwordState(String state) {
		String[] s = state.split(Networking.SEPARATOR_ATTRIBUTE);

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.currentImage = s[2];
	}

	public void move(double nx, double ny) {
		x = nx + swordOffsetX[left];
		y = ny + swordOffsetY;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(y);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(currentImage);
		return sb.toString();
	}

	public String currentImage() {
		return currentImage;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

}
