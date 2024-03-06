package animation.animationObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.concurrent.ThreadLocalRandom;

import animation.AbstractAnimation;
import animation.AnimatedObject;

/**
 * Creates a ship object, ready to be drawn by a Game type class
 * the ship rotates with user input (left and right arrows)
 * changes+increases movement speed towards the currectly facing direcion when user
 * presses up arrow, and slows down with the down one. H key "hyperspace", 
 * randomly teleports to x,y coords on the screen, space bar shoots bullets in the
 * currently facing direction. 
 */
public class AnimatedShip implements AnimatedObject{

	//static variables

		//the amount we rotate with each press of the key
		private static final double ROTATE_ANGLE = Math.PI /10;

		// The number of pixels to move on each frame of the animation.
		private static final int SPEED_MAX = 20;

	//rotation tracking variables

		//affine transformation rotate angle-- where the ship is facing
		private double rotateAmount = 0;

		//the current angle at which the ship is moving
		private double movementAngle =0;

	//speed tracking variables

		//booleans to keep track of movement or not
		private Boolean isMoving = false;

		//numbers of pixels traversed per frame
		private double moveAmount = 0;

		//tracks the frames to adjust speed as necessary
		private int frameCount =0;

	//death animation variables

		//tracks if ship is performing death animation
		private Boolean isDeathAnimation = false;

		//tracks number of frames of death animation
		private int deathAnimationFrameCount = 0;

	//basic animation variables

		// The animation that this object is part of.
		private AbstractAnimation animation;

		//the color of the ship outline
		private Color currentColor = Color.WHITE;

		// The current coordinates of the ship
		private double x = 300;
		private double y = 300;
		
		// The ship shape
		private Polygon ship;

		
    /**
     * Creates the animated ship
     * 
     * @param animation the animation this ship is part of
     */
    public AnimatedShip(AbstractAnimation animation) {
        this.animation = animation;
		ship = new Polygon();
		ship.addPoint(-5, 8);
		ship.addPoint(0, -8);
		ship.addPoint(5, 8);
    }

    /**
     * Draws a triangle ship with black fill and white outline
     * 
     * @param g the graphics context to draw on.
     */
    public void paint(Graphics2D g) {
		Graphics2D g2 = g;

		AffineTransform rat = new AffineTransform();
		rat.rotate(rotateAmount,x,y);
		rat.translate(x,y);
		g2.transform(rat);

		g2.setPaint(Color.BLACK);
    	g2.fill(ship);
		g2.setColor(currentColor);
		g2.draw(ship);
	}

	/**
	 * Sets the outline color of the ship
	 * 
	 * @param color
	 */
	public void setColor(Color color){
		currentColor = color;
	}

    /**
     * Moves the ship a small amount. If it reaches the left or right edge, it
     * warps around to the other end of the screen.
	 * 
	 * Slows down by 10% every three frames. calls death animation if necessary
     */
    public void nextFrame() {
		//ony update if moving
		if(isMoving){

			//use trig to move diagonally
			x += moveAmount*Math.cos(movementAngle);
			y -= moveAmount*Math.sin(movementAngle);
       
			//if the ship has reached the right or left edges, warp

			if (x > animation.getWidth()) {
				x = 0;

			}else if (x < 0) {
				x = (animation.getWidth());
			}

			//if the ship has reached the upper or lower edges, warp
			
			if(y > animation.getHeight()){
				y = 0;
			}else if (y < 0) {
				y = animation.getHeight();
			}

			//every three frames reduce speed
			frameCount++;
			if(frameCount==3){
				moveAmount = moveAmount*0.9;
				frameCount = 0;
			}
		}else{
			frameCount = 0;
		}

		//this will update regardless of movement
		if(isDeathAnimation){
			executeDeathAnimation();
		}
    }

	/**
	 * Turns the ship outline red and creates the illusion
	 * of the ship blinking by tunring the outline black 
	 * every three frames
	 * 
	 * ends after 8 rounds
	 */
	private void executeDeathAnimation(){
		if(deathAnimationFrameCount == 24){
			isDeathAnimation = false;
			deathAnimationFrameCount = 0;
			currentColor = Color.WHITE;
		}else{
			if(deathAnimationFrameCount%3 ==0){
				currentColor = Color.BLACK;	
			}else{
				currentColor = Color.RED;	
			}
			deathAnimationFrameCount++;
		}
	}

	/**
	 * Begins the death animation cycle
	 */
	public void setDeathAnimation(){
		isDeathAnimation = true;
	}

	/**
	 * @return boolean regarding death animation status
	 */
	public Boolean isInDeathAnimation(){
		return isDeathAnimation;
	}

	/**
     * Returns the shape
     * @return the ship located as we want it to appear
     */
    public Shape getShape() {
        // AffineTransform captures the movement and rotation of the ship
		//this ensures we get accurate coordinates when calling this method

        AffineTransform at1 = new AffineTransform();
        at1.translate(x, y);
        at1.rotate(0);
        AffineTransform at = at1;
        return at.createTransformedShape(ship);
    }

	/**
	 * Updates the movement angle and speed
	 * @param move
	 */
	public void setMovement(Boolean move){
		if(move){
			isMoving = move;
			if(moveAmount+3> SPEED_MAX){
				moveAmount = SPEED_MAX;
			}else{
				moveAmount+=3;
			}
		}else{
			if(moveAmount-3<0){
				moveAmount = 0;
			}else{
				moveAmount-=3;
			}
		}
		movementAngle = Math.PI/2 - (rotateAmount);
	}
	
	/**
	 * Returns information necessary for game class to create a bullet
	 * @return {current x coord, current y coord, rotateAngle, currentSpeed}
	 */
	public double[] shoot(){
		double[] directions = {x,y,rotateAmount,moveAmount};
		return directions;
	}

    /**
     * updates rotate amount by the rotate angle acordingly
	 * ensures we keep rotateAmount within 0-2Pi
     */
    public void rotate(int dir) {
		if(dir==1){
			rotateAmount -= ROTATE_ANGLE;
		}else{
			rotateAmount += ROTATE_ANGLE;
		}

		if (rotateAmount>2*Math.PI){
			rotateAmount -= 2*Math.PI;
		}else if (rotateAmount<0){
			rotateAmount += 2*Math.PI;
		}
    }

	/**
	 * Teleports the ship to a random x,y coordinate on the scrren
	 * @param max Screen height
	 */
	public void hyperspace(int max){
		x = ThreadLocalRandom.current().nextInt(0, max + 1);
		y = ThreadLocalRandom.current().nextInt(0, max + 1);
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
	public void setX(double newX) {
		x = newX;
	}
	
	// For TESTING only
	public void setY(double newY) {
		y = newY;
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
	public void setMovementAngle(double mA) {
		movementAngle = mA;
	}

	// For TESTING only
	public double getrotateAmount() {
		return rotateAmount;
	}
	
	// For TESTING only
	public void setRotateAmount(double rotateAmount) {
		this.rotateAmount = rotateAmount;
	}

	

}
