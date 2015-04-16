package start;

import game.HakkStage;
import game.HakkThread;
import game.UpdateThread;

import java.util.Scanner;

import networking.Client;

public class Launcher {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Enter server address: ");
		Scanner scan = new Scanner(System.in);
//		String serverAddress = scan.nextLine();
		String serverAddress = "127.0.0.1";
		Client client = new Client(serverAddress);
		HakkStage stage = new HakkStage();
		new HakkThread(stage).start();
		new UpdateThread(client, stage).start();
	}
}
