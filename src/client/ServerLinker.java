package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerLinker extends Thread {
	
	static public final int BASE = 0;
	
	// static public final int ORDER = 1;
	
	// static public final int SEND = 1;
	
	// static public final int RECIEVE = 2;
	
	private Socket client;
	
	private int type;
	
	private String host;
	
	private int port;
	
	private String userName = null;
	
	private int roomId = -1;
	
	byte[] buffer = new byte[1024];
	
	private int uid = -1;
	
	public ServerLinker(String host, int port, int type) {
		this.host = new String(host);
		this.port = port;
		this.type = type;
	}
	
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
	
	private void baseLink() {
		BufferedInputStream socketIn = null;
		BufferedOutputStream socketOut = null;
		String response = null;

		try {
			client = new Socket(host, port);
			socketIn = new BufferedInputStream(client.getInputStream());
			socketOut = new BufferedOutputStream(client.getOutputStream());
			
			// set name
			do {
				System.out.println("get name");
				userName = application.Exe.getName();
		
				System.out.println("Send name:" + userName);
				socketOut.write("$SETNAME".getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				response = read(socketIn);
				if (!response.equals("$Success")) continue;
				socketOut.write(("&" + userName).getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				response = read(socketIn);
			} while (!response.equals("$Success"));
			response = read(socketIn);
			uid = Integer.parseInt(response.substring(1, response.length()));
			System.out.println("你的ID是" + uid);
			
			
			// set room
			do { 
				roomId = application.Exe.getId();
				
				socketOut.write("$SETROOMID".getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				response = read(socketIn);
				if (!response.equals("$Success")) continue;
				socketOut.write(("&" + roomId).getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				response = read(socketIn);
			} while (!response.equals("$Success"));
			System.out.println("已成功连接到ROOM" + roomId);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		RecieveClient recieveClient = new RecieveClient(socketIn, socketOut);
		recieveClient.start();
		
		SendClient sendClient = new SendClient(socketIn, socketOut);
		sendClient.start();
		
		while(true);
		// 
	}

	public void run() {
		baseLink();
	}

}
