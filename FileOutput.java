/********************************************
 * FileOutput.java
 * Particle Swarm Optimizer
 * @author Julian Pryde
 * 
 *******************************************/

import java.util.ArrayList;
//import java.util.wizardry

public class FileOutput{

	// Attributes
	//Information from run
	private String iterationData; //Contains all data from previous dimensions. Each run of constructOutput adds on to this
	private double[][] distances = new double[PSOMain.NUM_PARTICLES][PSOMain.NUM_PARTICLES];
	private double[][] position = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES]; //1 array element per particle
	private double[][] velocity = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	private double[] solution = new double[PSOMain.NUM_PARTICLES];
	private double[][] gradient = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	private double[][] upVelocity = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	private double[][] upPosition = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	private double[] upSolution = new double[PSOMain.NUM_PARTICLES];
	private double[][] normalizedPosition = new double[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	public static final double[][] INIT_POSITIONS = new double[PSOMain.NUM_PARTICLES][Swarm.N_DIMENSIONS];//notice: particles is higher level.
	public static final double[][] INIT_N_POSITIONS = new double[PSOMain.NUM_PARTICLES][Swarm.N_DIMENSIONS]; //Ditto.
	private double winningSolution;

	//Unformatted Strings
	private String[][] inRadiusData = new String[Swarm.N_DIMENSIONS][PSOMain.NUM_PARTICLES];
	private ArrayList<String[]> partsInRadius = new ArrayList<String[]>();
	private String IterationString[] = new String[PSOMain.MAXITERATIONS];
	private String iterationHeader;
	private String dimensionHeader;
	private String particleHeader;
	private String outputPart;
	private String particleBody;
	private String body;
	private String dimensionBody;

	// Methods
	public double[][] getNormalizedPosition() {
		return normalizedPosition;
	}

	public void setNormalizedPosition(double[] normalizedPosition, int i){
		for (int q = 0; q < Swarm.N_DIMENSIONS; q++){
			this.normalizedPosition[q][i] = normalizedPosition[q];
		}
	}

	public String getIterationData() {
		return iterationData;
	}

	public void setIterationData(String iterationData) {
		this.iterationData = iterationData;
	}

	public double[][] getDistances() {
		return distances;
	}

	public double[][] getPosition() {
		return position;
	}

	public void setPosition(double[] position, int i) {
		for (int u = 0; u < Swarm.N_DIMENSIONS; u++){
			this.position[u][i] = position[u];
		}
	}

	public double[][] getVelocity() {
		return velocity;
	}

	public void setVelocity(double[] velocity, int i) {
		for (int u = 0; u < Swarm.N_DIMENSIONS; u++){
			this.velocity[u][i] = velocity[u];
		}
	}

	public double[] getSolution() {
		return solution;
	}

	public void setSolution(double solution, int i) {
		this.solution[i] = solution;
	}

	public double[][] getGradient() {
		return gradient;
	}

	public void setGradient(double gradient, int i, int j) {
		this.gradient[i][j] = gradient;
	}

	public void setDistances(double[][] distances) {
		this.distances = distances;
	}

	public double[][] getUpVelocity() {
		return upVelocity;
	}

	public void setUpVelocity(double velocity, int i, int t){
		this.upVelocity[i][t] = velocity;
	}

	public double[][] getUpPosition() {
		return upPosition;
	}

	public void setUpPosition(double position, int i, int t){
		this.upPosition[i][t] = position;
	}

	public double[] getUpSolution() {
		return upSolution;
	}

	public void setUpSolution(double solution, int i){
		this.upSolution[i] = solution;
	}


	//for use by constructOutput()
	public String[][] getInRadiusData() {
		return inRadiusData;
	}

	public void setInRadiusData(String inRadiusData, int i, int j) {
		this.inRadiusData[i][j] = inRadiusData;
	}

	public ArrayList<String[]> getPartsInRadius() {
		return partsInRadius;
	}

	public void setPartsInRadius(ArrayList<String[]> partsInRadius) {
		this.partsInRadius = partsInRadius;
	}

	public String[] getIterationString() {
		return IterationString;
	}

	public void setIterationString(String[] iterationString) {
		IterationString = iterationString;
	}

	public String getIterationHeader() {
		return iterationHeader;
	}

	public void setIterationHeader(String iterationHeader) {
		this.iterationHeader = iterationHeader;
	}

	public String getDimensionHeader() {
		return dimensionHeader;
	}

	public void setDimensionHeader(String dimensionHeader) {
		this.dimensionHeader = dimensionHeader;
	}

	public String getParticleHeader() {
		return particleHeader;
	}

	public void setParticleHeader(String particleHeader) {
		this.particleHeader = particleHeader;
	}

	public String getOutputPart() {
		return outputPart;
	}

	public void setOutputPart(String outputPart) {
		this.outputPart = outputPart;
	}

	public String getParticleBody() {
		return particleBody;
	}

	public void setParticleBody(String particleBody) {
		this.particleBody = particleBody;
	}

	public String getBody(){
		return body;
	}
	
//	//Construct string of best average positions
//	public static String constructWinners(finalPositions){
//		String winners = "";
//		
//		for (int owl = 0; owl < Swarm.N_DIMENSIONS; owl++){
//			winners = winners +
//		}
//		
//		return winners;
//	}

	//Construct string of initial positions and normalized positions
	public static String constructInitPositions(){
		String initPositions = "Initial Positions:\n\nParticle:\t";

		for (int osprey = 0; osprey < Swarm.N_DIMENSIONS; osprey++){ //add collumn headers
			initPositions = initPositions + "Dimension " + osprey + ":\t\t\t";
		}

		for (int cats = 0; cats < PSOMain.NUM_PARTICLES; cats++){ //add particle numbers and positions
			initPositions = initPositions + "\n" + cats;
			for (int dogs = 0; dogs < Swarm.N_DIMENSIONS; dogs++){
				initPositions = initPositions + "\t\t" + INIT_POSITIONS[cats][dogs];
			}
		}

		//		String initNormalPositions = "\n\nInitial Normalized Positions:\n\nParticle:\t";
		//
		//		for (int lions = 0; lions < Swarm.N_DIMENSIONS; lions++){ //add collumn headers
		//			initNormalPositions = initNormalPositions + "Dimension " + lions + ":";
		//		}
		//
		//		for (int tigers = 0; tigers < PSOMain.NUM_PARTICLES; tigers++){ //add particle numbers and positions
		//			initNormalPositions = initNormalPositions + "\n" + tigers;
		//			for (int bears = 0; bears < Swarm.N_DIMENSIONS; bears++){
		//				initNormalPositions = initNormalPositions + "\t\t" + INIT_N_POSITIONS[tigers][bears];
		//			}
		//		}
		//		String[] initialData = new String[2];
		//		initialData = initPositions;
		//		initialData = initNormalPositions;
		return initPositions;
	}

	//Construct final output for iteration
	public void constructOutput(int iterNumber){

		//Build distanceStrings
		String[] distanceStrings = new String[PSOMain.NUM_PARTICLES];
		for (int k = 0; k < distances.length; k++){
			for (int i = 0; i < distances.length; i++){
				distanceStrings[k] = distanceStrings[k] + String.format("\n\t\t%f", distances[k][i]);
			}
		}

		//Construct body
		for (int j = 0; j < Swarm.N_DIMENSIONS; j++){
			dimensionHeader = String.format("\nDimension: %d ", j);
			dimensionBody = "";
			for(int k = 0; k < PSOMain.NUM_PARTICLES; k++){
				particleHeader = String.format("\nParticle: %d ", k);
				particleBody = String.format("\n\tSolution: %f\n\tNormalized Position: %f\n\tDistances:%s\n\tinRadius:%s"
						+ "\n\tGradient: %f\n\tUpdated Velocity: %f\n\tUpdated Normalized Position: %f\n\tUpdated Solution: %f",
						solution[k], normalizedPosition[j][k], distanceStrings[k], inRadiusData[j][k], gradient[j][k], 
						upVelocity[j][k], upPosition[j][k], upSolution[k]);
				dimensionBody = dimensionBody + particleHeader + particleBody;
			}
			body = body + dimensionHeader + dimensionBody;
		}

		String allNParticlePositions = "\nParticle\t";
		
		for (int falcon = 0; falcon < Swarm.N_DIMENSIONS; falcon++){
			allNParticlePositions = allNParticlePositions + "Dimension " + falcon + ":\t";
		}

		for (int hawk = 0; hawk < PSOMain.NUM_PARTICLES; hawk++){
			allNParticlePositions = allNParticlePositions + "\n" + hawk;
			for (int eagle = 0; eagle < Swarm.N_DIMENSIONS; eagle++){
				allNParticlePositions = allNParticlePositions + String.format("\t\t%f", normalizedPosition[eagle][hawk]);
			}
		}

		body = String.format("\n\nIteration: %d ", iterNumber) + body + 
				String.format("\n\nFinal iteration positions:\n%s\n", allNParticlePositions);
	}
}
