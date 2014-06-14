import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

/**
 * Creates a window and adds a menu to load several test graphs.
 * @author Jan-Philipp Kappmeier
 */
public class EmbeddedGraphExample {

	/**
	 * Initializes a window and sets it to be visible.
	 * @param args 
	 */
	public static void main( String... args ) {
		SwingUtilities.invokeLater( new Runnable() {

			public void run() {
				// Generate a new window and center on the screen
				JFrame window = new JFrame( "CoMa-Graph-Embedding" );
				window.setSize( 800, 800 );
				window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
				Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
				window.setLocation(d.width/2 - window.getWidth()/2, d.height/2 - window.getHeight()/2);
				
				// String that stores the strokes.
				String[] strokes = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
				
				// Creates a JButton for activating and deactivating indices.
				final JButton button1 = new JButton ("Index");
				// Creates a JComboBox for selecting line width.
				final JComboBox button2 = new JComboBox(strokes);
				// Creates a JButton for activating and deactivating colors.
				final JButton button3 = new JButton ("Farbe");
				
				JPanel free1 = new JPanel ();
				JPanel free2 = new JPanel ();
				JPanel south = new JPanel ();
			
				// Create an instance of the graph board
				final JGraphBoard board = new JGraphBoard( EmbeddingTestInstances.SIMPLE.getGraph(), true );
				
				// Set the layout managers
				window.setLayout( new BorderLayout() );
				south.setLayout( new FlowLayout(FlowLayout.CENTER) );
				
				// Add the graph board
				window.add( board, BorderLayout.CENTER );
				// Add the buttons
				south.add( button2 );
				south.add( free1 );
				south.add( button3 );
				south.add( free2 );
				south.add( button1 );
				window.add( south, BorderLayout.SOUTH );
				
				// Add the MouseListener
				board.addMouseListener( new MyMouseListener( board ) );
				
				// Add the ActionListener for button1
				button1.addActionListener( new ActionListener() {
					public void actionPerformed( ActionEvent e ) {
						// Check whether the indices are displayed or not
						if (board.getBool()==false) {
							// If the indices are not displayed, set the boolean on true
							board.setBool(true);
							board.repaint();
						} else {
							// If the indices are displayed, set the boolean on false
							board.setBool(false);
							board.repaint();
						}
					}
				});
				
				// Add the ActionListener for button2
				button2.addActionListener( new ActionListener() {
					public void actionPerformed( ActionEvent e ) {
						// Set index on selected index
						int index = button2.getSelectedIndex();
						// Add strokelist in graph board the selected index
						board.addStroke(index);
						board.repaint();
					}
				});
				
				// Add the ActionListener for button3
				button3.addActionListener( new ActionListener() {
					public void actionPerformed( ActionEvent e ) {
						if (board.getColor()==false) {
							board.setColor(true);
							board.repaint();
						} else {
							board.setColor(false);
							board.repaint();
						}
					}
				});
				
				// Add some menus for test instances
				window.setJMenuBar( getMenuBar( board ) );
				
				// show the window
				window.setVisible( true );
			}
			
		} );
	}
	
	/**
	 * <p>Draws the vee of an arc. The vee will be drawn at the end of a line that
	 * is supposed to go from <code>(x_1, y_1)</code> to <code>(x_2, y_2)</code>.
	 * </p>
	 * @param g the graphics context to draw on
	 * @param x1 <code>x</code>-coordinate of the start of the arc
	 * @param y1 <code>y</code>-coordinate of the start of the arc
	 * @param x2 <code>x</code>-coordinate of the end of the arc
	 * @param y2 <code>y</code>-coordinate of the end of the arc
	 * @param size the size of the vee. good value is the node size.
	 */
	public static void drawArrowTip( Graphics2D g, double x1, double y1, double x2, double y2, double size ) {
		// Store the old transformation to restore after drawing
		final AffineTransform oldTransform = g.getTransform();
		
		// Set up an affince transformation such that the arc will be vertically
		// aligned in the transformed space
    final double angle = Math.atan2( y2-y1, x2-x1 );
    g.translate( x2, y2 );
    g.rotate( (angle-Math.PI/2d) );

		// draw the vee to the transformed arc
		g.drawLine( 0, 0, (int)(-size*0.25), (int)(-size*0.5) );
		g.drawLine( 0, 0, (int)(+size*0.25), (int)(-size*0.5) );

		// restore transformation
		g.setTransform( oldTransform );
	}
	
	/**
	 * Generates the menu entries to select test instances. Adds labels with name, student-number, group-number.
	 */
	private static JMenuBar getMenuBar( final JGraphBoard board ) {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu( "Beispiele" );
		JPanel free = new JPanel();
		JLabel name = new JLabel( "   Johanna Tengler  |" );
		JLabel matrikel = new JLabel( "  355249  |" );
		JLabel group = new JLabel( "  co1-104  |" );
		JLabel pa06 = new JLabel( "  Programmieraufgabe 6   " );
		menuBar.add( menu );
		menuBar.add( free );
		menuBar.add( name );
		menuBar.add( matrikel );
		menuBar.add( group );
		menuBar.add( pa06 );
		for( final EmbeddingTestInstances instance : EmbeddingTestInstances.getAllInstances() ) {
			JMenuItem menuItem = new JMenuItem( instance.getName() );
			menu.add( menuItem );

			menuItem.addActionListener( new ActionListener() {

				public void actionPerformed( ActionEvent e ) {
					board.setEmbeddedGraph( instance.getGraph() );
				}
			});
		}
		return menuBar;
	}
	
	private static class MyMouseListener extends MouseAdapter {
		private final JGraphBoard component;

		private MyMouseListener( JGraphBoard component ) {
			this.component = component;
		}

		public void mouseClicked( MouseEvent e ) {
			super.mouseClicked( e );
			if( SwingUtilities.isRightMouseButton( e ) )
				component.setSizer(component.getSizer()+1);
			if( SwingUtilities.isLeftMouseButton( e ) ) {
				if (component.getSizer()>1) {
					component.setSizer(component.getSizer()-1);
				}
			}
			component.repaint();
		}
	}
}