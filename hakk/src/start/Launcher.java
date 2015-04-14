package start;

import game.HakkThread;

import java.util.Scanner;

import networking.Client;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Enter server address: ");
		Scanner scan = new Scanner(System.in);
//		String serverAddress = scan.nextLine();
		String serverAddress = "localhost";
		Client client = new Client(serverAddress);
		new HakkThread(client).start();

	}
}
