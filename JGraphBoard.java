import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JComponent;

/**
 * A component that draws a graph.
 * @author Johanna Tengler
 */
public class JGraphBoard extends JComponent{
	
	/** Graph that will be displayed. */
	private EmbeddedGraph egraph;
	/** Boolean, if the graph will be displayed. */
	private boolean bool;
	/** Boolean, if the nodenumber is displayed. */
	private boolean knotennummer;
	/** Boolean, if the graph is displayed colorful or not. */
	private boolean color;
	/** The size of the displayed nodes. */
	private int size;
	/** List that stores the strokes. */
	private SimpleLinkedList<Integer> strokelist;
	
	/**
	 * Constructor constructs an egraph, sets if the graph will be displayed.
	 * @param graph the graph that will be displayed, boool if the graph will be displayed.
	 */
	public JGraphBoard(EmbeddedGraph graph, boolean boool) {
		super();
		egraph = graph;
		bool = boool;
		color = false;
		knotennummer = false;
		size = 50;
		strokelist = new SimpleLinkedList<Integer>();
		strokelist.add(1);
	}
	
	/**
	 * Sets the graph that will be displayed.
	 * @param graphnew the setted graph.
	 */
	public void setEmbeddedGraph( EmbeddedGraph graphnew) {
		egraph = graphnew;
		System.out.println (egraph);
	}
	
	/**
	 * Draws the component.
	 * @param g the graphics context
	 */
	public void paintComponent (Graphics g) {
		super.paintComponent( g );

		// Cast graphics component to correct type
		Graphics2D g2d = (Graphics2D)g;
		
		// Clear background
		g2d.setColor( Color.white );
		g2d.fillRect( 0, 0, getWidth(), getHeight() );
		
		
		for (int i=0; i < egraph.nodeCount(); i++) {
			// Set color
			Color col[] = {Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.cyan, Color.magenta, Color.pink};
			if (color==true) {
				if (i<8) {
					g2d.setColor( col[i] );
				} else {
					g2d.setColor( col[i%8]);
				}
			} else {
				g2d.setColor (Color.darkGray);
			}
			
			// Set line width
			strokelist.reset();
			g2d.setStroke( new BasicStroke( (int)strokelist.getCurrent() ) );
			
			//transform coordinates of nodes
			Point2D.Double node = transform(egraph.getCoordinate(i));
			//draw nodes
			g2d.drawOval((int)node.x - (int) (size/2), (int)node.y-(int) (size/2), (int) size, (int) size);
			
			SimpleLinkedList<GraphEdge> indicence = egraph.getNode(i).getEdges();
			
			// Draw edges
			for (indicence.reset(); indicence.isValid(); indicence.advance()) {
				int x1= (int) node.x ;
				int y1= (int) node.y;
				Point2D.Double nodeend = transform(egraph.getCoordinate(indicence.getCurrent().getEnd().id()));
				int x2= (int) nodeend.x;
				int y2= (int) nodeend.y;
				g2d.drawLine(x1, y1, x2, y2);
				// Draw arrowtips
				EmbeddedGraphExample.drawArrowTip(g2d, x1, y1, x2, y2, 20);
			}
		}

		for (int i=0; i < egraph.nodeCount(); i++) {
			// Check whether node-numbers are displayed or not
			if (knotennummer==true) {
				// Draw node-numbers
				Point2D.Double node = transform(egraph.getCoordinate(i));
				g2d.setColor( Color.black );
				String string = "" + (egraph.getNode(i).id());
				int xstring= (int) node.x+10;
				int ystring= (int) node.y+5;
				g2d.drawString (string, xstring, ystring);
			} 
		}

	}
	
	/**
	 * Sets radius of the nodes
	 * @param size the radius
	 */
	public void setSizer (int size) {
		this.size = size;
	}
	
	/**
	 * Gets radius of the nodes
	 * @param size
	 */
	public int getSizer () {
		return size;
	}
	
	/**
	 * Sets boolean {@code knotennummer} on false if the the node-numbers are displayed
	 * @param bolean the value of {@code knotennummer}
	 */
	public void setBool (boolean bolean) {
		knotennummer = bolean;
	}

	/**
	 * Gets boolean of {@code knotennummer} 
	 * @param knotennummer 
	 */
	public boolean getBool () {
		return knotennummer;
	}
	
	/**
	 * Sets color for drawing
	 * @param bolean true if the graph should be displayed colorful, false if the graph should be displayed black
	 */
	public void setColor (boolean bolean) {
		color = bolean;
	}
	
	/**
	 * Gets color for drawing
	 * @return color the boolean of color
	 */
	public boolean getColor () {
		return color;
	}
	
	/**
	 * Adds an int to the list of Strokes
	 * @param number a line width
	 */
	public void addStroke (int number) {
		strokelist.addFirst(number);
	}

	/**
	 * Transforms a point. It is supposed that {@code corner} is the upper right
	 * coordinate of a rectangle with a given width and height. The transformation
	 * applicated to the submitted point {@code toTransform} scales and moves
	 * all points in the interior and on the border of the rectangle such that the
	 * rectangle fills a maximal area of the component. The rectangle will be
	 * centered.
	 * @param toTransform the point that is actually transformed. For correct scaling, it should lie inside the rectangle.
	 * @return transformed point
	 */
	public Point2D.Double transform(Point2D.Double toTransform) {
		double availableHeight = getHeight();
		double availableWidth = getWidth();
		double scaleHeight = (availableHeight-size-50)/(egraph.getHeight());
		double scaleWidth = (availableWidth-size-50)/(egraph.getWidth());
		double scale = Math.min(scaleHeight, scaleWidth);
		if (egraph.getHeight()==0) {
			scale = 0;
		}
		double xOffset = -egraph.getMinCorner().x * scale + (availableWidth - egraph.getWidth()*scale)*0.5;
		double yOffset = -egraph.getMinCorner().y * scale + (availableHeight - egraph.getHeight()*scale)*0.5;
		


		
		return new Point2D.Double( toTransform.x * scale + xOffset, toTransform.y * scale + yOffset );
	}
}