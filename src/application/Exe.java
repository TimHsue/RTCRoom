package application;

import java.util.LinkedList;
import java.util.Scanner;

import client.ServerLinker;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Exe extends Application {
	
	public static LoginController loginController;
	
	public static Scene1Controller sceneController;
	
	private Parent root1;
	
	private Scene scene;
	
	static ServerLinker baseLink;
	
	static private String host = "localhost";
	
	static private int port = 54433;
	
	static private String userName = null;
	
	static private int roomId = -1;
	
	static Scanner scan = new Scanner(System.in);
	
	static private LinkedList<Info> messageList;
	
	static public String getMessage() {
		while (!Main.active)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return Main.myController.getMessage();
	}
	
	static public void updateMessage(String name, String message) {
		while (!Main.active)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		Platform.runLater(new Runnable() {
		    @Override
		    public void run() {
		    	Main.myController.updateMessage(name, message);
		    }
		});
		
	}
	
	static public String getName() {
		return loginController.getName();
	}
	
	static public int getId() {
		// System.out.println(roomId);
		return loginController.getId();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		messageList = new LinkedList<Info>();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
    	root1 = loader.load();
    	loginController = (LoginController)loader.getController();
		baseLink = new ServerLinker(host, port, ServerLinker.BASE);
		baseLink.start();

        scene = new Scene(root1,406, 248);
        
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(350.0);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");
        
        loginController.init();
        primaryStage.show();
        
	}
	
	public static void main(String args[]) {
		launch(args);
	}

}
