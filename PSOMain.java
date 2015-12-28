/***************************************************************************************************************************
 * Main.java
 * Particle Swarm Optimizer
 * @author: Julian Pryde
 * Collaborations: Peter Devyatkin, Josh Swain, Andrew Verdes, Sean Holden
 * Date: 09NOV2015
 * 
 * Variables:
 * public static int NUM_PARTICLES: number of particles in swarm
 * public static double RADIUS: radius that a particle searches for other particles to find a gradient
 * public static double LOGISTIC_EXPONENT_CONSTANT: used to control relationship between gradient and velocity of a particle
 * public static double RANDOMNESS_FACTOR: standard deviation of gaussian distribution which determines velocity
 * 
 * Methods:
 * public static void main(String[] args)
 ***************************************************************************************************************************/

import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.Arrays;
import java.util.Random;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.and.chill;
import java.util.Date;
//import javafx.application.Application;
//import java.util.List;

public class PSOMain{

	// Controllable factors
	public static int NUM_PARTICLES; //Number of particles | range: 0 - infinity
	public static double RADIUS; //For all dimensions. Used on normalized dimensions with bounds 0 to 1. | range: 0 - 1
	public static double LOGISTIC_EXPONENT_CONSTANT; //used when caclulating new velocity from gradient | range: 1/1000 - 100
	public static double RANDOMNESS_FACTOR; //standard deviation of the nextGaussian which determines particle velocity | range: 0 - 1

	public static int MAXITERATIONS; //number of iterations until the program terminates
	public static boolean FINDMAX; //to find either a global maximum or minimum

