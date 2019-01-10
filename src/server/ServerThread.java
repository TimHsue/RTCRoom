package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerThread extends Thread {
	
	private Socket socket;
	
	private String userName;
	
	private int roomId;
	
	private int uid;
	
	private boolean sendingMessage = false;
	
	public int getUid() {
		return uid;
	}
	
	public String getUserName() {
		return userName;
	}
	
	byte[] buffer = new byte[1024];
	
	public ServerThread(Socket socket) {
		this.socket = socket;
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
	
	public void sendMessage(int uid, String name, String content) {
		synchronized(this) {
			sendingMessage = true;
		}
			BufferedInputStream  socketIn;
			BufferedOutputStream socketOut;
			String response = null;
			
			try {
				socketIn = new BufferedInputStream(socket.getInputStream());
				socketOut = new BufferedOutputStream(socket.getOutputStream());
				// 为消息类型
				socketOut.write(("$MESSAGE").getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				// 用户id与名字
				socketOut.write(("&" + name + "(" + uid + ")").getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				// 文本
				socketOut.write(("&" + content).getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		synchronized(this) {
			sendingMessage = false;
		}
	}
	
	public void run() {
		BufferedInputStream  socketIn;
		BufferedOutputStream socketOut;
		userName = "UnKnown";
		System.out.println("new link built");
	
		try {
			socketIn = new BufferedInputStream(socket.getInputStream());
			socketOut = new BufferedOutputStream(socket.getOutputStream());
			
			while (true) {
				String tmp = null;
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (sendingMessage) continue;
				tmp = read(socketIn);
			
			
				System.out.println("main loop:" + tmp);
				socketOut.write("$Success".getBytes());
				socketOut.write("$END".getBytes());
				socketOut.flush();
				if (tmp.equals("$SETNAME")) {
					tmp = read(socketIn);
					
					userName = tmp.substring(1, tmp.length());
					System.out.println(userName);
					socketOut.write("$Success".getBytes());
					socketOut.write("$END".getBytes());
					socketOut.flush();
					
					uid = ServerClient.uidPool.newId();
					socketOut.write(
							("&" + uid).getBytes());
					socketOut.write("$END".getBytes());
					socketOut.flush();
				} else if (tmp.equals("$SETROOMID")) {
					tmp = read(socketIn);
					
					roomId = Integer.parseInt(tmp.substring(1, tmp.length()));
					socketOut.write("$Success".getBytes());
					socketOut.write("$END".getBytes());
					socketOut.flush();
					synchronized(this) {
						// 将用户绑定到指定房间，更新信息的时候所有用户都会更新
						if (!ServerClient.chatRooms.containsKey(roomId)) {
							ServerClient.chatRooms.put(roomId, 
									new ChatRoom(roomId, 
											ServerClient.ports.newId()));
						}
							ServerClient.chatRooms.get(roomId).addUser(this);
					}
				} else if (tmp.equals("$SENDMESSAGE")) {
					tmp = read(socketIn);
					tmp = tmp.substring(1);
					
					System.out.println("RECIEVE MESSAGE:");
					System.out.println(userName + "(" + uid + ")" + ":" + tmp);
					
					ChatRoom target = ServerClient.chatRooms.get(roomId);
					synchronized(this) {
						target.sendMessage(uid, userName, tmp);
					}
				} else if (tmp.equals("$SENDAUDIO")) {
					
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
