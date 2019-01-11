package application;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;  
  
public class Main extends Application {  
	
	public static Scene1Controller myController;
	
	private Parent root1;
	
	private Scene scene;
	
	private String roomId = null;
	
	private String userName = null;
  
    public static boolean active = false;
    
    public void setRoomId(String tar) {
    	roomId = "[" + tar + "]";
    }
    
    public void setUserName(String tar) {
    	userName = tar;
    }
	
	@Override  
    public void start(Stage primaryStage) throws Exception {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
    	root1 = loader.load();
    	myController = (Scene1Controller)loader.getController();
    	
        scene = new Scene(root1, 440, 598);
        scene.widthProperty().addListener(this::process);
        scene.setOnKeyPressed(this::send);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(350.0);
        primaryStage.setResizable(true);
        primaryStage.setTitle("Chatting Room Final");
        
        myController.init(roomId, userName);
        primaryStage.show();
        
        active = true;
    }  
 
    public static void main(String[] args) {  
        launch(args);  
    } 
    
    public void process(ObservableValue<? extends Number> property, 
    		Object oldValue, Object newValue)  {
    		myController.dragMe();
    }
    
    public void send(KeyEvent event) {
    	
    	if (event.getCode() == KeyCode.ENTER  && event.isControlDown()){
    		ActionEvent e = new ActionEvent();
    		myController.showMyMessage(e);
    		
    	} 
    }
    
}  
