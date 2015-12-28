public class InRadius{
	//Attributes
	private double distInDimension;
	private double solution;
	private int PartNumber;
	
	//Methods
	public double getDistInDimension() {
		return distInDimension;
	}
	public int getPartNumber() {
		return PartNumber;
	}
	public double getSolution() {
		return solution;
	}
	public void setPartNumber(int partNumber) {
		PartNumber = partNumber;
	}
	public void setDistInDimension(double distInDimension) {
		this.distInDimension = distInDimension;
	}
	public void setSolution(double solution) {
		this.solution = solution;
	}	
}