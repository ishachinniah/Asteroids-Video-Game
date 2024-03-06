package animation.animationObjects;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * This class creates an animated asteroid object to move around
 * in an animation frame.
 *
 */
public class AnimatedAsteroid implements AnimatedObject {

    // The starting coordinates of the asteroid
    private double x;
    private double y;
  
    // The number of pixels to move on  each frame of the animation.
    private double moveAmount;

    // The angle direction for the asteroid to move in 
    private double movementAngle = Math.toRadians(Math.random()*360);

    // The angle by which to rotate the upright asteroid.
    private double rotateAngle = Math.toRadians(Math.random()*360);

    // The animation that this object is part of.
    private AbstractAnimation animation;
     
    // The asteroid shape
    private Polygon rock;

    /**
     * Creates the animated asteroid
     * 
     * @param animation the animation this object is part of
     * @param the speed at which the asteroid moves
     */
    public AnimatedAsteroid(AbstractAnimation animation, double speed) {
    	this.animation = animation;
		this.moveAmount = speed;
		
		// Initialise asteroid's properties
		startingPoint();
		asteroidShape();	
    }
    
	/**
	 * Method to randomly generate the asteroid shape from one of two options
	 */
    private void asteroidShape() {
    	double option = Math.random();
    	
    	if (option < 0.5) {
    		rock = new Polygon();
    		rock.addPoint(0, 20);
    		rock.addPoint(10, 15);
    		rock.addPoint(20, 20);
    		rock.addPoint(30, 15);
    		rock.addPoint(20, 5);
    		rock.addPoint(30, -5);
    		rock.addPoint(20, -20);
    		rock.addPoint(0, -15);
    		rock.addPoint(-10, -20);
    		rock.addPoint(-30, -10);
    		rock.addPoint(-10, 0);
    		rock.addPoint(-20, 10);
    	}
    	else {
    		rock = new Polygon();
    		rock.addPoint(0, 10);
    		rock.addPoint(10, 20);
    		rock.addPoint(20, 15);
    		rock.addPoint(30, 10);
    		rock.addPoint(30, -10);
    		rock.addPoint(23, -20);
    		rock.addPoint(0, -20);
    		rock.addPoint(-10, -10);
    		rock.addPoint(-20, -16);
    		rock.addPoint(-30, -10);
    		rock.addPoint(-30, 10);
    		rock.addPoint(-10, 20);
    	}
    }
    
    /**
     * Sets the starting points of the asteroid at the edge
     * the animation frame.
     */
    private void startingPoint() {    	
    	// Randomly generate and select a point that is at  
    	// one of the 4 edges of the screen. 
    	int option = ((int) (Math.random()*4)) + 1;
    	int num = (int) (Math.random() * 600);
    	
    	if (option == 1) {
    		x = 0;
    		y = num;
    	}
    	else if (option == 2) {
    		x = 600;
    		y = num;
    	} 
    	else if (option == 3) {
    		x = num;
    		y = 0;
    	}
    	else {
    		x = num;
    		y = 600;
    	}
    } 

    /** 
     * Draws the asteroid at its current location.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
        Graphics2D g2 = g;
		AffineTransform trans = new AffineTransform();
		trans.translate(x,y);
		trans.rotate(rotateAngle);
		g2.transform(trans);
		
		g2.setPaint(Color.GRAY);
		g2.draw(rock);

    }

    /** 
     * Moves the asteroid at the set speed and angle. If it 
     * reaches the edge of the frame, it wraps around the 
     * screen.
     */
    public void nextFrame() { 
 
    	// Update the x and y value to move in the current direction
    	x += moveAmount*Math.cos(movementAngle);
		y += -moveAmount*Math.sin(movementAngle);
		
    	// if the asteroid reaches the right or left edge, wrap
        if (x > animation.getWidth()) {
			x = 0;
		}
        else if (x < 0) {
			x = animation.getWidth();
		}
         
        // if the asteroid reaches the top or bottom, wrap
		if(y > animation.getHeight()){
			y = 0;
		} 
		else if (y < 0) {
			y = animation.getHeight();
		}
    }
    
    /**
     * Returns the asteroid that is the graphics shape 
     * @return the asteroid that is the graphics shape being drawn
     */
    public Shape getShape() {
		// AffineTransform captures the movement and rotation of the ship
		// this ensures we get accurate coordinates when calling this method
        AffineTransform at1 = new AffineTransform();
        at1.translate(x, y);
        AffineTransform at = at1;

        return at.createTransformedShape(rock);
    } 


    // For TESTING only 
    public double getX() {
    	return x;
    }
    
    // For TESTING only
    public double getY() {
        return y;
    }
     
    // For TESTING only
    public void setX(double x) {
        this.x = x;
    }
    
    // For TESTING only
    public void setY(double y) {
        this.y = y;
    }
    
    // For TESTING only
    public double getMoveAmount() {
        return moveAmount;
    }
    
    // For TESTING only
    public void setMoveAmount(double amount) {
        moveAmount = amount;
    }
    
    // For TESTING only
    public double getMovementAngle() {
    	return movementAngle;
    }
    
    // For TESTING only
    public void setMovementAngle(double movementAngle) {
    	this.movementAngle = movementAngle;
    }
}

