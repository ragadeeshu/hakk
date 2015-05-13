package notifications;

public class ConnectNotification extends Notification {

	private String player;

	public ConnectNotification(String player) {
		this.player = player;
	}

	@Override
	protected String getString() {
		return String.format("%s has connected.", player);
	}

}
