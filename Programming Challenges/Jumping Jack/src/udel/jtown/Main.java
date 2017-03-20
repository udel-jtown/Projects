package udel.jtown;

/**
* This class contains only the main method so that it may run the program.
* @Author John Townsend
* Written 03/09/2017
* Polaris Alpha Programming Challenge
*/
public class Main {
	/**
	* Takes command line inputs:
	* <p>
	* args[1] = number of mob members
	* <p>
	* args[2 .. n] = Every two pairs is a Point representing coordinates where
	* mob members are in the town.
	* The runtime for this program is under a second.
	* @param args a String array representing a test case
	*/
	public static void main(String[] args) {
		// Read Input From User
		ReadInput input = new ReadInput();
		Point[] points = input.getInput();
		GeomMedian graph = new GeomMedian(points);
		Point cent = graph.getCentroid();
		Point median = graph.getMedian(cent);
		// Print Answer
		System.out.println(median + " " + graph.getBestDistance());
	}
}
