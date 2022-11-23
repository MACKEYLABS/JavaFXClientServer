package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) { 
		
		PGServer classServer = new PGServer();
		Stage serverStage = new Stage();
		serverStage.initOwner(primaryStage);
		classServer.start(serverStage);
				
		PGClient classClient = new PGClient();
		Stage clientStage = new Stage();
		clientStage.initOwner(primaryStage);
		classClient.start(clientStage);
	}
		
	public static void main(String[] args) {
		launch(args);
	}
}
