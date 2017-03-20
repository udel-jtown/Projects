package udel.jtown;

/**
* Creates points that can be represented on a two-dimensional graph.
* @Author John Townsend
* Written 03/09/2017
* Polaris Alpha Programming Challenge
*/

public class Point {
	private int x;
	private int y;
	/**
	* The change in {@link x} to be used when checking neighbors.
	*<p>
	* Corresponds with {@link deltaY}.
	*/
	private int[] deltaX = {-1, 0, 1, 0};
	/**
	* The change in {@link y} to be used when checking neighbors.
	*<p>
	* Corresponds with {@link deltaX}.
	*/
	private int[] deltaY = {0, 1, 0, -1};

	/**
	* Creates a point with {@link x} and {@link y} coordinates.
	* @param x the x coordinate for a two-dimensional graph.
	* @param y the y coordinate for a two-dimensional graph.
	*/
	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}
	/**
	* Creates a point with {@link x} and {@link y} coordinates.
	*<p>
	* These coordinates, given as doubles, are rounded to their nearest int value.
	* @param x the x coordinate for a two-dimensional graph.
	* @param y the y coordinate for a two-dimensional graph.
	*/
	public Point(double x, double y) {
		this.x = (int) Math.round(x);
		this.y = (int) Math.round(y);
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	@Override
	public String toString() {
		return "(" + getX() + ", " + getY() + ")";
	}
	/**
	* Creates a Point array which contains Points all equidistant from this point.
	* The points in the array are potential points that could provide a geometric median.
	* @param range the distance from this point to all Points in the array.
	* @return a Point array.
	*/
	public Point[] findNeighbors(int range) {
		Point[] neighbors = new Point[4];
		for (int i = 0; i < 4; i++) {
			int newX = getX() + range * deltaX[i];
			int newY = getY() + range * deltaY[i];
			neighbors[i] = new Point(newX, newY);
		}
		return neighbors;
	}	
}
