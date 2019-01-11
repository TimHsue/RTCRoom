package application;

import com.jfoenix.controls.JFXButton;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController {
	@FXML
	private AnchorPane back;
	@FXML
	private ImageView backGround;
	@FXML
	private TextField name;
	@FXML
	private Text nameTitle;
	@FXML
	private Text roomTitle;
	@FXML
	private TextField roomID;
	
	private JFXButton btn;
	
	private String nameTmp = null;
	
	private String idTmp = null;
	
	private boolean active = false;
	
	
	public void init() {
		// TODO Auto-generated method stub
		btn = new JFXButton();
		btn.setStyle("-fx-background-color: #D6D8D9;" +
		"-jfx-button-type: RAISED;" +
	    "-fx-pref-height: 28px;" +
	    "-fx-pref-width: 60px;" +
	    "-fx-min-width: -fx-pref-width;" +
	    "-fx-max-width: -fx-pref-width;" +
	    "-fx-min-height: -fx-pref-height;" +
	    "-fx-max-height: -fx-pref-height;" );
		btn.setText("Login");
		btn.setLayoutX(250);
		btn.setLayoutY(190);
		btn.setOnAction(this::log);
		
		back.getChildren().add(btn);
	}
	
	public void log(ActionEvent e) {
		nameTmp = name.getText();
		idTmp = roomID.getText();
		active = true;
		System.out.println(nameTmp);
		System.out.println(idTmp);
		Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
        Main m = new Main();
        m.setRoomId(idTmp);
        m.setUserName(nameTmp);
        Stage a = new Stage();
        try {
			m.start(a);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public String getName() {
		while (! active)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return nameTmp;
	}
	
	public int getId() {
		while (! active)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		return Integer.parseInt(idTmp);
	}

}
