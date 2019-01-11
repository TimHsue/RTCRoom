package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class RecieveClient extends Thread {
	
	BufferedInputStream socketIn = null;
	
	BufferedOutputStream socketOut = null;
	
	byte[] buffer = new byte[1024];
	
	private String read(BufferedInputStream in) {
		String tmp = "";
		StringBuilder swap = new StringBuilder();
		int len;
		int nowlen = 0;
		try {
			while ((len = in.read(buffer, nowlen, 1)) != -1) {
				nowlen++;
				tmp = new String(buffer, 0, nowlen);
			    if(tmp.indexOf("$END") != -1) {
			    	tmp = tmp.substring(0, tmp.length() - 4);
			    	break;
			    }
			 }
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}  
		
		return tmp;
	}
	
	public RecieveClient(BufferedInputStream in, BufferedOutputStream out) {
		socketIn = in;
		socketOut = out;
	}
	
	public void run() {
		System.out.println("start recieving");
		String message;
		while (true) {
			String userName;
			message = read(socketIn);
			
			if (message.equals("$MESSAGE")) {
				message = read(socketIn);

				userName = message.substring(1);
						
				message = read(socketIn);

				message = message.substring(1);
				synchronized(this) {
					application.Exe.updateMessage(userName, message);
				}				
			} else if (message.equals("$VEDIO")) {
				
			} else if (message.equals("$AUDIO")) {
				
			}
		}
	}
}
