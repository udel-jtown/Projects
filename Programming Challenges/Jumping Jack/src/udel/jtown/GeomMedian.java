package udel.jtown;

/**
* Class can calculate the Centroid of Points on a two-dimensional graph.
* Can also be used to calculate the geometric median iteratively. 
* @Author John Townsend
* Written 03/09/2017
* Polaris Alpha Programming Challenge
*/
public class GeomMedian {
	/**
	* Used for iteratively checking for Points that are
	* a 'better' geometric median.
	*/
	private int range = 100;
	/**
	* Since coordinates are all ints, PERCISION is used to make sure
	* we do not check for Points less than 1 distance away from the
	* current best Point.
	*/
	private static final double PERCISION = 1.0;
	private Point[] points;
	private Point bestPoint;
	private double bestDistance;

	public GeomMedian(Point[] points) {
		this.points = points;
	}

	public int getBestDistance() {
		return (int) this.bestDistance;
	}
	/**
	* Calculates the centroid of the two-dimensional graph.
	* Uses traditional formula for calculating centroid.
	* @return a Point representing the centroid of the two-dimensional graph.
	*/
	public Point getCentroid() {
		double x = 0.0;
		double y = 0.0;
		double size = (double) points.length;
		for (Point p : points) {
			x += p.getX();
			y += p.getY();
		}
		x = x / size;
		y = y / size;
		return new Point(x, y);
	}
	/**
	* Calculates the distance from an origin {@link Point} to all 
	* Points representing mob member locations.
	* Please note: Does not use euclidean distances.
	* @param p the origin Point.
	* @return the sum of all distances from the mob members to the given Point.
	*/
	public double getDistance(Point p) {
		double ret = 0.0;
		for (Point p1 : points) {
			double dx = Math.abs(p1.getX() - p.getX());
			double dy = Math.abs(p1.getY() - p.getY());
			// Calculate Distance
			ret += dx + dy;
		}
		return ret;
	}
	/**
	* First assumes the centroid is the geometric median.
	* Iteratively checks points within the {@link range} for potential 
	* better Points. 
	* Better meaning more likely to be the actual geometric median.
	* param centroid the centroid of the points and first potential geometric median.
	* @return the actual (or best found) geometric median. 
	*/
	public Point getMedian(Point centroid) {
		// Start by using centroid as best point
		bestDistance = getDistance(centroid);
		bestPoint = centroid;
		boolean finished = false;
		while (range >= PERCISION) {
			finished = false;
			// Get list of Points using range
			Point[] neighbors = bestPoint.findNeighbors(range);
			for (Point p : neighbors) {
				double currDist = getDistance(p);
				if (currDist < bestDistance) {
					bestDistance = currDist;
					bestPoint = p;
					// If we found a better one, don't decrease range.
					finished = true;
				}
			}
			if (!finished) {
				range = range / 2;
			}
		}
		return bestPoint;
	}
	
}
