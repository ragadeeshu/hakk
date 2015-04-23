package networking;

import game.CharacterState;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client {
	private String playerName = "Default Name";
	private String address = "";
	private int portNbr = 4444;
	private Socket socket = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;

	public Client(String serverAddress, String playerName) {
		this.playerName = playerName;
		this.connect(serverAddress);
	}

	private void connect(String serverAddress) {
		System.out.println("haba");
		try {
			socket = new Socket(serverAddress, portNbr);
			socket.setTcpNoDelay(true);
			socket.setReceiveBufferSize(Networking.BUFFER_SIZE);
			socket.setSendBufferSize(Networking.BUFFER_SIZE);
			System.out.println("Client socket established");
			outputStream = socket.getOutputStream();
			inputStream = socket.getInputStream();
			send(Networking.CLIENT_HANDSHAKE+";"+playerName);
//			System.out.println("Player name: "+playerName);
//			send(playerName);
			String reply = getUpdate();
//			System.out.println("Reply: "+reply);
			if(!reply.trim().equals(Networking.SERVER_HANDSHAKE))
				System.exit(1);
		} catch (IOException e) {
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
//		System.out.println("sak: " + socket.getInetAddress().getHostName()
//				+ ":"
//				+ ((InetSocketAddress) socket.getLocalSocketAddress())
//						.getPort());
		return socket.getInetAddress().getHostName()
				+ ":"
				+ ((InetSocketAddress) socket.getLocalSocketAddress())
						.getPort();
	}

	public void send(String string) {
		try {
			outputStream.write(string.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getUpdate() {
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
