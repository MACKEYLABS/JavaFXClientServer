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
	static boolean isPrime(int numba) {
		if (numba <= 1) {
			return false;
		}
		for (int i = 2; i <= numba/2; i++) {
			if ((numba % i) == 0)
				return false;
		}
	return true;
	}
	
	@Override
	public void start(Stage serverStage) {
		TextArea ta = new TextArea();
		Scene scene = new Scene(new ScrollPane(ta), 450, 210);
		serverStage.setTitle("Prime Game Server"); 
		serverStage.setScene(scene); 
		serverStage.show(); 
    
		new Thread( () -> { //Create socket connection and streams on separate thread
			try {
				ServerSocket serverSock = new ServerSocket(8000);
				ta.appendText("The Server started at " + new Date() + '\n');
				Socket socket = serverSock.accept();
				DataInputStream inputClient = new DataInputStream(socket.getInputStream());
				DataOutputStream outputClient = new DataOutputStream(socket.getOutputStream());

				while (true) {
					int num = inputClient.readInt();
					ta.appendText("The number received from Client: " + num + '\n');
					outputClient.writeInt(num);
					if (isPrime(num)) {
						ta.appendText(num + " is a prime number!" + '\n');
					}
					else { 	
						ta.appendText(num + " is not a prime number!" + '\n');    	
					}
				}
        
			}catch(IOException ex) {
				ex.printStackTrace();
			}
		}).start();
	}
}

