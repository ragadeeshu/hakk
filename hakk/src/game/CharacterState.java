package game;


public class CharacterState {
	public double x = 0;
	public double y = 0;
	public double xspeed = 0;
	public double yspeed = 0;
	public Action action;
	public String currentImage; 

	public CharacterState(String state) {
		// System.out.println("State: " + state);
		String[] s = state.split(":");

		this.x = Double.parseDouble(s[0]);
		this.y = Double.parseDouble(s[1]);
		this.xspeed = Double.parseDouble(s[2]);
		this.yspeed = Double.parseDouble(s[3]);
		this.action = Action.values()[Integer.parseInt(s[4])];
		this.currentImage=s[5];
	}

	public CharacterState() {
		action = Action.STOPPING;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(x + ":");
		sb.append(y + ":");
		sb.append(xspeed + ":");
		sb.append(yspeed + ":");
		sb.append(action.ordinal() + ":");
		sb.append(currentImage);
		return sb.toString();
	}

}
