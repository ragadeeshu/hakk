package start;

import game.HakkThread;

import java.util.Scanner;

import networking.Client;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		Scanner scan = new Scanner(System.in);
		String serverAddress = scan.nextLine();
		Client client = new Client(serverAddress);
		new HakkThread(client).start();

	}
}
