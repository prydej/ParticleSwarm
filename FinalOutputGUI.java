/****************************************************
 * FinalOutputGUI.java
 * Particle Swarm Optimizer
 * @author: Julian Pryde
 * Collaborations: Peter Devyatkin, Joshua Swain
 * Date 03Dec2015
 * 
 * Attributes:
 * 
 * Methods:
 ****************************************************/
import java.util.Arrays;
import javafx.application.Application;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;

public class FinalOutputGUI extends Application{
	//Attributes
	BorderPane borderPane;
	Pane pane;
	
	Text
	finalPositionLabel, finalPositionText, solutionLabel, solutionText;
	
	Button
	ok;
	
	FinalOutputGUI(double[] finalPosition, double solution){
		//Instantiate Panes
		borderPane = new BorderPane();
		pane = new Pane();
		
		//Instantiate text
		finalPositionLabel = new Text(10, 30, "Average Final Position:");
		finalPositionText = new Text(10, 60, Arrays.toString(finalPosition));
		solutionLabel = new Text(10, 90, "Solution:");
		solutionText = new Text(10, 120, String.valueOf(solution));
		
		//Instantiate OK button
		ok = new Button();
		
		//Add pane to borderPane
		borderPane.setLeft(pane);
		
		//Add text to regular pane
		pane.getChildren().add(finalPositionLabel);
		pane.getChildren().add(finalPositionText);
		pane.getChildren().add(solutionLabel);
		pane.getChildren().add(solutionText);
		
		//Button to borderPane
		borderPane.setBottom(ok);
		BorderPane.setAlignment(ok, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(ok, new Insets(0, 20, 20, 0));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception{
		//Event Handlers
		
		//Set scene and stage
		Scene scene = new Scene(borderPane, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Final Results");
		
		//Display GUI
		primaryStage.show();
	}
	
	
}