package animation.animationObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * Creates a ship bullet object, ready to be drawn by a Game type class.
 * Generates a small ellipse that moves in the direction it was shot, at 
 * 5+ the speed of the ship at time of shooting
 */
public class AnimatedBullet implements AnimatedObject{

    // The diameter of the ball, in pixels
    private static final int BALL_SIZE =5;

	//tracks whether bullet is on or off the screen
	private Boolean isAtEdge = false;
 
    // The current coordinates of the bullet
    private double x;
    private double y;


    // The number of pixels to move on each frame of the animation.
    private double moveAmount;

	//the angle of movement, given by ship
	private double directionAngle;

    // The animation that this object is part of.
    private AbstractAnimation animation;
    
    // The ball shape
    private Ellipse2D ball;

    /**
     * Creates the animated bullet
     * 
     * @param animation the animation this object is part of, array containting ship's
	 * information: {ship's current x coord, ship's current y coord, rotateAngle, ship's currentSpeed}
     */
    public AnimatedBullet(AbstractAnimation animation, double[] directions) {
        this.animation = animation;
		this.directionAngle = Math.PI/2 -directions[2];
		this.x = (int) directions[0];
		this.y= (int) directions[1];
		this.moveAmount = directions[3]+5;
        ball = new Ellipse2D.Double(x, y, BALL_SIZE*1/2, BALL_SIZE);
    }

	/**
	 * @return isAtEdge status
	 */
	public Boolean isAtEdge(){
		return isAtEdge;
	}

    /**
     * Draws a black circle with white outlien at its current location.
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
		g.setPaint(Color.BLACK);
        g.fill(ball);
		g.setPaint(Color.WHITE);
		g.draw(ball);
    }

    /**
     * Moves the ball a small amount. If it reaches the left or right edge, it
     * updates isAtEdge and will be deleted by Game class.
     */
    public void nextFrame() {

		//use trig to move diagonally
		x += moveAmount*Math.cos(directionAngle);
		y -= moveAmount*Math.sin(directionAngle);

		//check if the ship has reached the screen's edges
        if (x + BALL_SIZE > animation.getWidth()) {
			isAtEdge = true;

        }else if (x < 0) {
            isAtEdge = true;
        }
		if(y + BALL_SIZE*1/2 > animation.getHeight()){
			isAtEdge = true;
		}else if (y < 0) {
			isAtEdge = true;
        }

		ball.setFrame(x, y, BALL_SIZE, BALL_SIZE);
    }
    
    /**
     * Returns the ball that is the graphics shape 
     * @return the ball that is the graphics shape being drawn
     */
    public Shape getShape() {
        return ball;
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
	public double getDirectionAngle() {
		return directionAngle;
	}
	
	// For TESTING only
	public void setDirectionAngle(double directionAngle) {
		this.directionAngle = directionAngle;
	}
}