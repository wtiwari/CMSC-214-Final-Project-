/*
 * Class: CMSC214 
 * Instructor: Prof. Estep
 * Description: I made an ATM symulator 
 * Due: 05/14/2023
 * I pledge that I have completed the programming assignment independently.
   I have not copied the code from a student or any source.
   I have not given my code to any student.
   Print your Name here: Will Tiwari
*/
import java.net.*;
import java.util.Date;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import java.io.*;

import javafx.application.Application;

public class Server extends Application{
	
	final double INTEREST_RATE = 0.39;
	double checking_Acount_Balance = 1000.00;
	double savings_Account_Balance = 5000.00;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		// Text area for displaying contents
	    TextArea ta = new TextArea();

	    // Create a scene and place it in the stage
	    Scene scene = new Scene(new ScrollPane(ta), 450, 200);
	    primaryStage.setTitle("ATM_1Server"); // Set the stage title
	    primaryStage.setScene(scene); // Place the scene in the stage
	    primaryStage.show(); // Display the stage
	    
	    new Thread( () -> {
try {
	        	
	        	// Create a server socket
		          ServerSocket serverSocket = new ServerSocket(8000);
		          Platform.runLater(() ->
		            ta.appendText("Server started at " + new Date() + '\n'));
		    
		          // Listen for a connection request
		          Socket socket = serverSocket.accept();
		    
		          // Create data input and output streams
		          DataInputStream inputFromClient = new DataInputStream(
		            socket.getInputStream());
		          DataOutputStream outputToClient = new DataOutputStream(
		            socket.getOutputStream());
		          
		          while (true) {
						Date dateClientConnected = new Date();
						// send banking information to the client
						outputToClient.writeDouble(checking_Acount_Balance);
						
						outputToClient.writeDouble(savings_Account_Balance);
						
						
						// get updated banking information from the client
						checking_Acount_Balance = inputFromClient.readDouble();
						
						savings_Account_Balance = inputFromClient.readDouble();
						
						
			
						Platform.runLater(() -> {
								ta.appendText("Current Checking Account Balance: " + checking_Acount_Balance + '\n');
								ta.appendText("Current Savings Account Balance: " + savings_Account_Balance + '\n');
						});
							             
						
	        }
		          
		          
	          
	} catch(IOException ex) {
		 ex.printStackTrace();
	}


	    }).start();
	        
	 }
	
	/**
	   * The main method is only needed for the IDE with limited
	   * JavaFX support. Not needed for running from the command line.
	   */
	  public static void main(String[] args) {
	    launch(args);
	  }
}
	

