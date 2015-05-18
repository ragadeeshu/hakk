package notifications;

public class DisconnectNotification extends Notification {

	private String player;

	public DisconnectNotification(String player) {
		this.player = player;
	}

	@Override
	protected String getString() {
		return String.format("%s has disconnected.", player);
	}

}
