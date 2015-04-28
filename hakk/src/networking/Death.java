package networking;


public class Death {
	private double x, y;
	private String identification;

	public Death(String inetAddress, double x, double y) {
		this.x = x;
		this.y = y;
		this.identification = inetAddress;
	}

	public String message() {
		StringBuilder sb = new StringBuilder();
		

		sb.append(identification);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(x);
		sb.append(Networking.SEPARATOR_ATTRIBUTE);
		sb.append(y);

		return sb.toString().trim();

	}

}
