

package animation.demo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import animation.animationDemos.ShipDemo;
import animation.animationObjects.AnimatedShip;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

public class ShipDemoTest {
	
	private ShipDemo demo = new ShipDemo();
	private AnimatedShip ship = demo.getShip();
	
    @BeforeEach   
    void setUp() throws Exception {
		this.demo.start();
		ship.setMovement(true);
    }
 
    @Test    
    void testNextFrame() {
		ship = new AnimatedShip(demo);
		ship.setMovementAngle(0);
		demo.keyPressed(KeyEvent.VK_UP);
    	// retrieve and save coordinate values, movement angle, and movement speed.
    	double x = ship.getX();
    	double y = ship.getY();

    	double movementAngle = ship.getMovementAngle();
    	double moveAmount = ship.getMoveAmount();
    	
    	// calculate expected coordinates after one fame
    	double expectedX = x + moveAmount*Math.cos(movementAngle);
    	double expectedY = y - moveAmount*Math.sin(movementAngle);
    	 
    	// call next frame and check if coordinates match expected.
    	demo.nextFrame(); 
    	assertEquals(expectedX, ship.getX());
    	assertEquals(expectedY, ship.getY());

    	} 
    
    @Test   
    void testWrapUp() {
		ship = new AnimatedShip(demo);
    	// Set Ship to bottom edge of screen
		// adjust angles to face correct direction
    	ship.setX(300);
    	ship.setY(561);
		ship.setMoveAmount(4);
		ship.setRotateAmount(-Math.PI);
    	ship.setMovementAngle(3*Math.PI/2);
		ship.setMovement(true);
		demo.keyPressed(KeyEvent.VK_UP);
    	 
    	// call next frame and check if coordinates match expected.
    	ship.nextFrame(); 
    	assertEquals(0, ship.getY());
    }
    
    @Test  
    void testWrapDown() {
		
		ship = new AnimatedShip(demo);
		// Set Ship to bottom edge of screen
		// adjust angles to face correct direction
    	ship.setX(300);
    	ship.setY(0);
		ship.setMoveAmount(4);

    	ship.setMovementAngle(Math.PI/2);
		ship.setMovement(true);
		demo.keyPressed(KeyEvent.VK_UP);
		
    	// call next frame and check if coordinates match expected.
    	ship.nextFrame(); 
    	assertEquals(561, ship.getY());
    }
    
    @Test  
    void testWrapRight() {
		ship = new AnimatedShip(demo);
    	// Set Ship to right edge of screen
		// adjust angles to face correct direction
   		ship.setX(561);
    	ship.setY(300);
		ship.setMoveAmount(4);
		ship.setMoveAmount(4);
		ship.setRotateAmount(-Math.PI);
    	ship.setMovementAngle(0);
		ship.setMovement(true);
		demo.keyPressed(KeyEvent.VK_UP);
    	
    	// call next frame and check if coordinates match expected.
    	ship.nextFrame(); 
    	assertEquals(0, ship.getX());
    }
    
    @Test  
    void testWrapLeft() {
		ship = new AnimatedShip(demo);
		// Set Ship to right edge of screen
		// adjust angles to face correct direction
   		ship.setX(561);
    	ship.setY(300);
		ship.setMoveAmount(4);
		ship.setRotateAmount(-Math.PI);
    	ship.setMovementAngle(Math.PI);
		ship.setMovement(true);
		demo.keyPressed(KeyEvent.VK_UP);
    	
    	// call next frame and check if coordinates match expected.
    	ship.nextFrame(); 
    	assertEquals(0, ship.getX());
    }
    
	@Test  
    void testMovementAngleDifferentRotationAngle() {
		ship = new AnimatedShip(demo);
    	// Set Ship to left edge of screen
    	ship.setX(300);
    	ship.setY(300);
		//set ship to move towards negative x
    	ship.setMovementAngle(Math.PI);
		//set ship to face positive x
		ship.setRotateAmount(0);
		demo.keyPressed(KeyEvent.VK_UP);
    	 
    	double movementAngle = ship.getMovementAngle();
    	double moveAmount = ship.getMoveAmount();
    	
    	// calculate expected coordinates after one fame
    	double expectedX = 300 + moveAmount*Math.cos(movementAngle);
    	double expectedY = 300 - moveAmount*Math.sin(movementAngle);
    	 
    	// call next frame and check if coordinates match expected.
    	ship.nextFrame(); 
    	assertEquals(expectedX, ship.getX());
    	assertEquals(expectedY, ship.getY());
    }

	@Test  
    void testUserInputRotationLeft() {
		ship = new AnimatedShip(demo);
		ship.setRotateAmount(Math.PI /10);
		demo.keyPressed(KeyEvent.VK_LEFT);
    	 
    	// call next frame and check if coordinates match expected.
    	demo.nextFrame(); 
		ship.nextFrame(); 
    	assertEquals(Math.PI /10, ship.getrotateAmount());
    }

	@Test  
    void testUserInputRotationRight() {
		ship = new AnimatedShip(demo);
		ship.setRotateAmount(-Math.PI /10);
		demo.keyPressed(KeyEvent.VK_R);
    	 
    	// call next frame and check if coordinates match expected.
    	demo.nextFrame(); 
		ship.nextFrame(); 
    	assertEquals(-Math.PI /10, ship.getrotateAmount());
    }
}