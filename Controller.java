package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.LoginView;

public class Controller extends Application {
	 @Override
	    public void start(Stage primaryStage) throws Exception{
	        Parent root2 = FXMLLoader.load(getClass().getResource("src\\views\\Login.fxml"));
	        Scene scene2 = new Scene(root2, 320, 500);
	        primaryStage.setTitle("Student Login");
	        primaryStage.setResizable(false);
	        scene2.getStylesheets().add(Controller.class.getResource("../AdminV.css").toExternalForm());
	        primaryStage.setScene(scene2);
	        primaryStage.show();
	    }//SUPPOSED TO WORK WITH LOGIN VIEW FROM JAVAFX. NOT USING THAT NOW COS CAN'T INTEGRATE PROPERLY
	
	
	private LoginView loginView = null;
	
	public Controller(){
		new LoginView(this);
	}
	
	public static void main(String[] args) {
		new Controller();
		//launch(args); To launch java fx login view

	}
	
	public void attemptLogin(String action, String studID, String password){
		
		models.Student sobj = models.Student.studentLogin(action, studID, password);
		if(sobj != null){
			loginView.closeLoginView();
			views.Dashboard.getStudentData(sobj);
		}else
			loginView.loginFailed();
	}
}
