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
import javax.swing.*; 
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import java.io.*;

public class Client extends Application{

	// Initialize socket and input streams
    private Socket socket = null;
    private DataOutputStream output = null;
    private DataInputStream input = null;

    // Alert for incorrect PIN
    Alert alert;

    // Create text fields for PIN and account information
    private TextField tfPin = new TextField();
    private TextField tfWithdrawl_Amount = new TextField();
    private TextField tfDeposite_Amount = new TextField();

    // Create checkboxes for account options
    private CheckBox cbCheckings = new CheckBox("Checkings");
    private CheckBox cbSavings = new CheckBox("Savings");
    private CheckBox cbDeposite = new CheckBox("Deposite");
    private CheckBox cbWithdrawl = new CheckBox("Withdrawl");

    // Create Button for submitting information
    private Button Submit = new Button("Submit");
    private Button Checking = new Button("Checking");
    private Button Savings = new Button("Savings");

    // Create scenes
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;
    private Scene scene_Withdrawl;
    private Scene scene_Deposite;
    private Scene goodbye;

    // PIN constant
    private final String PIN = "1234";
	
    //create stage
	private Stage stage;
	
	//savings and checkings fields
	double checking_Acount_Balance, savings_Account_Balance;
	//textArea
	TextArea infoDisplay = new TextArea();
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		stage = primaryStage;
		
		BorderPane pane = new BorderPane();
		HBox hbox = new HBox();
		hbox.getChildren().addAll(new Label("Please enter PIN: "), tfPin, Submit);
		    hbox.setSpacing(10); // Adjust spacing between elements
		    
		    VBox vbox = new VBox();
		    vbox.setPadding(new Insets(20, 0, 0, 0));
		    vbox.getChildren().addAll(hbox, infoDisplay);
		    vbox.setSpacing(10); // Adjust spacing between sections
		    
		    pane.setTop(vbox);
		    pane.setCenter(new ScrollPane(infoDisplay));
		    
		    Scene scene1 = new Scene(pane, 355, 200);
		    stage.setTitle("ATM1_Client");
		    stage.setScene(scene1);
		    stage.show();
	
		
		try {
		      // Create a socket to connect to the server
		      Socket socket = new Socket("localhost", 8000);
		      
		      input = new DataInputStream(socket.getInputStream());

		      // Create an output stream to send data to the server
		      output = new DataOutputStream(socket.getOutputStream());
		      
		      checking_Acount_Balance = input.readDouble();
		      savings_Account_Balance = input.readDouble();
		      
		    }
		    catch (IOException ex) {
		    	infoDisplay.appendText(ex.toString() + '\n');
		    }
		

