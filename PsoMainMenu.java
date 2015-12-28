import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.application.Platform;
//import javafx.collections.ObservableList;
import javafx.geometry.Pos;
//import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.geometry.Insets;
//import javafx.scene.control.madCunt;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PsoMainMenu extends Application{

	//Main Menu
	//Declare panes
	private BorderPane borderPane;
	private Pane regularPane;

	// Declare nodes
	// text prompts 
	private Text
	numberParticlesPrompt, randomnessPrompt, findMaxPrompt, radiusPrompt, logExpConstPrompt, maxIterPrompt;

	// input fields
	private TextField
	numberParticles, randomness, logExpConst, maxIter;

	// radio button
	private RadioButton findMax, findMin;

	// radio button toggle group
	private ToggleGroup optimizationMode;

	// sliders
	private Slider
	radius;

	// buttons
	private Button
	mainMenuDone;

	//Dimension chooser
	//Declare panes
	private BorderPane dimBorderPane;
	private Pane dimRegularPane;

	// Declare nodes
	// text prompts
	private Text
	rangeOfDimension, whichDimensions, wordTo;

	// input fields
	private TextField
	rangeLower, rangeUpper, forDimension;

	// done button
	private Button
	doneButton;
	
	public PsoMainMenu(){
		//Instantiate PaneS
		borderPane = new BorderPane();
		regularPane = new Pane();

		//Instantiate text prompts
		numberParticlesPrompt = new Text(10, 30, "Number of Particles:");
		randomnessPrompt = new Text(10, 60, "Randomness:");
		findMaxPrompt = new Text(10, 90, "Find a: ");
		logExpConstPrompt = new Text(10, 180, "Logistic Exponent Constant:");
		maxIterPrompt = new Text(10, 210, "Number of Iterations:");
		radiusPrompt = new Text(10, 240, "Search Radius:");

		//Instantiate text fields
		numberParticles = new TextField();
		randomness = new TextField();
		logExpConst = new TextField();
		maxIter = new TextField();

		//Instantiate Radio Buttons
		findMax = new RadioButton("Global Maximum");
		findMin = new RadioButton("Global Minimum");

		//Instantiate Toggle Group
		optimizationMode = new ToggleGroup();

		//Instantiate Slider
		radius = new Slider(0, 1, 0.5);

		//Instantiate Button
		mainMenuDone = new Button("Done");

		//Set regularPane position
		borderPane.setLeft(regularPane);

		//Set TextField positions 
		numberParticles.setLayoutX(200);
		numberParticles.setLayoutY(10);

		randomness.setLayoutX(200);
		randomness.setLayoutY(40);

		logExpConst.setLayoutX(200);
		logExpConst.setLayoutY(160);

		maxIter.setLayoutX(200);
		maxIter.setLayoutY(190);

		//Set RadioButton positions and ToggleGroup
		findMax.setLayoutX(20);
		findMax.setLayoutY(105);
		findMax.setToggleGroup(optimizationMode);
		findMax.setSelected(true);

		findMin.setLayoutX(20);
		findMin.setLayoutY(130);
		findMin.setToggleGroup(optimizationMode);

		//Set slider properties
		radius.setLayoutX(5);
		radius.setLayoutY(250);
		radius.setMajorTickUnit(0.1);
		radius.setShowTickMarks(true);
		radius.setShowTickLabels(true);
		radius.setSnapToTicks(true);
		radius.setMaxWidth(400);
		radius.setPrefWidth(372);

		//Add text to pane
		regularPane.getChildren().add(numberParticlesPrompt);
		regularPane.getChildren().add(randomnessPrompt);
		regularPane.getChildren().add(findMaxPrompt);
		regularPane.getChildren().add(maxIterPrompt);

		//Add TextFields to pane
		regularPane.getChildren().add(numberParticles);
		regularPane.getChildren().add(randomness);
		regularPane.getChildren().add(radiusPrompt);
		regularPane.getChildren().add(logExpConstPrompt);
		regularPane.getChildren().add(logExpConst);
		regularPane.getChildren().add(maxIter);

		//Add RadioButttons to pane
		regularPane.getChildren().add(findMax);
		regularPane.getChildren().add(findMin);

		//Add slider to pane
		regularPane.getChildren().add(radius);

		//Add buttons to pane
		borderPane.setBottom(mainMenuDone);
		BorderPane.setAlignment(mainMenuDone, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(mainMenuDone, new Insets(0, 20, 20, 0));

		//************************************************************
		//DimensionChooser
		//Instantiate nodes and panes
		dimBorderPane = new BorderPane();
		dimRegularPane = new Pane();

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

		//Add pane to BorderPane
		// regularPane to BorderPane
		dimBorderPane.setLeft(dimRegularPane);

		// text boxes to regular pane
		dimRegularPane.getChildren().add(rangeOfDimension);
		dimRegularPane.getChildren().add(whichDimensions);
		dimRegularPane.getChildren().add(wordTo);

		// text fields to regular pane
		dimRegularPane.getChildren().add(rangeLower);
		dimRegularPane.getChildren().add(rangeUpper);
		dimRegularPane.getChildren().add(forDimension);

		// done button to borderPane
		dimBorderPane.setBottom(doneButton);
		BorderPane.setAlignment(doneButton, Pos.BOTTOM_RIGHT);
		BorderPane.setMargin(doneButton, new Insets(0, 20, 20, 0));

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Event handlers
		mainMenuDone.setOnAction(e -> setValues(primaryStage));
		doneButton.setOnAction(e -> setDimensions(primaryStage));

		//Set scene and stage
		Scene mainMenu = new Scene(borderPane, 390, 390);
		primaryStage.setScene(mainMenu);
		primaryStage.setTitle("Particle Swarm Optimizer");
		
		//Display GUI
		primaryStage.show();
	}

	public void setValues(Stage primaryStage){

		//Get text from text fields
		PSOMain.NUM_PARTICLES = Integer.parseInt(numberParticles.getText());
		PSOMain.RANDOMNESS_FACTOR = Double.parseDouble(randomness.getText());
		PSOMain.LOGISTIC_EXPONENT_CONSTANT = Double.parseDouble(logExpConst.getText());
		PSOMain.MAXITERATIONS = Integer.parseInt(maxIter.getText());

		//Get number from slider
		PSOMain.RADIUS = radius.getValue();

		//Get value from radio button
		PSOMain.FINDMAX = findMax.isSelected();

		//advance to dimensionChooser
		Scene dimensionChooser = new Scene(dimBorderPane, 270, 200);
		primaryStage.setScene(dimensionChooser);
	}

	public void setDimensions(Stage primaryStage){

		// save matching string
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
			try {
			Swarm.RANGE_UPPER[penguin] = Double.parseDouble(rangeUpper.getText());
			Swarm.RANGE_LOWER[penguin] = Double.parseDouble(rangeLower.getText());
			} catch ( Exception exceptionObject ) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setHeaderText("Range Error!");
				alert.setTitle("Error");
				alert.setContentText("Enter the number of particles as a positive integer.");
			}
		}

		if (Arrays.asList(Swarm.RANGE_LOWER).contains(null)){
			Scene dimension2Chooser = new Scene(dimBorderPane, 270, 200);
			primaryStage.setScene(dimension2Chooser);
		} else {
			primaryStage.close();
			Platform.exit();
		}
	}

	public static void main(String[] args){ launch(args); }

}