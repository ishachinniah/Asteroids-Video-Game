package animation.demo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import animation.animationDemos.AsteroidDemo;
import animation.animationObjects.AnimatedAsteroid;


class AnimatedAsteroidTest {
	
	// Initialise a demo and asteroid for testing
	private AsteroidDemo demo = new AsteroidDemo();
	private AnimatedAsteroid asteroid = new AnimatedAsteroid(demo, 2);

    @BeforeEach     
    void setUp() throws Exception {
    	// Create an asteroid demo and retrieve its asteroid
    	this.demo.start();
    }  
  
    @Test    
    void testNextFrame() {  

    	// Set asteroid at the centre of screen
    	asteroid.setX(300); 
    	asteroid.setY(300);
    	
    	// retrieve and save movement angle and movement speed.
    	double movementAngle = asteroid.getMovementAngle();
    	double moveAmount = asteroid.getMoveAmount();
    	
    	// calculate expected coordinates after one fame
    	double expectedX = asteroid.getX() + moveAmount*Math.cos(movementAngle);
    	double expectedY = asteroid.getY() - moveAmount*Math.sin(movementAngle);
    	
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame(); 
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
  
    	}  
    
    @Test    
    void testWrapDown() {
    	// Set asteroid to bottom edge of screen and move down
    	asteroid.setX(300);
    	asteroid.setY(600);
    	asteroid.setMovementAngle(Math.toRadians(270));
    	
    	// X should stay the same, Y should wrap to 0
    	double expectedX = asteroid.getX();
    	double expectedY = 0.0;
    	 
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame();
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
    }
     
    @Test  
    void testWrapUp() { 
    	// Set asteroid to top edge of screen and move up
    	asteroid.setX(300);
    	asteroid.setY(0);
    	asteroid.setMovementAngle(Math.toRadians(90));
    	
    	// X should stay the same, Y should wrap to 600
    	double expectedX = asteroid.getX();
    	int expectedY = 600;
    	 
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame(); 
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
    }
    
    @Test  
    void testWrapRight() {
    	// Set asteroid to right edge of screen
    	asteroid.setX(600);
    	asteroid.setY(300);
    	asteroid.setMovementAngle(0); 
    	
    	// Y should stay the same, X should wrap to 0
    	double expectedX = 0.0;
    	double expectedY =  asteroid.getY();
    	 
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame();  
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
    }
    
    @Test  
    void testWrapLeft() {
    	// Set asteroid to left edge of screen
    	asteroid.setX(0);
    	asteroid.setY(300);
    	asteroid.setMovementAngle(Math.toRadians(180));
    	
    	// Y should stay the same, X gets wrap to 600
    	double expectedX = 600;
    	double expectedY = asteroid.getY();
    	 
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame();
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
    }
    
    @Test  
    void testSpeed() {
    	// Set asteroid to centre and speed to 4
    	asteroid.setX(300);
    	asteroid.setY(300);
    	asteroid.setMovementAngle(0);
    	asteroid.setMoveAmount(4);  	 
    	
    	// X should increase by 4 pixels, Y should stay the same
    	double  expectedX = asteroid.getX() + 4;
    	double expectedY = asteroid.getY();
    	 
    	// call next frame and check if coordinates match expected.
    	asteroid.nextFrame();
    	assertEquals(expectedX, asteroid.getX());
    	assertEquals(expectedY, asteroid.getY());
    }
    
}

