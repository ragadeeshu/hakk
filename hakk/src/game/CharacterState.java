package game;

import java.io.Serializable;

public class CharacterState implements Serializable {
	public CharacterState(String state) {
//		System.out.println("State: " + state);
		String[] s = state.split(":");

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.xspeed = Double.parseDouble(s[2]);
		this.yspeed = Double.parseDouble(s[3]);
		this.action = Action.values()[Integer.parseInt(s[4])];
	}

	public CharacterState() {
		action = Action.STOPPING;
	}

	public String toString() {
		return x + ":" + y + ":" + xspeed + ":" + yspeed + ":"
				+ action.ordinal();
	}

	private static final long serialVersionUID = 1L;
	public double x = 0;
	public double y = 0;
	public double xspeed = 0;
	public double yspeed = 0;
	public Action action;
}
