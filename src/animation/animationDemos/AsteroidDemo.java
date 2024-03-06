package animation.animationDemos;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import animation.AbstractAnimation;
import animation.animationObjects.AnimatedAsteroid;

/**
 * This class creates an asteroid that moves during animation.
 *
 */
public class AsteroidDemo extends AbstractAnimation {
	// The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    
    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;
    
    // The asteroid moving during the animation.  
    private AnimatedAsteroid asteroid;
    
    /**  
     * Constructs an animation
     */
    public AsteroidDemo () {
        // Create a single asteroid in the frame
    	asteroid = new AnimatedAsteroid(this, 2);
        setFocusable(true);
        startGame();
    }

    @Override
    /**
     * Updates the asteroid for the next frame of the animation
     * and repaints the window.
     */
    protected void nextFrame() {
    	asteroid.nextFrame();
    	repaint();
    }

    /**
     * Paint the animation by painting the objects in the animation.
	 * implicitely called by the repaint() method
	 * 
	 * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {    
        super.paintComponent(g); 
        // paints the animated asteroid
        asteroid.paint((Graphics2D) g);
    }
    
    /**
     * Adds the visual aspects of the game and starts the animation 
     * of the demo
     */
    public void startGame() {
    	JFrame f = new JFrame();
        f.setTitle("Asteroid Demo");
        f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT + 28));
        
        // When the user closes the window, the entire program exits.
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container contentPane = f.getContentPane();
        contentPane.add(this, BorderLayout.CENTER);

        // Display the window.
        f.setVisible(true);
    }
    
    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * 
     * @param args none
     */
    public static void main(String[] args) {
        // Create the animation.
        AsteroidDemo demo = new AsteroidDemo();
        // Start the animation
        demo.start();
    } 

    // for TESTING only
    public AnimatedAsteroid getAsteroid() {
    	return asteroid;
    } 
    
}