package animation.animationDemos;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JFrame;

import animation.AbstractAnimation;
import animation.MyGame;
import animation.animationObjects.AnimatedBullet;

import java.awt.Color;

public class BulletDemo extends AbstractAnimation {
   
	// The width of the window, in pixels.
    private static final int WINDOW_WIDTH = 600;
    
    // The height of the window, in pixels.
    private static final int WINDOW_HEIGHT = 600;
    
    // The object that moves during the animation.  You might have
    // many objects! 
    private AnimatedBullet bullet;
    
    /**  
     * Constructs an animation and initialises it to be able to accept
     * key input.
     */
    public BulletDemo () {
		startGame();
		setFocusable(true);
    }

    @Override
    /**
     * Updates the animated object for the next frame of the animation
     * and repaints the window.
     */
    protected void nextFrame() {
    	bullet.nextFrame();
    	repaint();
    }

    /**
     * Paint the animation by painting the objects in the animation.
     * @param g the graphic context to draw on
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        bullet.paint((Graphics2D) g);
    }
    
    public AnimatedBullet getBullet() {
    	return bullet;
    }

	/**
	 * Method to initialize the jframe and container components of the game
	 */
	public void startGame(){
		// JFrame is the class for a window. Create the window,
		// set the window's title and its size.
		JFrame f = new JFrame();
		f.setTitle("Asteroids");
		f.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));

		// This says that when the user closes the window, the
		// entire program should exit.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create the animation.
		MyGame newGame = new MyGame();

		// Add the animation to the window
		Container contentPane = f.getContentPane();
		contentPane.setBackground(Color.black);
		contentPane.add(newGame, BorderLayout.CENTER);

		// Display the window.
		f.setVisible(true);
		f.add(this);

		// Start the animation
		newGame.start();
	}

    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * @param args none
     */
    public static void main(String[] args) {
        BulletDemo demo = new BulletDemo();
        demo.start();
    }
}
