package networking;

import game.CharacterState;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

public class Client {

	private String address = "";
	private int portNbr = 4444;
	private Socket socket = null;
//	private ObjectInputStream inputStream = null;
//	private ObjectOutputStream outputStream = null;

	public Client(String serverAddress) {
		this.connect(serverAddress);
	}

	private void connect(String serverAddress) {
		System.out.println("haba");
		try {
			socket = new Socket(serverAddress, portNbr);
			System.out.println("Client socket established");
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
		return socket.getLocalAddress().toString();
	}

	public void send(CharacterState state) {
		try {
			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(state);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, CharacterState> getUpdate() {
		try {
			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			return (HashMap<String, CharacterState>) inputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
