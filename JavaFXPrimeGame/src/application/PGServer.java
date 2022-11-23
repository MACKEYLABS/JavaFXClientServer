package application;

import java.io.*;
import java.net.*;
import java.util.Date;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class PGServer extends Application {
		@Override
  public void start(Stage serverStage) {
    TextArea ta = new TextArea();
    Scene scene = new Scene(new ScrollPane(ta), 450, 210);
    serverStage.setTitle("Prime Game Server"); 
    serverStage.setScene(scene); 
    serverStage.show(); 
    
    new Thread( () -> { //Create socket connection and streams on separate thread
      try {
    	ServerSocket serverSock = new ServerSocket(8008);
      	ta.appendText("The server started at " + new Date() + '\n');
        Socket socket = serverSock.accept();
        DataInputStream inputClient = new DataInputStream(socket.getInputStream());
        DataOutputStream outputClient = new DataOutputStream(socket.getOutputStream());

        while (true) {
          int num = inputClient.readInt();
         ta.appendText("The number received from client: " + num + '\n');
          
         int i, count = 0;
         for(i=2; i<num; i++) {
            if(num%i == 0) {
               count++;
               break;
            }
         }
         if(count == 0)      	         
       	  ta.appendText(num + " is prime!" + '\n');

       	  else      	         
       	  ta.appendText(num + " is not prime!" + '\n');
         outputClient.writeInt(num);
         outputClient.flush();  
        }
      }catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
  }
}

