package animation.animationDemos;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import animation.AbstractAnimation;
import animation.MyGame;
import animation.animationObjects.AnimatedShip;


public class ShipDemo extends AbstractAnimation{
	// The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    
    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;
    
	private AnimatedShip ship;
    
    /**  
     * Constructs an animation and initialises it to be able to accept
     * key input.
     */
    public ShipDemo () {
		setFocusable(true);
		this.ship = new AnimatedShip(this);
		startGame();
    }

    @Override
    /**
     * Updates the animated object for the next frame of the animation
     * and repaints the window.
     */
    public void nextFrame() {
    	ship.nextFrame();
    	repaint();

    }

    /**
     * Paint the animation by painting the objects in the animation.
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
		ship.paint((Graphics2D) g.create());
    }

    /**
     * This is called on the downward action when the user presses a key.
     * It notifies the animated ball about presses of up arrow, right 
     * arrow, left arrow, and the space bar.  All other keys are ignored.
     * @param e information abou the key pressed
     */
    public void keyPressed(int key) {
        switch (key) {
			case KeyEvent.VK_UP:
				ship.setMovement(true);
				break;
			case KeyEvent.VK_DOWN:
				ship.setMovement(false);
				break;
			case KeyEvent.VK_RIGHT:
				ship.rotate(0);
				break;
			case KeyEvent.VK_LEFT:
				ship.rotate(1);
				break;
			case KeyEvent.VK_H:
				ship.hyperspace(WINDOW_WIDTH);
				break;
			default:
				// Ignore all other keys
			}
    }

	/**
	 * Method to initialize the jframe and container components of the game
	 */
	public void startGame(){
	
		JFrame f = new JFrame();
		f.setTitle("Asteroids");
		f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		// This says that when the user closes the window, the
		// entire program should exit.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the animation.
		MyGame newGame = new MyGame();

		// Add the animation to the window

		// Display the window. 
		f.setVisible(true);
		f.add(this);

		// Start the animation
		newGame.start();
	}

	//FOR TESTING ONLY
	public AnimatedShip getShip(){
		return ship;
	}

    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * @param args none
     */
    public static void main(String[] args) {
        ShipDemo demo = new ShipDemo();
        demo.start();
    }

}

