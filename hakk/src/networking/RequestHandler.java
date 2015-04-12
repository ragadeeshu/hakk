package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class RequestHandler implements Runnable {

	private Socket socket;
	
	public RequestHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		
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
		
		try{
			socket.close();
		} catch(IOException e){
			System.out.println(e);
		}
		
	}
	
}
