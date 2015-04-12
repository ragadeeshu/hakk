package networking;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
	
	public Server(){
		
		ServerSocket serverSocket = null;
		int portNbr = 4444;
		
		try{
			serverSocket = new ServerSocket(portNbr);
		} catch(IOException e){
			System.out.println(e);
			System.exit(1);
		}
		
		System.out.println("Server setup successfully.");
		System.out.println("Waiting for clients to connect...");
		
		ExecutorService pool = Executors.newFixedThreadPool(4);
		
		while(true){
			Socket socket = null;
			try{
				socket = serverSocket.accept();
				pool.submit(new RequestHandler(socket));
			} catch(IOException e){
				System.out.println(e);
				System.exit(1);
			}
		}
		
	}
	
	public static void main(String[] args) {
		Server server = new Server();
	}

}
