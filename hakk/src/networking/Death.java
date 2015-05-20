package networking;

public class Death {
	private double x, y;
	private String identification;
	private String murderer;
	private int score;

	public Death(String inetAddress, String murderer, int score, double x,
			double y) {
		this.x = x;
		this.y = y;
		this.identification = inetAddress;
		this.murderer = murderer;
		this.score = score;
	}

	public String message() {
		StringBuilder sb = new StringBuilder();

		sb.append(identification);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(murderer);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(x);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(y);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(score);

		return sb.toString().trim();

	}

}
