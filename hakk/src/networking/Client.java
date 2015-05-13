package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	private String myIP = "127.0.0.1";
	private String playerName = "Default Name";
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	public Client(String serverAddress, String playerName) {
		this.playerName = playerName;
		this.connect(serverAddress);
	}

	private void connect(String serverAddress) {
		try {
			socket = new Socket(serverAddress, Networking.PORT);
			socket.setTcpNoDelay(true);
			socket.setReceiveBufferSize(Networking.BUFFER_SIZE);
			socket.setSendBufferSize(Networking.BUFFER_SIZE);
			System.out.println("Client socket established");
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			send(Networking.CLIENT_HANDSHAKE + Networking.SEPARATOR_ATTRIBUTE
					+ playerName);
			String reply = getUpdate();
			System.out.println("Reply: " + reply);
			String[] splatReply = reply.split(Networking.SEPARATOR_ATTRIBUTE);
			if (!splatReply[0].equals(Networking.SERVER_HANDSHAKE))
				System.exit(1);
			myIP = splatReply[1].trim();
		} catch (IOException e) {
			disconnect();
			System.out.println(e);
			System.exit(1);
		}
		System.out.println("Connected to server successfully.");
	}

	private void disconnect() {
		try {
			socket.close();
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}
	}

	public String getAddress() {
		return myIP
				+ ":"
				+ ((InetSocketAddress) socket.getLocalSocketAddress())
						.getPort();
	}

	public void send(String string) {
		try {
			outputStream.write(string.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			System.out.println("Disconnected from server");
			System.exit(1);
		}
	}

	public String getUpdate() {
		try {
			byte[] bytes = new byte[Networking.BUFFER_SIZE];
			inputStream.read(bytes);
			return new String(bytes);
		} catch (IOException e) {
			System.out.println("Disconnected from server");
			System.exit(1);
		}
		return null;
	}

}
