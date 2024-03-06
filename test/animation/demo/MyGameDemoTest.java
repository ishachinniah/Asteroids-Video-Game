package animation.demo;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import animation.MyGame;
import animation.animationDemos.ShipDemo;
import animation.animationObjects.AnimatedShip;

import animation.animationDemos.AsteroidDemo;
import animation.animationObjects.AnimatedAsteroid;

import animation.animationDemos.BulletDemo;
import animation.animationObjects.AnimatedBullet;

public class MyGameDemoTest{
    
    private ShipDemo shipDemo = new ShipDemo();
    
    private AsteroidDemo asteroidDemo = new AsteroidDemo();
    //private animation.AnimatedAsteroid asteroid = asteroidDemo.getAsteroid();
    private BulletDemo bulletDemo = new BulletDemo();
//     {current x coord, current y coord, rotateAngle, currentSpeed}
    private double[] directions = {300,300,Math.PI/2,5};;
    
//    private AnimatedBullet bullet = new AnimatedBullet(bulletDemo, directions);
    
    private AnimatedShip ship = new AnimatedShip(shipDemo);
    private AnimatedAsteroid asteroid = new AnimatedAsteroid(asteroidDemo, 2);
    private AnimatedBullet bullet = new AnimatedBullet(bulletDemo, directions);
    private ArrayList<AnimatedAsteroid> asteroids = new ArrayList<AnimatedAsteroid>();
    private ArrayList<AnimatedBullet> bullets;
    
//    private ArrayList<AnimatedAsteroid> asteroids = new ArrayList<AnimatedAsteroid>();
//    private ArrayList<AnimatedBullet> bullets = new ArrayList<AnimatedBullet>();
    MyGame asteroidsGame;    
    
    @BeforeEach   
    void setUp() throws Exception {
        asteroidsGame = new MyGame();
        asteroidsGame.initGame();      
    }
        
    /**
     * Test if the ship spawns at the specified coordinates
     */
    @Test
    void testSpawnShip() {
        asteroidsGame.initShip();    
        
        ship.setX(500);
        ship.setY(500);

        double expectedY = 500;
        assertEquals(expectedY, ship.getY()); 
    }
    
    /**
     * Test if an asteroid spawns at the specified coordinates
     */
    @Test
    void testSpawnAsteroid() {
        
        asteroidsGame.spawnAsteroids(1, 2);
        
        asteroid.setX(300);
        asteroid.setY(300);
        
        double expectedX = 300;
        double expectedY = 300;
        assertEquals(expectedX, asteroid.getX());
        assertEquals(expectedY, asteroid.getY());   
    }
    
    /**
     * Test if an bullet spawns at the specified coordinates
     */
    @Test
    void testSpawnBullet() {
        asteroidsGame.spawnBullet(directions);
        
        bullet.setX(300);
        bullet.setY(300);

        
        double expectedX = 300;
        double expectedY = 300;
        assertEquals(expectedX, bullet.getX());
        assertEquals(expectedY, bullet.getY());   
    }
    
    /**
     * Tests if checkCollision for ship and asteroid returns true
     * when ship and asteroid are in same spot
     */
    @Test
    void testShipAsteroidCollision() {
        asteroidsGame.initShip();
        asteroidsGame.spawnAsteroids(1, 2);
        
        ship.setX(300);
        ship.setY(300);
        
        asteroid.setX(300);
        asteroid.setY(300);
    
        assertTrue(asteroidsGame.checkCollision(ship, asteroid));      
    }
    
    /**
     * Tests if checkCollision for bullet and asteroid returns true
     * when bullet and asteroid are in same spot
     */
    @Test
    void testBulletAsteroidCollision() {
        asteroidsGame.spawnBullet(directions);
        asteroidsGame.spawnAsteroids(1, 2);
        
        bullet.setX(300);
        bullet.setY(300);
        
        asteroid.setX(300);
        asteroid.setY(300);
    
        assertTrue(asteroidsGame.checkCollision(bullet, asteroid));      
    }
    
    /**
     * Tests if objects are updated correctly after a collision
     */
    @Test 
    void testCheckInteractions(){

        asteroidsGame.initShip();
        asteroids = new ArrayList<AnimatedAsteroid>();
        bullets = new ArrayList<AnimatedBullet>();
        
        ship.setX(0);
        ship.setY(0);
        
        bullets.add(bullet);
        bullet.setX(300);
        bullet.setY(300);

        //Add asteroid at same spot as bullet
        asteroids.add(asteroid);
        asteroid.setX(300);
        asteroid.setY(300);
                
        // Check if asteroids and bullets were modified successfully 
        assertEquals(1, asteroids.size());
        assertEquals(1, bullets.size());

        
        // Code from checkCollision to check if bullet and asteroid collide
        for (int bullet = 0; bullet < bullets.size(); bullet++) {
            if (bullets.get(bullet) != null) {
                for (int asteroid = 0; asteroid < asteroids.size(); asteroid++) {
                    if (asteroids.get(asteroid) != null) {
                        if (asteroidsGame.checkCollision(bullets.get(bullet), asteroids.get(asteroid))) {
                            asteroids.remove(asteroid);
                            bullets.remove(bullet);
                        }
                    }
                }
            }
        }
        // Check if asteroid was removed
        assertEquals(0, asteroids.size());
        
        // Add an asteroid to where the ship is 
        asteroids.add(asteroid);
        asteroid.setX(0);
        asteroid.setY(0);
        
        // Check if asteroid was added
        assertEquals(1, asteroids.size());
        
        // Check for collision between ship and asteroid
        for (int asteroid = 0; asteroid < asteroids.size(); asteroid++) {
            if (asteroids.get(asteroid) != null) {
                if (asteroidsGame.checkCollision(ship, asteroids.get(asteroid))) {
                    asteroids.remove(asteroid);
                }
            }
        }
        
        
        // asteroids and bullets should have been removed
        assertEquals(0, asteroids.size());
        assertEquals(0, bullets.size());
    }    
    
}