	// Main method
	public static void main(String [] args) throws Exception{
		//boolean stopCondition;
		//double maxGrouping;
		//double minGradient;

		ArrayList<FileOutput> outputList = new ArrayList<FileOutput>(); //Create FileOutput ArrayList

		//Launch GUI
		PsoMainMenu.main(args);

		// Instantiate particle objects in ArrayList
		Swarm particles = new Swarm(); //Instantiate ArrayList of Particles

		particles.createParticles();//Fill ArrayList with particles (This is important)		

		// Set particle numbers
		for (int l = 0; l < PSOMain.NUM_PARTICLES; l++){
			particles.get(l).setPartNumber(l);
		}

		// Initialize particle positions
		for (Particle particle: particles){ //For each particle
			particle.initialPosition(particles);
			FileOutput.INIT_POSITIONS[particle.getPartNumber()] = particle.getPosition();//update FileOutput
		}

		//Start string of data from run
		String megaString = new String();
		try {
			megaString = FileOutput.constructInitPositions(); //add on initial positions
		} catch (ExceptionInInitializerError e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setHeaderText("Range Error");
			alert.setContentText("Enter the number of particles as a positive integer.");
		}

		// Calculate Solutions
		// Instantiate costFunction object
		CostFunctionTest costFunction = new CostFunctionTest();

		// Iterate
		for (int z = 0; z < MAXITERATIONS; z++){

			//Create FileOutput object
			FileOutput iterationOutput = new FileOutput();
			outputList.add(iterationOutput);

			// find solution for each particle in ArrayList
			for (Particle particle: particles){
				particle.setSolution(particle.calcSolution(costFunction));

				outputList.get(z).setSolution(particle.getSolution(), 
						particle.getPartNumber());//update FileOutput
			}

			// Normalize positions
			for (Particle particle: particles){
				for (int i = 0; i < Swarm.N_DIMENSIONS; i++){
					particle.findNormalPosition(particles, particle, i);
				}
				FileOutput.INIT_N_POSITIONS[particle.getPartNumber()] = particle.getNormalizedPosition();
			}

			// Create array of distances between each particle
			double[][] particleDistances = new double[PSOMain.NUM_PARTICLES][PSOMain.NUM_PARTICLES];
			int k = 0; //k and m describe position in particle Distances. k = horizontal, m = vertical distance
			for (Particle particle1: particles){ //For each particle
				int m = 0;

				for (Particle particle2: particles){ //Search each particle
					if (k>m){ //to not search distance between 2 particles twice
						particleDistances[k][m] = particles.findDistance(particle1, particle2); //Find distance
					}
					m++; //Advance to next element in array
				}
				k++;	
			}

			// Fill in zeros in upper right of particleDistances
			for (int q = 0; q < PSOMain.NUM_PARTICLES; q++){
				int r = 0;
				while (r < q){
					particleDistances[r][q] = particleDistances[q][r];
					r++;
				}
			}

			outputList.get(z).setDistances(particleDistances);//update FileOutput

			// Calculate solutions of near particles
			// Create list of solutions and distance in radius for each particle
			String[] inRadius = new String[PSOMain.NUM_PARTICLES];
			String nearParticlesData = new String();

			String[] gradientData = new String[PSOMain.NUM_PARTICLES];
			for (int j = 0; j < Swarm.N_DIMENSIONS; j++){ //iterate through all dimensions
				ArrayList<InRadius> allParticlesInRadius;

				for (int s = 0; s < PSOMain.NUM_PARTICLES; s++){ //iterate through all particles
					//					System.out.println();
					//					System.out.println("Particle: " + (s + 1));

					//create array of all nearby particles' position in dimension and solution
					allParticlesInRadius = new ArrayList<InRadius>(1);

					InRadius particleInRadius = new InRadius();

					//Add position in dimension and solution of nearby particles
					for (int p = 0; p < PSOMain.NUM_PARTICLES; p++){ //search each other particle
						particleInRadius = particles.get(p).findSolutionDistancesRadius(
								particles, particleDistances, s, j, p); //includes sign (-/+)
						if (particleInRadius.getSolution() != 0 || particleInRadius.getDistInDimension() !=0){
							allParticlesInRadius.add(particleInRadius); //Add to solDistparticlesInRadius
							inRadius[p] = String.format("\n\t\tParticle %d: solution: %f, Distance: %f", 
									particleInRadius.getPartNumber(), particleInRadius.getSolution(),
									particleInRadius.getDistInDimension());
						} else {
							inRadius[p] = "";
						}
					}

					for (int i = 0; i < PSOMain.NUM_PARTICLES; i++){ //compile inRadius into nearParticlesData
						nearParticlesData = nearParticlesData + inRadius[i];
					}

					outputList.get(z).setInRadiusData(nearParticlesData, j, s);// give nearParticlesData to FileOutput

					nearParticlesData = null; //reset nearParticlesData

					//Calculate particle gradient
					double[] x = new double[allParticlesInRadius.size()]; //Distances on x-dimension when calc gradient
					double[] y = new double[allParticlesInRadius.size()]; //Solutions on y-dimension when calc gradient

					for (int q = 0; q < allParticlesInRadius.size(); q++){ //for each particle in radius
						x[q] = allParticlesInRadius.get(q).getDistInDimension(); // create array of all DISTANCES of parts in radius
						y[q] = allParticlesInRadius.get(q).getSolution(); // create array of all SOLUTIONS of parts in radius
					}

					if (allParticlesInRadius.size() > 1){ //If there are particles in the radius
						particles.get(s).linearRegression(x, y, j);

						gradientData[s] = String.format("\nGradient:\n%f\n", particles.get(s).getGradient()[j]);

					} else {
						Random gradGen = new Random(); //If no particles in radius
						particles.get(s).setGradient(gradGen.nextDouble() - 0.5, j);
					}

					outputList.get(z).setGradient(particles.get(s).getGradient()[j], j, s); //update FileOutput
				}
			}

			//Set new velocities
			for (int i = 0; i < Swarm.N_DIMENSIONS; i++){
				for (Particle particle: particles){
					particle.findNewVelocity(particle.getGradient()[i], i);
					outputList.get(z).setUpVelocity(particle.getVelocity()[i], i, particle.getPartNumber()); //update FileOutput
				}
			}

			// calculate new position for each particle
			for (int pie = 0; pie < Swarm.N_DIMENSIONS; pie++){
				for (Particle particle: particles){
					outputList.get(z).setPosition(particle.getPosition(), particle.getPartNumber()); //set current position in FileOutput
					outputList.get(z).setNormalizedPosition(particle.getNormalizedPosition(),
							particle.getPartNumber()); //set current normal position in FileOutput

					particle.findNewPosition(particles, pie); //find new position

					outputList.get(z).setUpPosition(particle.getNormalizedPosition()[pie], 
							pie, particle.getPartNumber());//set updated position in FileOutput
				}
			}

			//save updated solutions to FileOutput
			for (Particle particle: particles){
				outputList.get(z).setUpSolution(particle.calcSolution(costFunction), particle.getPartNumber());
			}

			//find un-normalized positions
			for (Particle particle: particles){
				particle.denormalizePosition(particles);
			}

			//Build string of data from iteration
			outputList.get(z).constructOutput(z);

			System.out.println("Finished iteration: " + z);
		}

		//Find average best value in each dimension
		double[] winners = new double[Swarm.N_DIMENSIONS];

		for (int harrier = 0; harrier < Swarm.N_DIMENSIONS; harrier++){

			double positionSum = 0;
			for (Particle particle: particles){
				positionSum += particle.getPosition()[harrier];
			}
			winners[harrier] = positionSum/PSOMain.NUM_PARTICLES; //update FileOutput
		}

		double winningSolution = costFunction.function(winners); //find winning solution
		//update FileOutput

		//Add iteration data to megaString
		for (FileOutput anIterationOutput: outputList){
			megaString = megaString + anIterationOutput.getBody();
		}

		megaString = "Winning positions: " + Arrays.toString(winners) + "\n" + "Winning Solution: " + winningSolution + "\n" + megaString;

		//Write mega-string to file
		DateFormat formatObj = new SimpleDateFormat("ddMMMYY-HH-mm-ss"); //Format for date for name of file
		Date dateObj = new Date(); //date for filename
		String filename = formatObj.format(dateObj); //set filename

		BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false)); //create writer object
		writer.write(megaString);

		//Close filewriter
		writer.close();
		System.out.println("Ding!");
	}
}


