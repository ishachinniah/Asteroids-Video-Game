package animation.demo;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import animation.animationDemos.BulletDemo;
import animation.animationObjects.AnimatedBullet;

// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.Test;

class BulletDemoTest {
	

	private BulletDemo demo = new BulletDemo();
	// {current x coord, current y coord, rotateAngle, currentSpeed}
	private double[] directions = {300,300,Math.PI/2,5};;
	
	private AnimatedBullet bullet = new AnimatedBullet(demo, directions);
	
    @BeforeEach   
    void setUp() throws Exception {
    }
 
    @Test   
    void testNextFrame() {

		bullet = new AnimatedBullet(demo, directions);

    	double directionAngle = bullet.getDirectionAngle();
    	double moveAmount = bullet.getMoveAmount();
    	
    	// calculate expected coordinates after one fame
    	double expectedX = 300 + moveAmount*Math.cos(directionAngle);
    	double expectedY = 300 - moveAmount*Math.sin(directionAngle);
    	 
    	// call next frame and check if coordinates match expected.
    	bullet.nextFrame(); 
    	assertEquals(expectedX, bullet.getX());
    	assertEquals(expectedY, bullet.getY());

    	} 
    
    @Test   
    void testEdgeUp() {
		bullet.setX(300);
    	bullet.setY(600);
    	bullet.setDirectionAngle(3*Math.PI/2);

    	// retrieve and save coordinate values, movement angle, and movement speed.
    	bullet.nextFrame(); 
		assertTrue(bullet.isAtEdge());
    }
    
    @Test  
    void testEdgeDown() {
    	// Set bullet to bottom edge of screen
    	bullet.setX(300);
    	bullet.setY(0);
    	bullet.setDirectionAngle(Math.PI/2);
    	 
    	// retrieve and save coordinate values, movement angle, and movement speed.
    	bullet.nextFrame(); 
		assertTrue(bullet.isAtEdge());
    
    }
    
    @Test  
    void testEdgeRight() {
    	// Set bullet to right edge of screen
    	bullet.setX(600);
    	bullet.setY(300);
    	bullet.setDirectionAngle(0);
    	 
    	// retrieve and save coordinate values, movement angle, and movement speed.
    	bullet.nextFrame(); 
		assertTrue(bullet.isAtEdge());
    }
    
    @Test  
    void testEdgeLeft() {
    	// Set bullet to left edge of screen
    	bullet.setX(0);
    	bullet.setY(300);
    	bullet.setDirectionAngle(180);
    	 
		// retrieve and save coordinate values, movement angle, and movement speed.
		bullet.nextFrame(); 
		assertTrue(bullet.isAtEdge());
	}
    
}

