public class Particle{
	
	public static final int N_DIMENSIONS = 10;
	
	// Declare attributes
	private double[] position;
	private double[] velocity;
	private double[] solution;
	
	public Particle(){
		position = new double[N_DIMENSIONS];
	}
	
	// Methods
	public double[] getPosition() {
		return position;
	}

	public double[] getVelocity() {
		return velocity;
	}

	public double[] getSolution() {
		return solution;
	}
	
	// Set position
	
	// Set velocity

	// Calculate solution at current position 
	public double[] calcSolution() {
		
	// Call CostFunction object
		costFunction.function(position);
		
		return solution;
		
	}
	
	// Calculate solution to particles in radius
	public double[] getSolutionsInRadius(){
		
		
	}
	
	// Set new velocity
		
}