package networking;

import game.CharacterState;
import game.Sword;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private HashMap<String, CharacterState> characterStates;
	private HashMap<String, Sword> swordStates;
	private HashMap<String, String> playerNames;

	public Server() {
		characterStates = new HashMap<String, CharacterState>();
		swordStates = new HashMap<String, Sword>();
		playerNames = new HashMap<String, String>();

		ServerSocket serverSocket = null;
		int portNbr = 4444;

		try {
			serverSocket = new ServerSocket(portNbr);
		} catch (IOException e) {
			System.out.println(e);
			System.exit(1);
		}

		System.out.println("Server setup successfully.");
		System.out.println("Waiting for clients to connect...");

		ExecutorService pool = Executors.newFixedThreadPool(4);

		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				socket.setTcpNoDelay(true);
				socket.setReceiveBufferSize(Networking.BUFFER_SIZE);
				socket.setSendBufferSize(Networking.BUFFER_SIZE);
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				String clientHandshake = Networking.getUpdate(inputStream);
				while(!clientHandshake.trim().startsWith(Networking.CLIENT_HANDSHAKE)){
					System.out.println("Waiting for handshake");
					clientHandshake = Networking.getUpdate(inputStream);
				}
				String playerName = clientHandshake.split(";")[1];
				playerNames.put(socket.getInetAddress()
						.getHostName() + ":" + socket.getPort(), playerName);
				Networking.send(outputStream, Networking.SERVER_HANDSHAKE);
				pool.submit(new RequestHandler(socket, this));
			} catch (IOException e) {
				System.out.println(e);
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(1);
			}
		}

	}

	public static void main(String[] args) {
		Server server = new Server();
	}

	public synchronized void updateCharacterState(String inetAddress, String state) {
		characterStates.put(inetAddress, new CharacterState(state));

	}
	
	public synchronized void updateSwordState(String inetAddress, String state) {
		Sword s = new Sword(state);
		swordStates.put(inetAddress, s);
		for(CharacterState chState : characterStates.values()){
			if(chState.isHit(s)){
				//System.out.println("du dog!");
			}
		}
	}

	public synchronized String getStates() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, CharacterState> e : characterStates.entrySet()) {
			sb.append(";");
			sb.append(e.getKey());
			sb.append("%");
			sb.append(e.getValue().toString().trim());
		}
		sb.append(":END");
		return sb.substring(1);
	}
	
	public synchronized String getName(String address){
		return playerNames.get(address);
	}

	public synchronized void disconnect(String string) {
		characterStates.remove(string);
	}

}
