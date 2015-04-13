package game;

import java.io.Serializable;

public class CharacterState implements Serializable {
	private static final long serialVersionUID = 1L;
	public double x = 0;
	public double y = 0;
	public double xspeed = 0;
	public double yspeed = 0;
	public Action action;
}
