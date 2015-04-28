package networking;

import game.CharacterAnimation;
import game.CharacterState;
import game.Sword;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private ArrayList<RequestHandler> handlers;
	private HashMap<String, CharacterState> characterStates;
	private HashMap<String, Sword> swordStates;
	private HashMap<String, String> playerNames;
	private HashMap<String, String> playerAnimations;

	public Server() {
		characterStates = new HashMap<String, CharacterState>();
		swordStates = new HashMap<String, Sword>();
		playerNames = new HashMap<String, String>();
		playerAnimations = new HashMap<String, String>();
		handlers = new ArrayList<>();
		BitMaskResources.init();

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
				synchronized (this) {

					socket.setTcpNoDelay(true);
					socket.setReceiveBufferSize(Networking.BUFFER_SIZE);
					socket.setSendBufferSize(Networking.BUFFER_SIZE);
					InputStream inputStream = socket.getInputStream();
					OutputStream outputStream = socket.getOutputStream();
					String clientHandshake = Networking.getUpdate(inputStream);
					while (!clientHandshake.trim().startsWith(
							Networking.CLIENT_HANDSHAKE)) {
						System.out.println("Waiting for handshake");
						clientHandshake = Networking.getUpdate(inputStream);
					}
					System.out.println("ch " + clientHandshake);
					System.out.println("chs "
							+ clientHandshake
									.split(Networking.SEPARATOR_ATTRIBUTE)[1]);
					String clientIdentity = socket.getInetAddress()
							.getHostName() + ":" + socket.getPort();
					String playerName = clientHandshake
							.split(Networking.SEPARATOR_ATTRIBUTE)[1];
					playerNames.put(clientIdentity, playerName);
					Random rng = new Random();
					playerAnimations
							.put(clientIdentity,
									CharacterAnimation.BASENAMES[rng
											.nextInt(CharacterAnimation.BASENAMES.length)]);
					Networking.send(outputStream, Networking.SERVER_HANDSHAKE);
					RequestHandler handler = new RequestHandler(socket, this);
					handlers.add(handler);
					pool.submit(handler);
					for (RequestHandler h : handlers) {
						h.flagNewConnect();
					}
				}
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

	public synchronized void updateCharacterState(String inetAddress,
			String state) {
		characterStates.put(inetAddress, new CharacterState(state));

	}

	public synchronized void updateSwordState(String inetAddress, String state) {
		Sword s = new Sword(state);
		swordStates.put(inetAddress, s);
		for (CharacterState chState : characterStates.values()) {
			if (chState.isHit(s)) {
				for (RequestHandler handler : handlers) {
					handler.putDeath(inetAddress, chState.x, chState.y);
				}
				chState.y=-100;
			}
		}
	}

	public synchronized String getStates() {
		StringBuilder sb = new StringBuilder();
		for (Entry<String, CharacterState> e : characterStates.entrySet()) {
			sb.append(Networking.SEPARATOR_PLAYER);
			sb.append(e.getKey());
			sb.append(Networking.SEPARATOR_STATE);
			sb.append(e.getValue().toString().trim());
		}
		sb.append(Networking.SEPARATOR_MESSAGES);
		return sb.substring(1);
	}

	public synchronized String getName(String address) {
		return playerNames.get(address);
	}

	public synchronized void disconnect(String string) {
		characterStates.remove(string);
		playerNames.remove(string);
		for (RequestHandler h : handlers) {
			h.flagNewDisconnect();
		}
	}

	public synchronized String getNameMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(Networking.MESSAGE_NEWPLAYER);
		sb.append(Networking.SEPARATOR_STATE);

		for (Entry<String, String> e : playerNames.entrySet()) {
			sb.append(e.getKey()); // ip
			sb.append(Networking.SEPARATOR_ATTRIBUTE);
			sb.append(e.getValue().trim()); // name
			sb.append(Networking.SEPARATOR_ATTRIBUTE);
			sb.append(playerAnimations.get(e.getKey()).trim()); // anim
			sb.append(Networking.SEPARATOR_PLAYER);
		}
		System.out.println("getNameMessage: "
				+ sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER)));
		return sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER));
	}

	public synchronized String getDisconnectMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(Networking.MESSAGE_DISCONNECT);
		sb.append(Networking.SEPARATOR_STATE);

		for (String ip : playerNames.keySet()) {
			sb.append(ip);
			sb.append(Networking.SEPARATOR_PLAYER);
		}
		System.out.println("getDisconnectMessage: "
				+ sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER)));
		return sb.substring(0, sb.lastIndexOf(Networking.SEPARATOR_PLAYER));
	}

}
