package udel.jtown;

/**
* Used for reading user input of ints in a digit by digit way.
* @Author John Townsend
* Written 03/09/2017
* Polaris Alpha Programming Challenge
*/

import java.io.IOException;

public class ReadInput {
	/**
	* Max amount of {@link Point} allowed to be given as input
	*/
	public static final int MAX_INPUT = 1000;
	/**
	* Reads user input one byte at a time to create an int.
	* Multiplies the current read int by 10 to add a new digit.
	* For example, if it has read a 2 in the previous loop,
	* and i = '1', then ret = 2 * 10 = 20. 
	* Then add i, using i -'0', to create 21.
	* @throws IOException if there is an exception when parsing
	* for user input. 
	* @return the newly parsed int.
	*/
	private static int readInt() throws IOException {
		int ret = 0;
		boolean finished = false;
		for (int i = 0; (i = System.in.read()) != -1;) {
			if (i >= '0' && i <= '9') {
				finished = true;
				ret = ret * 10 + i - '0';
			}
			else if (finished) {
				break; // We have reached the end of the buffer.
			}
		}
		return ret;
	}
	/**
	* First asks the user for input.
	* Uses the first parsed int to determine the number of Points that will be 
	* added to the {@link Point} array that will be returned. 
	* that the user will provide.
	* Every two parsed ints are used to create a {@link Point}.
	* @throws IOException if first parsed int is greater than {@link MAX_INPUT}.
	* @return a {@link Point} array containing the Points created by parsing the user input.
	*/
	public static Point[] getInput() {
		System.out.println("Input: ");
		Point[] points;
		// first int is the size of the array
		try {
			int size = readInt();
			points = new Point[size];
			if (size > MAX_INPUT) {
				System.out.println("Mob size cannot be greater than 1,000.");
				throw new IOException();
			}
			else {
				for (int i = 0; i < size; i++) {
				points[i] = new Point(readInt(), readInt());
				}
				return points;	
			}
		}
		catch(IOException e) {
			System.out.println("ERROR: " + e);
		}
		return null;
	}
	
}
