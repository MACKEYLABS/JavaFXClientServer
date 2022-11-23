package application;

import java.io.*;
import java.net.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class PGClient extends Application {
	  DataOutputStream toServ = null;
	  DataInputStream fromServ = null;

	  @Override 
	  public void start(Stage clientStage) {
	    BorderPane paneForTextField = new BorderPane();
	    paneForTextField.setPadding(new Insets(5, 5, 5, 5)); 
	    paneForTextField.setStyle("-fx-border-color: blue");
	    paneForTextField.setLeft(new Label("Enter a number to see if it is prime: "));	    

	    TextField tf = new TextField();
	    tf.setAlignment(Pos.BOTTOM_RIGHT);
	    paneForTextField.setCenter(tf);	    

	    BorderPane panel = new BorderPane();
	    TextArea ta = new TextArea();
	    panel.setCenter(new ScrollPane(ta));
	    panel.setTop(paneForTextField);
	    
	    Scene scene = new Scene(panel, 450, 210);
	    clientStage.setTitle("Prime Game Client"); 
	    clientStage.setScene(scene); 
	    clientStage.show();

	    tf.setOnAction(e -> {
	      try {
	        int num = Integer.parseInt(tf.getText().trim());
	        toServ.writeInt(num);
	        toServ.flush();
	        ta.appendText("The number you sent is " + num + "\n");
	         ta.appendText("The number received from client: " + num + '\n');
	      
	      }catch (NumberFormatException ex) {
	       
	        Alert alert = new Alert(AlertType.WARNING);
    	    alert.setTitle("Warning Dialog");
    	    alert.setHeaderText("Please do not enter letters");
    	    alert.setContentText("Only use numbers!");
    	    alert.showAndWait();
	      } catch (IOException e1) {
			e1.printStackTrace();
		}
	    });

	    try {
	      Socket socket = new Socket("localhost", 8008);
	      fromServ = new DataInputStream(socket.getInputStream());
	      toServ = new DataOutputStream(socket.getOutputStream());
	    }
	    catch (IOException ex) {
	      ta.appendText(ex.toString() + '\n');
	    }
	  }

	}

