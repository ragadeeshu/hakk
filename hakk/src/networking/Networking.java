package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class Networking {
	public static final int BUFFER_SIZE = 1024;
	public static final String CLIENT_HANDSHAKE = "C_CONNECT";
	public static final String SERVER_HANDSHAKE = "S_CONNECT";
	
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
