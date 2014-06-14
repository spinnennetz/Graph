import java.awt.geom.Point2D.Double;
import java.awt.geom.Point2D;

/**
 * A class that creates an graph wich stores coordinates for each node.
 * @author Johanna Tengler
 */
public class EmbeddedGraph extends Graph{
	
	/** Array that stores the coordinates of each graphnode. */
	private Point2D.Double[] coordinatesgraph;
	
	/**
	 * Constructor constructs an graph that stores coordinates too.
	 * @param coordinates array that stores the coordinates.
	 */
	public EmbeddedGraph(Point2D[] coordinates) {
		super(coordinates.length);
		coordinatesgraph = new Point2D.Double[coordinates.length];
		
		for (int i=0; i<coordinates.length; i++) {
			this.coordinatesgraph[i]=(Point2D.Double) coordinates[i];
		}
	}
	
	/**
	 * Gets coordinates at index {@code index}
	 * @param index
	 * @return coordinatesgraph[index] the coordinates at the index
	 */
	public Point2D.Double getCoordinate(int index) throws IllegalArgumentException{
		System.out.println(coordinatesgraph[0]);
		if (index >= coordinatesgraph.length) {
			throw new IllegalArgumentException();
		} else if (index < 0) {
			throw new IllegalArgumentException();
		}
		return coordinatesgraph[index];
	}

	/**
	 * Gets the upper-left corner of an imaginary rectangle around the graph.
	 * @return minCorner the coordinates at the upper-left corner of the rectangle
	 */
	public Point2D.Double getMinCorner() {
		Point2D.Double minCorner = new Point2D.Double( coordinatesgraph[0].x , coordinatesgraph[0].y );
		for (int i=1; i<coordinatesgraph.length; i++) {
			// Checks whether the x-coordinates of each node are smaller than those of the other nodes.
			if (coordinatesgraph[i].x < minCorner.x) {
				minCorner.x= coordinatesgraph[i].x;
			}
			// Checks whether the y-coordinates of each node are smaller than those of the other nodes.
			if (coordinatesgraph[i].y < minCorner.y) {
				minCorner.y= coordinatesgraph[i].y;
			}
		}
		return minCorner;
	}
	
	/**
	 * Gets the lower-right corner of an imaginary rectangle around the graph.
	 * @return maxCorner the coordinates at the lower-right corner of the rectangle
	 */
	public Point2D.Double getMaxCorner() {
		Point2D.Double maxCorner = new Point2D.Double( coordinatesgraph[0].x , coordinatesgraph[0].y );
		for (int i=1; i<coordinatesgraph.length; i++) {
			// Checks whether the x-coordinates of each node are larger than those of the other nodes.
			if ((double)coordinatesgraph[i].x > (double)maxCorner.x) {
				maxCorner.x= coordinatesgraph[i].x;
			}
			// Checks whether the y-coordinates of each node are larger than those of the other nodes.
			if ((double)coordinatesgraph[i].y > (double)maxCorner.y) {
				maxCorner.y= coordinatesgraph[i].y;
			}
		}
		return maxCorner;
	}
	
	/**
	 * Gets width of the imaginary rectangle around the graph.
	 * @return {@code this.getMaxCorner().x - this.getMinCorner().x} margin between the x-component of maxCorner and minCorner
	 */
	public double getWidth() {
		return this.getMaxCorner().x - this.getMinCorner().x;
	}
	
	/**
	 * Gets height of the imaginary rectangle around the graph.
	 * @return {@code this.getMaxCorner().y - this.getMinCorner().y} margin between the y-component of maxCorner and minCorner
	 */
	public double getHeight() {
		return this.getMaxCorner().y - this.getMinCorner().y;
	}
	
}