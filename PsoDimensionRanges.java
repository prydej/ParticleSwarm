import javafx.application.Application;
//import javafx.collections.ObservableList;
import javafx.geometry.Pos;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
import java.util.regex.*;
//import java.util.motivation (Hooyah!)

public class PsoDimensionRanges extends Application{

	//Declare panes
	private BorderPane dimBorderPane;
	private Pane pane;

	//Declare nodes
	// text propmts
	private Text
	rangeOfDimension, whichDimensions, wordTo;

	// input fields
	private TextField
	rangeLower, rangeUpper, forDimension;

	// done button
	private Button
	doneButton;

	public PsoDimensionRanges() {

		//Instantiate nodes and panes
		dimBorderPane = new BorderPane();
		pane = new Pane();

		// text boxes
		rangeOfDimension = new Text(10, 30, "Dimension Range:");
		whichDimensions = new Text(10, 100, "For Dimension(s):");
		wordTo = new Text(125, 60, "to");

		// text fields
		rangeLower = new TextField();
		rangeUpper = new TextField();
		forDimension = new TextField();

		//Done button
		doneButton = new Button("Done");

		//Set node properties
		// text fields
		rangeLower.setLayoutX(10);
		rangeLower.setLayoutY(40);
		rangeLower.setPrefWidth(100);

		rangeUpper.setLayoutX(160);
		rangeUpper.setLayoutY(40);
		rangeUpper.setPrefWidth(100);

		forDimension.setLayoutX(10);
		forDimension.setLayoutY(110);
		forDimension.setPrefWidth(250);

		//Add to panes
		// pane to BorderPane
		dimBorderPane.setLeft(pane);

		// text boxes to regular pane
		pane.getChildren().add(rangeOfDimension);
		pane.getChildren().add(whichDimensions);
		pane.getChildren().add(wordTo);

		// text fields to regular pane
		pane.getChildren().add(rangeLower);
		pane.getChildren().add(rangeUpper);
		pane.getChildren().add(forDimension);

		// done button to borderPane
		dimBorderPane.setBottom(doneButton);
		BorderPane.setAlignment(doneButton, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(doneButton, new Insets(0, 20, 20, 0));
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		//Event handler
		doneButton.setOnAction(e -> setDimensions(primaryStage));

		//Set scene
		Scene scene = new Scene(dimBorderPane, 270, 200); 
		primaryStage.setScene(scene);
		primaryStage.setTitle("Dimension Ranges");

		//Display GUI
		primaryStage.show();
	}

	public static void main(String[] args){ launch(args); }

	public void setDimensions(Stage primaryStage){

//		//find range of dimensions
//		// find first match
//		Pattern pattern = Pattern.compile("[0-9]*-?[0-9]*");
//		Matcher matcher = pattern.matcher(forDimension.getText());
//		//boolean matches = matcher.find(0);
//
//		// split matching string
//		int startIndex = matcher.start();
//		int endIndex = matcher.end();
//
//		//System.out.println(startIndex + " -> " + (endIndex-1));
//
//		// save matching string
		CharSequence dimensionString = forDimension.getText();
		
		
		//find numbers in array
		Pattern isNumber = Pattern.compile("[0-9]"); // to find if char is digit
		int[] dimension = {0,0}; // digit in integer form
		int placeValue = 1; // to define where digit goes in number
		int dimRangeEnd = 1; // beginning or end of range of dimensions
		for (int emu = dimensionString.length(); emu > 0; emu--){ // for the entire string
			Matcher numberMatcher = isNumber.matcher(Character.toString(dimensionString.charAt(emu-1))); // to check if char is digit
			if (numberMatcher.matches() == true){ // if char is digit
				dimension[dimRangeEnd] = dimension[dimRangeEnd] + 
						placeValue * Character.getNumericValue(
						dimensionString.charAt(emu-1));// multiply constant by 10 to add next digit to next place
			} else {
				placeValue = 1;
				dimRangeEnd = 0;
			}
		}
		
		for (int penguin = dimension[0]-1; penguin < dimension[1]; penguin++){
			Swarm.RANGE_UPPER[penguin] = Double.parseDouble(rangeUpper.getText());
			Swarm.RANGE_LOWER[penguin] = Double.parseDouble(rangeLower.getText());
		}

		primaryStage.close();

	}
}