		Submit.setOnAction(e -> {
			
			if(tfPin.getText().equals(PIN)) {
				createScene2();
			}else {
				alert = new Alert(Alert.AlertType.WARNING);
				alert.setTitle("Incorrect PIN");
				alert.setContentText("Your pin is incorrect!!");
				alert.show();
				
			}
				
				
			});
		
		 }
	
	private void createScene2() {
		 // Create a BorderPane to hold the layout
	   // BorderPane borderPane = new BorderPane();
		
	 // Create a GridPane to hold the bank account info components
	    GridPane paneForBankInfo = new GridPane();
	    
	 // Add the label to the GridPane 
	    paneForBankInfo.add(new Label("Please choose which account"
	    		+ " you would like to use"), 0, 0); 
	 // Add the savings checkbox to the GridPane
	    paneForBankInfo.add(cbSavings, 0, 3); 
	    paneForBankInfo.add(cbCheckings, 0, 2);
	 // Set the alignment of the GridPane to center
	    paneForBankInfo.setAlignment(Pos.CENTER);
	   
	    
	// Create a new Scene with the BorderPane as the root and set its size
	    scene2 = new Scene(paneForBankInfo, 355, 200);
		
   // Set the action for the checkings checkbox
		cbCheckings.setOnAction(e -> {
			checkingOptionsScene3();
		});
// Set the action for the checkings checkbox
		cbSavings.setOnAction(e -> {
			savingsOptionsScene();
		});
		
		stage.setScene(scene2);
		stage.show();
	}
	
	private void checkingOptionsScene3() {
		GridPane paneForBankInfo = new GridPane();
		paneForBankInfo.setAlignment(Pos.CENTER);
	
		Font font = new Font("Arial", 12);
		Label label = new Label("What would you like to do?");

		paneForBankInfo.add(cbWithdrawl, 0, 1);
		paneForBankInfo.add(label, 0, 0);
		paneForBankInfo.add(cbDeposite, 0, 2);
		
		
	    label.setFont(font);
		
		scene3 = new Scene(paneForBankInfo, 355, 200);

	    // Set the action for the withdrawal checkbox
	    cbWithdrawl.setOnAction(e -> {
	        withdrawlChecxkingsScene(checking_Acount_Balance);
	    });

	    // Set the action for the deposit checkbox
	    cbDeposite.setOnAction(e -> {
	        depositeCheckingsScene(checking_Acount_Balance);
	    });

	    // Set the scene for the stage and show it
	    stage.setScene(scene3);
	    stage.show();
	}
	
	private void savingsOptionsScene() {
		// Create a GridPane to hold the bank account info components
		GridPane paneForBankInfo = new GridPane();
		paneForBankInfo.setAlignment(Pos.CENTER);
	
		 // Create a label with larger font size
		Font font = new Font("Arial", 12);
		Label label = new Label("What would you like to do?");
		label.setFont(font);

		// Add the components to the GridPane
		paneForBankInfo.add(cbWithdrawl, 0, 1);
		paneForBankInfo.add(label, 0, 0);
		paneForBankInfo.add(cbDeposite, 0, 2);
		
		scene4 = new Scene(paneForBankInfo, 355, 200);
		
	    // Set the action for the withdrawal checkbox
		cbWithdrawl.setOnAction(e -> {
			withdrawlSavingsingsScene(savings_Account_Balance);
		});
		
		cbDeposite.setOnAction(e -> {
			depositeSavingsScene(savings_Account_Balance);
		});
		stage.setScene(scene4);
		stage.show();
	}
	
	private void withdrawlChecxkingsScene(double account_Balance) {
		BorderPane pane = new BorderPane();
	    TextArea ta = new TextArea();
	    Button exitButton = new Button("Exit");
		HBox hbox = new HBox();
		HBox hbox2 = new HBox();
		hbox.getChildren().addAll(new Label("Please enter the amount that"
				+ "you would like withdrawn "));
		hbox2.getChildren().addAll(tfWithdrawl_Amount, Submit, exitButton);
		
		pane.setTop(hbox);
		pane.setCenter(hbox2);
		pane.setBottom(ta);
		
	    
		scene_Withdrawl = new Scene(pane, 355, 200);
		Submit.setOnAction(e -> {
			checking_Acount_Balance = account_Balance - Double.parseDouble(tfWithdrawl_Amount.getText().trim());
			try {
				output.writeDouble(checking_Acount_Balance);
				output.writeDouble(savings_Account_Balance);
				output.flush();
				ta.appendText("Current account balance:\n" +
				"Checkings: " + checking_Acount_Balance +
				"\n" + "Savings: " + savings_Account_Balance); 
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		exitButton.setOnAction(e -> {
			goodBye();
		});
           
		
		stage.setScene(scene_Withdrawl);
		stage.show();
		
	}
		
		private void depositeCheckingsScene(double account_Balance) {
			BorderPane pane = new BorderPane();
			Button exitButton = new Button("Exit");
		    TextArea ta = new TextArea();
			HBox hbox = new HBox();
			HBox hbox2 = new HBox();
			hbox.getChildren().addAll(new Label("Please enter the amount that"
					+ "you would like deposited "));
			hbox2.getChildren().addAll(tfWithdrawl_Amount, Submit, exitButton);
			
			pane.setTop(hbox);
			pane.setCenter(hbox2);
			pane.setBottom(ta);
			
		    
			scene_Deposite = new Scene(pane, 355, 200);
			Submit.setOnAction(e -> {
				checking_Acount_Balance = account_Balance + Double.parseDouble(tfWithdrawl_Amount.getText().trim());
				try {
					output.writeDouble(checking_Acount_Balance);
					output.writeDouble(savings_Account_Balance);
					output.flush();
					ta.appendText("Current account balance:\n" +
					"Checkings: " + checking_Acount_Balance +
					"\n" + "Savings: " + savings_Account_Balance); 
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			exitButton.setOnAction(e -> {
				goodBye();
			});
		
		stage.setScene(scene_Deposite);
		stage.show();
	}
		
		private void withdrawlSavingsingsScene(double account_Balance) {
			BorderPane pane = new BorderPane();
			Button exitButton = new Button("Exit");;
		    TextArea ta = new TextArea();
			HBox hbox = new HBox();
			HBox hbox2 = new HBox();
			hbox.getChildren().addAll(new Label("Please enter the amount that"
					+ "you would like withdrawn "));
			hbox2.getChildren().addAll(tfWithdrawl_Amount, Submit, exitButton);
			
			pane.setTop(hbox);
			pane.setCenter(hbox2);
			pane.setBottom(ta);
			
		    
			scene_Withdrawl = new Scene(pane, 355, 200);
			Submit.setOnAction(e -> {
				savings_Account_Balance = account_Balance - Double.parseDouble(tfWithdrawl_Amount.getText().trim());
				try {
					output.writeDouble(checking_Acount_Balance);
					output.writeDouble(savings_Account_Balance);
					output.flush();
					ta.appendText("Current account balance:\n" +
					"Checkings: " + checking_Acount_Balance +
					"\n" + "Savings: " + savings_Account_Balance);
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			exitButton.setOnAction(e -> {
				goodBye();
			});
			
			stage.setScene(scene_Withdrawl);
			stage.show();
			
			
		}
		
		private void depositeSavingsScene(double account_Balance) {
			BorderPane pane = new BorderPane();
			Button exitButton = new Button("Exit");
		    TextArea ta = new TextArea();
			HBox hbox = new HBox();
			HBox hbox2 = new HBox();
			hbox.getChildren().addAll(new Label("Please enter the amount that"
					+ "you would like deposited "));
			hbox2.getChildren().addAll(tfWithdrawl_Amount, Submit, exitButton);
			
			pane.setTop(hbox);
			pane.setCenter(hbox2);
			pane.setBottom(ta);
			
		    
			scene_Deposite = new Scene(pane, 355, 200);
			Submit.setOnAction(e -> {
				savings_Account_Balance = account_Balance + Double.parseDouble(tfWithdrawl_Amount.getText().trim());
				try {
					output.writeDouble(checking_Acount_Balance);
					output.writeDouble(savings_Account_Balance);
					output.flush();
					ta.appendText("Current account balance:\n" +
					"Checkings: " + checking_Acount_Balance +
					"\n" + "Savings: " + savings_Account_Balance); 
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
			
			exitButton.setOnAction(e -> {
				goodBye();
			});
		
		stage.setScene(scene_Deposite);
		stage.show();
	}
		
	public void goodBye() {
		Stage exitStage = new Stage();
		BorderPane exitPane = new BorderPane();
	    VBox vbox = new VBox(10);
	    vbox.setAlignment(Pos.CENTER);
	    Label exitLabel = new Label("Are you sure you want to exit?");
	    Button exitButton = new Button("Exit");

	    vbox.getChildren().addAll(exitLabel, exitButton);
	    exitPane.setCenter(vbox);
	   
	    Scene exitScene = new Scene(exitPane, 200, 100);
	    exitStage.setScene(exitScene);
	    exitStage.show();
        exitButton.setOnAction(e -> {
            exitStage.close();
            stage.close();
        });

	}
	/**
	   * The main method is only needed for the IDE with limited
	   * JavaFX support. Not needed for running from the command line.
	   */
	  public static void main(String[] args) {
	    launch(args);
	  }
}
