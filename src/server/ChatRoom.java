package server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ChatRoom {
	private int roomId;
	
	static ServerSocket server;
	
	static private int port;
	
	static LinkedList<ServerThread> users;
	
	public void addUser(ServerThread user) {
		users.add(user);
		String message = user.getUserName() + "进入房间";
		System.out.println(roomId + ":" + message);
		updateMessage(0, "admin", message);
	}
	
	public void sendMessage(int uid, String name, String message) {
		updateMessage(uid, name, message);
	}
	
	private void updateMessage(int uid, String name, String message) {
		for (ServerThread user : users) {
			if (user.getUid() == uid) continue;
			user.sendMessage(uid, name, message);
		}
	}
	
	public ChatRoom(int roomId, int port) {
		this.port = port;
		this.roomId = roomId;
		users = new LinkedList<ServerThread>();
	}
}
