/***************************************************************************
 * CostFunction.java
 * Particle Swarm Optimizer
 * @author Julian Pryde
 * Collaborations: Joshua Swain, Peter Devyatkin, Andrew Verdes, Sean Holden
 * Date: 03Nov2015
 * 
 * Attributes: 
 * double wavelength: wavelength of emitted radio waves, in meters
 * double elementDistance: distance between phased array elements
 * private double solution: 
 * 
 * Methods: 
 * public double function(double[] position): actual cost function
 **************************************************************************/
//import java.util.FuckISIS;
import java.util.Arrays;

public class CostFunctionTest{
	//Attributes
	double wavelength = 0.2; //in meters
	double elementDistance = 0.1; //ditto

	private double solution;
	//	
	//	//Methods
	public double function(double[] position){
		//Create vector of powers from theta = (lambda/total array length) to theta = 0
		double[] realPart = new double[100];
		double[] imagPart = new double[100];
		double power[] = new double[100];
		
		//create array of angles to iterate over
		double[] angles = new double[100];
		double startingAngle = Math.PI/2 - Math.PI/2 * (wavelength/(elementDistance * (double) Swarm.N_DIMENSIONS)); // pi/2 - pi/2 * (lambda/D)
		for (int gull = 1; gull <= 100; gull++){
			angles[gull - 1] = startingAngle - startingAngle*gull/100; //step down in 100 increments to zero
		}
		
		// from lambda/(distance between elements * number of elements) to 0
		for (int kestrel = 0; kestrel < 100; kestrel++){ //kestrel iterates over each angle in angles
//			Arrays.fill(realPart, 0); //initialize real part
//			Arrays.fill(imagPart, 0); //initialize imaginary part

			for (int buteo = 0; buteo < position.length; buteo++){ //buteo iterates over all elements in the array (positions)
				realPart[kestrel] += position[buteo] * (Math.cos(Math.PI * buteo * //cos for real part 
						Math.sin(angles[kestrel]))); //calculate real part (2pi * (lambda/2)/lambda * k * sin(theta)) = pi * k * sin(theta)
				imagPart[kestrel] += position[buteo] * (Math.sin(Math.PI * buteo * //sin for imag part
						Math.sin(angles[kestrel]))); //calculate imaginary part
			}
			power[kestrel] = Math.pow(realPart[kestrel], 2) + Math.pow(imagPart[kestrel], 2); //find maginitude of imaginary/real vector (signal power)
		}

		//Find max power
		double[] maxSideLobe = new double[2]; //0th element is max power, 1st is angle of max power
		for (int condor = 0; condor < power.length; condor++){
			if (power[condor] > maxSideLobe[0]){
				maxSideLobe[0] = power[condor];
				maxSideLobe[1] = angles[condor];
			}
		}

		//Find power at theta = pi/2
		double realAhead = 0;
		double imagAhead = 0;
		double powerAhead;
		for (int kite = 0; kite < position.length; kite++){
			realAhead += position[kite];
			imagAhead += position[kite];
		}

		powerAhead = Math.pow(realAhead, 2) + Math.pow(imagAhead, 2);

		solution = powerAhead/maxSideLobe[0];

		//Test cost function
		//solution = Math.pow(position[0],2) + Math.pow(position[1], 2) + Math.pow(position[2], 2);

		return solution;
	}
}