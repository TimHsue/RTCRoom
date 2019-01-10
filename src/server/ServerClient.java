package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ServerClient {
	
	static ServerSocket server;
	
	static Socket user;
	
	static private int port = 54433;
	
	static LinkedList<ServerThread> threadList;

	static HashMap<Integer, ChatRoom> chatRooms;
	
	static IdPool uidPool = new IdPool(1);
	
	static IdPool ports = new IdPool(54434);
	
	static public void main(String argv[]) {
		threadList = new  LinkedList<ServerThread>();
		chatRooms = new HashMap<Integer, ChatRoom>();
		
		try {
			server = new ServerSocket(port);
			System.out.println("Ready");
			while (true) {
				user = server.accept();
				System.out.println("user accept");
				ServerThread serverThread = new ServerThread(user);
				threadList.add(serverThread);
				serverThread.start();
				
				Iterator<ServerThread> it = threadList.iterator();
				while (it.hasNext()) {
					ServerThread next = it.next();
					if (!next.isAlive()) threadList.remove(next);
				}
						
				System.out.println("user count:" + threadList.size());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
