package game;

import networking.Networking;

public class SwordState {
//	private Character wielder;
	private double x;
	private double y;
	private String currentImage;

	public SwordState(double x, double y) {
		this.x = x;
		this.y = y;
		currentImage = "sword_attacking_left___000.png";
	}

	public SwordState(String state) {
		String[] s = state.split(Networking.SEPARATOR_ATTRIBUTE);

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.currentImage = s[2];
	}

	public void move(double nx, double ny) {
		x = nx;
		y = ny;
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
