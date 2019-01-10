package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

import server.ChatRoom;
import server.ServerClient;

public class SendClient extends Thread {
	
	BufferedInputStream socketIn = null;
	
	BufferedOutputStream socketOut = null;
	
	byte[] buffer = new byte[1024];
	
	public SendClient(BufferedInputStream in, BufferedOutputStream out) {
		socketIn = in;
		socketOut = out;
	}
	
	public void run() {
		String message;
		String response = null;
		
		while (true) {
			message = Client.getMessage();
			try {
				socketOut.write("$SENDMESSAGE".getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				
				socketOut.write(("&" + message).getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
