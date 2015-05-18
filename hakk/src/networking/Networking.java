package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Networking {
	public static final String SEPARATOR_PLAYER = ">";
	public static final String SEPARATOR_ATTRIBUTE = "#";
	public static final String SEPARATOR_STATE = "%";
	public static final String SEPARATOR_SWORD = "~";
	public static final String SEPARATOR_MESSAGES = "&";
	public static final String SEPARATOR_MESSAGE = "@";

	public static final int BUFFER_SIZE = 2048;
	public static final int PORT = 13577;

	public static final String CLIENT_HANDSHAKE = "CHS_CONN";
	public static final String SERVER_HANDSHAKE = "SHS_CONN";
	public static final String MESSAGE_NEWPLAYER = "MSG_NEWP";
	public static final String MESSAGE_ANIMATION = "MSG_ANIM";
	public static final String MESSAGE_DISCONNECT = "MSG_DISC";
	public static final String MESSAGE_DEATH = "MSG_DIED";


	public static void send(OutputStream outputStream, String string) {
		try {
			outputStream.write(string.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getUpdate(InputStream inputStream) {
		try {
			byte[] bytes = new byte[Networking.BUFFER_SIZE];
			inputStream.read(bytes);
			return new String(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
