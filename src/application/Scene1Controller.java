package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXNodesList;
import javafx.event.ActionEvent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class Scene1Controller {
	@FXML
	private AnchorPane backGround;
	@FXML
	private AnchorPane sendGround;
	@FXML
	private TextArea sendText;
	@FXML
	private Button sendButton;
	@FXML
	private VBox userTitle;
	@FXML
	private Text userName;
	@FXML
	private AnchorPane myMessage;
	@FXML
	private Text myName;
	@FXML
	private Pane myBubblePane;
	@FXML
	private Group myBubble;
	@FXML
	private Circle myBubbleShape1;
	@FXML
	private Rectangle myBubbleShape3;
	@FXML
	private Circle myBubbleShape2;
	@FXML
	private Rectangle myBubbleShape4;
	@FXML
	private Text myText;
	@FXML
	private AnchorPane yourMessage;
	@FXML
	private Text yourName;
	@FXML
	private Pane yourBubblePane;
	@FXML
	private Group yourBubble;
	@FXML
	private Circle yourBubbleShape1;
	@FXML
	private Rectangle yourBubbleShape3;
	@FXML
	private Circle yourBubbleShape2;
	@FXML
	private Rectangle yourBubbleShape4;
	@FXML
	private Text yourText;
	@FXML
	private ScrollPane chatGround;
	@FXML
	private AnchorPane chatBoard;
	@FXML
	private VBox myChatBoard;
	@FXML
	private VBox yourChatBoard;
	
	private Pane pane;
	
	private double lastLineNum = 1;
	
	private boolean mePostLast = true;
	
	private JFXNodesList list;
	
	private List<Integer> myNum;
	
	private List<Integer> yourNum;
	
	private boolean active = false;
	
	private String inputText;
	
	
	public void init() {
		myNum = new ArrayList<Integer>();
		yourNum = new ArrayList<Integer>();
	
		JFXButton head = new JFXButton();
		head.setStyle("-fx-background-color: #D6D8D9;" +
		"-jfx-button-type: FLAT;" +
	    "-fx-background-radius: 35px;" +
	    "-fx-pref-height: 35px;" +
	    "-fx-pref-width: 35px;" +
	    "-fx-min-width: -fx-pref-width;" +
	    "-fx-max-width: -fx-pref-width;" +
	    "-fx-min-height: -fx-pref-height;" +
	    "-fx-max-height: -fx-pref-height;" + 
	    "-fx-background-radius: 35;" +
	    "-fx-border-color: FFFFFF;" + 
	    "-fx-border-width: 2;" + 
	    "-fx-border-radius: 38;");
		head.setText("+");
		
		JFXButton music = new JFXButton();
		music.setText("M");
		music.setStyle("-fx-background-color: #D6D8D9;" +
				"-jfx-button-type: RAISED;" +
			    "-fx-background-radius: 35px;" +
			    "-fx-pref-height: 35px;" +
			    "-fx-pref-width: 35px;" +
			    "-fx-min-width: -fx-pref-width;" +
			    "-fx-max-width: -fx-pref-width;" +
			    "-fx-min-height: -fx-pref-height;" +
			    "-fx-max-height: -fx-pref-height;" + 
			    "-fx-background-radius: 35;" +
			    "-fx-border-color: FFFFFF;" + 
			    "-fx-border-width: 2;" + 
			    "-fx-border-radius: 38;");
	
		
		JFXButton video = new JFXButton();
		video.setText("V");
		video.setStyle("-fx-background-color: #D6D8D9;" +
				"-jfx-button-type: RAISED;" +
			    "-fx-background-radius: 35px;" +
			    "-fx-pref-height: 35px;" +
			    "-fx-pref-width: 35px;" +
			    "-fx-min-width: -fx-pref-width;" +
			    "-fx-max-width: -fx-pref-width;" +
			    "-fx-min-height: -fx-pref-height;" +
			    "-fx-max-height: -fx-pref-height;" + 
			    "-fx-background-radius: 35;" +
			    "-fx-border-color: FFFFFF;" + 
			    "-fx-border-width: 2;" + 
			    "-fx-border-radius: 38;");
		
		
		list = new JFXNodesList();
		list.addAnimatedNode(head);
		list.addAnimatedNode(music);
		list.addAnimatedNode(video);
		
		AnchorPane.setBottomAnchor(list, 10.0);
		AnchorPane.setRightAnchor(list, 110.0);
		list.setRotate(90.0);
		
		sendGround.getChildren().add(list);
	}
	
	static private void toSameRect(Rectangle a, Rectangle model) {
		a.setWidth(model.getWidth());
		a.setHeight(model.getHeight());
		a.setFill(model.getFill());
		a.setLayoutX(model.getLayoutX());
		a.setLayoutY(model.getLayoutY());
		a.setTranslateX(model.getTranslateX());
		a.setTranslateY(model.getTranslateY());
	}
	
	
	static private void toSameCir(Circle a, Circle model) {
		a.setRadius(model.getRadius());
		a.setFill(model.getFill());
		a.setLayoutX(model.getLayoutX());
		a.setLayoutY(model.getLayoutY());
		a.setTranslateX(model.getTranslateX());
		a.setTranslateY(model.getTranslateY());
	}
	
	
	private static int getLineNum(String str) {
		int num = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == 10) {
				num ++;
			}
		}
		
		return num + 1;
	}
	
	
	private static int getLineLength(String str) {
		int num = getLineNum(str), pos = 0, tmp = 0, count = 0;
		int line[] = new int[num];
		if (str.length() == 0) return 0;
		if (str.indexOf('\n') == -1) return str.length();
		while ((pos = str.indexOf('\n', pos + 1)) != -1) {
			line[count++] = str.substring(tmp, pos).length();
			tmp = pos;
		}
		line[count] = str.substring(tmp, str.length()).length();
		Arrays.sort(line);
		return line[num - 1];
	}
	
	
	private static int getMessageNum(List<Integer> list) {
		int n = 0;
		Iterator<Integer> e = list.iterator();
		while (e.hasNext()) {
			int tmp = e.next();
			n += tmp * 34;
			n += 3;
		}
		return n;
	}

	
	public void dragMe() {
		double num = backGround.getWidth();
		myChatBoard.setPrefWidth(2 * num - 480);
	}
	
	public void updateMessage(String name, String message) {
		showYourMessage(name, message);
	}
	
	// Event Listener on Button[#sendButton].onAction
	@FXML
	public void showMyMessage(ActionEvent event) {
		// TODO Autogenerated
		Text myNameClone = new Text();
		myNameClone.setFont(myName.getFont());
		myNameClone.setFill(myName.getFill());
		myNameClone.setText(myName.getText());
		active = true;
		inputText = sendText.getText();
		
		Text myTextClone = new Text();
		myTextClone.setFont(sendText.getFont());
		myTextClone.setFill(Color.WHITE);
		
		Circle a = new Circle();
		toSameCir(a, myBubbleShape1);
		
		Circle b = new Circle();
		toSameCir(b, myBubbleShape2);
		
		Rectangle c = new Rectangle();
		toSameRect(c, myBubbleShape3);
		
		Rectangle d = new Rectangle();
		toSameRect(d, myBubbleShape4);
		
		int lineNum = getLineNum(sendText.getText());
		int lineLength = getLineLength(sendText.getText());
		double tmp = c.getWidth();
		Group e = null;
		
		
		if (lineLength <= 6) {
			c.setWidth(lineLength * 4 + 6);
		
			a.setTranslateX((tmp - c.getWidth()) / 2);
			b.setTranslateX(- (tmp - c.getWidth()) / 2);
			c.setTranslateX((tmp - c.getWidth()) / 2);
			d.setTranslateX(- (tmp - c.getWidth()) / 2);
			
			e = new Group(a, b, c, d);
			e.setScaleY(lineNum * 0.75);
			e.setTranslateX(107 - lineLength * 2);

			myTextClone.setWrappingWidth(tmp);
			myTextClone.setText(sendText.getText());
			myTextClone.setTextAlignment(TextAlignment.RIGHT);
		}
		else if (lineLength <= 9) {
			c.setWidth(lineLength * 5 + 6);
		
			a.setTranslateX((tmp - c.getWidth()) / 2);
			b.setTranslateX(- (tmp - c.getWidth()) / 2);
			c.setTranslateX((tmp - c.getWidth()) / 2);
			d.setTranslateX(- (tmp - c.getWidth()) / 2);
			
			e = new Group(a, b, c, d);
			e.setScaleY(lineNum * 0.75);
			e.setTranslateX(106 - lineLength * 2.5);
			
			myTextClone.setWrappingWidth(tmp);
			myTextClone.setText(sendText.getText());
			myTextClone.setTextAlignment(TextAlignment.RIGHT);
			myTextClone.setTranslateX(2 + 0.25 * lineLength);
		}
		else if (lineLength <= 17) {
			c.setWidth(lineLength * 5.5 + 6);
		
			a.setTranslateX((tmp - c.getWidth()) / 2);
			b.setTranslateX(- (tmp - c.getWidth()) / 2);
			c.setTranslateX((tmp - c.getWidth()) / 2);
			d.setTranslateX(- (tmp - c.getWidth()) / 2);
			
			e = new Group(a, b, c, d);
			e.setScaleY(lineNum * 0.75);
			e.setTranslateX(100 - lineLength * 2.25);
			
			myTextClone.setWrappingWidth(tmp);
			myTextClone.setText(sendText.getText());
			myTextClone.setTextAlignment(TextAlignment.RIGHT);
			myTextClone.setTranslateX(1 + 0.25 * lineLength);
		}
		else if (lineLength <= 28) {
			c.setWidth(lineLength * 6.5 + 3);
		
			a.setTranslateX((tmp - c.getWidth()) / 2);
			b.setTranslateX(- (tmp - c.getWidth()) / 2);
			c.setTranslateX((tmp - c.getWidth()) / 2);
			d.setTranslateX(- (tmp - c.getWidth()) / 2);
			
			e = new Group(a, b, c, d);
			e.setScaleY(lineNum * 0.75);
			e.setTranslateX(107 - lineLength * 3.25);
			
			myTextClone.setWrappingWidth(tmp);
			myTextClone.setText(sendText.getText());
			myTextClone.setTextAlignment(TextAlignment.RIGHT);
			myTextClone.setTranslateX(-2 + 0.25 * lineLength);
		}
		else if (lineLength <= 33) {
			c.setWidth(lineLength * 7 - 14);
		
			a.setTranslateX((tmp - c.getWidth()) / 2);
			b.setTranslateX(- (tmp - c.getWidth()) / 2);
			c.setTranslateX((tmp - c.getWidth()) / 2);
			d.setTranslateX(- (tmp - c.getWidth()) / 2);
			
			e = new Group(a, b, c, d);
			e.setScaleY(lineNum * 0.75);
			e.setTranslateX(115 - lineLength * 3.5);
			
			myTextClone.setWrappingWidth(tmp);
			myTextClone.setText(sendText.getText());
			myTextClone.setTextAlignment(TextAlignment.RIGHT);
			myTextClone.setTranslateX(-6 + 0.25 * lineLength);
		}
		
		myTextClone.setLayoutY(-15.0);
		
		myNameClone.setTranslateX(myNameClone.getLayoutX() + 160.0);
		myNameClone.setTranslateY( - 7.5 * lineNum - 5);
		
		pane = new StackPane(e, myNameClone, myTextClone);
		AnchorPane p = new AnchorPane(pane);
		AnchorPane.setRightAnchor(p, 14.0);
		
		

		Rectangle buffer = new Rectangle(0, 10);
		
		if (mePostLast) {
			double n1 = Math.min(lastLineNum, lineNum);
			double n2 = Math.max(lastLineNum, lineNum);
			buffer.setHeight(6 * n2 + 1 * n1);
		}
		else {
			buffer.setHeight(getMessageNum(yourNum));
		}
		
		myChatBoard.getChildren().add(buffer);
		myChatBoard.getChildren().add(pane);
	
		sendText.setText("");
		lastLineNum = Math.max(lineNum, 1);
		
		pane.setId("me");

		myNum.add(lineNum);
		yourNum.clear();
		
		mePostLast = true;
		chatGround.applyCss();
		chatGround.layout();
		chatGround.setVvalue(chatGround.getVmax());
		//System.out.println(lineLength + "   " + lineNum);	
	}
	
	public String getMessage() {
		while (!active)
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		active = false;
		return inputText;
	}
	
	
	public void showYourMessage(String name, String message) {
		// TODO Autogenerated
				yourName.setText(name);
				String textFile = new String(message);
				
				Text yourNameClone = new Text();
				yourNameClone.setFont(yourName.getFont());
				yourNameClone.setFill(yourName.getFill());
				yourNameClone.setText(yourName.getText());
				
				Text yourTextClone = new Text();
				yourTextClone.setFont(sendText.getFont());
				yourTextClone.setFill(Color.BLACK);
				
				Circle a = new Circle();
				toSameCir(a, yourBubbleShape1);
				
				Circle b = new Circle();
				toSameCir(b, yourBubbleShape2);
				
				Rectangle c = new Rectangle();
				toSameRect(c, yourBubbleShape3);
				
				Rectangle d = new Rectangle();
				toSameRect(d, yourBubbleShape4);
				
				int lineNum = getLineNum(textFile);
				int lineLength = getLineLength(textFile);
				double tmp = c.getWidth();
				Group e = null;
				
				if (lineLength <= 6) {
					c.setWidth(lineLength * 4 + 6);
				
					a.setTranslateX((tmp - c.getWidth()) / 2);
					b.setTranslateX(- (tmp - c.getWidth()) / 2);
					c.setTranslateX((tmp - c.getWidth()) / 2);
					d.setTranslateX((tmp - c.getWidth()) / 2);
					
					e = new Group(a, b, c, d);
					e.setScaleY(lineNum * 0.75);
					e.setTranslateX(- 107 + lineLength * 2);

					yourTextClone.setWrappingWidth(tmp);
					yourTextClone.setText(textFile);
					yourTextClone.setTextAlignment(TextAlignment.LEFT);
				}
				else if (lineLength <= 9) {
					c.setWidth(lineLength * 5 + 6);
				
					a.setTranslateX((tmp - c.getWidth()) / 2);
					b.setTranslateX(- (tmp - c.getWidth()) / 2);
					c.setTranslateX((tmp - c.getWidth()) / 2);
					d.setTranslateX((tmp - c.getWidth()) / 2);
					
					e = new Group(a, b, c, d);
					e.setScaleY(lineNum * 0.75);
					e.setTranslateX(- 106 + lineLength * 2.5);
					
					yourTextClone.setWrappingWidth(tmp);
					yourTextClone.setText(textFile);
					yourTextClone.setTextAlignment(TextAlignment.LEFT);
					yourTextClone.setTranslateX(2 + 0.25 * lineLength);
				}
				else if (lineLength <= 17) {
					c.setWidth(lineLength * 5.5 + 6);
				
					a.setTranslateX((tmp - c.getWidth()) / 2);
					b.setTranslateX(- (tmp - c.getWidth()) / 2);
					c.setTranslateX((tmp - c.getWidth()) / 2);
					d.setTranslateX((tmp - c.getWidth()) / 2);
					
					e = new Group(a, b, c, d);
					e.setScaleY(lineNum * 0.75);
					e.setTranslateX(- 100 + lineLength * 2.25);
					
					yourTextClone.setWrappingWidth(tmp);
					yourTextClone.setText(textFile);
					yourTextClone.setTextAlignment(TextAlignment.LEFT);
					yourTextClone.setTranslateX(1 + 0.25 * lineLength);
				}
				else if (lineLength <= 28) {
					c.setWidth(lineLength * 6.5 + 3);
				
					a.setTranslateX((tmp - c.getWidth()) / 2);
					b.setTranslateX(- (tmp - c.getWidth()) / 2);
					c.setTranslateX((tmp - c.getWidth()) / 2);
					d.setTranslateX((tmp - c.getWidth()) / 2);
					
					e = new Group(a, b, c, d);
					e.setScaleY(lineNum * 0.75);
					e.setTranslateX(- 107 + lineLength * 3.25);
					
					yourTextClone.setWrappingWidth(tmp);
					yourTextClone.setText(textFile);
					yourTextClone.setTextAlignment(TextAlignment.LEFT);
					yourTextClone.setTranslateX(-2 + 0.25 * lineLength);
				}
				else if (lineLength <= 33) {
					c.setWidth(lineLength * 7 - 14);
				
					a.setTranslateX((tmp - c.getWidth()) / 2);
					b.setTranslateX(- (tmp - c.getWidth()) / 2);
					c.setTranslateX((tmp - c.getWidth()) / 2);
					d.setTranslateX((tmp - c.getWidth()) / 2);
					
					e = new Group(a, b, c, d);
					e.setScaleY(lineNum * 0.75);
					e.setTranslateX(- 115 + lineLength * 3.5);
					
					yourTextClone.setWrappingWidth(tmp);
					yourTextClone.setText(textFile);
					yourTextClone.setTextAlignment(TextAlignment.LEFT);
					yourTextClone.setTranslateX(-6 + 0.25 * lineLength);
				}
				
				yourTextClone.setLayoutY(-15.0);
				
				yourNameClone.setTranslateX(- yourNameClone.getLayoutX() - 160.0);
				yourNameClone.setTranslateY( - 7.5 * lineNum - 5);
				
				e.setTranslateX(e.getTranslateX() - 20);
				yourNameClone.setTranslateX(yourNameClone.getTranslateX() - 22);
				yourTextClone.setTranslateX(yourTextClone.getTranslateX() - 22);
				
				AnchorPane.setLeftAnchor(e, 0.0);
				pane = new StackPane(e, yourNameClone, yourTextClone);
				Rectangle buffer = new Rectangle(0, 10);
				
				if (!mePostLast) {
					double n1 = Math.min(lastLineNum, lineNum);
					double n2 = Math.max(lastLineNum, lineNum);
					buffer.setHeight(6 * n2 + 1 * n1);
				}
				else {
					buffer.setHeight(getMessageNum(myNum));
				
				}
			
				yourChatBoard.getChildren().add(buffer);
				
				yourChatBoard.getChildren().add(pane);
				//AnchorPane.setLeftAnchor(chatBoard, 0.0);
				lastLineNum = Math.max(lineNum, 1);
				
				pane.setId("you");

				mePostLast = false;
				yourNum.add(lineNum);
				myNum.clear();
				
				chatGround.applyCss();
				chatGround.layout();
				chatGround.setVvalue(chatGround.getVmax());
				
	}
}
