package networking;

import java.io.*;
import java.net.*;

public class Server {
	
	public static void main(String[] args) {
		Server server = new Server();
	}
	
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
		
		while(true){
			Socket socket = null;
			try{
				socket = serverSocket.accept();
			} catch(IOException e){
				System.out.println(e);
				System.exit(1);
			}
			try{
				System.out.println("New connection from "+socket.getInetAddress().getHostName()+":"+socket.getPort()+". Local port: "+socket.getLocalPort());
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				int ch = inputStream.read();
				while(ch!=-1){
					System.out.println(ch);
					outputStream.write(Character.toUpperCase((char) ch));
					ch = inputStream.read();
				}
			} catch(IOException e){
				System.out.println(e);
			}
		}
		
	}

}
