package animation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

import javax.swing.JFrame;

import animation.animationObjects.AnimatedAsteroid;
import animation.animationObjects.AnimatedBullet;
import animation.animationObjects.AnimatedShip;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
 
/**
 * This class is the conductor that initializes {@AnimateShip}, {@AnimatedBullet}, {@AnimateAsteroid}
 * to create and run a game.
 * Listens for user key input to decide ship movement and actions.
 */
public class MyGame extends AbstractAnimation implements KeyListener {

	// The width of the window, in pixels.
	private static final int WINDOW_WIDTH = 600;

	// The height of the window, in pixels.
	private static final int WINDOW_HEIGHT = 600;

	//variables to keep track of game status

		//Boolean to check if game has begun
		private boolean hasStarted = false;

		//Boolean to check if player has lost
		private Boolean playing = false;

	//variables to keep track of player status
		private int score;
		private int level;
		private int lives;
	
	//Structures to hold multiple objects of the game

		//contains all asteroids active in the game
		private ArrayList<AnimatedAsteroid> asteroids;

		//contains all bullets active in the game
		private ArrayList<AnimatedBullet> bullets;

	//tracks asteroid speed, increases by 50% as levels go up
	private double asteroidSpeed = 3;

	//tracks number of asteroids at start of level, increases by 50%
	private int numAsteroids;
	
	//tracks if user is still pressing a key, so the relevant output
	//can still occur wihtout having to constantly press and release
	private Map<Integer, Boolean> keysPressed = new HashMap<>();

	//Player's ship
	private AnimatedShip ship;

    /**
	 * Constructs an animation and initializes it to be able to accept
	 * key input.
	 * Initializes all aspects of the game:  
	 * asteroid and bullet arrays
	 * the asteroids present in the first level 
	 * (the ship is initialized when user presses the S key)
	 * And populates the key behavior tracking hash map
	 */
    public MyGame () {
        // Allow the game to receive key input
        setFocusable(true);
        addKeyListener (this);        
        this.asteroids = new ArrayList<AnimatedAsteroid>();
        this.bullets = new ArrayList<AnimatedBullet>();

        keysPressed.put(KeyEvent.VK_UP, false);
        keysPressed.put(KeyEvent.VK_DOWN, false);
        keysPressed.put(KeyEvent.VK_RIGHT, false);
        keysPressed.put(KeyEvent.VK_LEFT, false);
        keysPressed.put(KeyEvent.VK_SPACE, false);
        keysPressed.put(KeyEvent.VK_H, false);
    }
    
    /**
     * Initializes the game by setting the appropriate stats
     */
    public void initGame() {
        score = 0;
        lives = 3;
        level = 0;
        numAsteroids = 5;
        asteroidSpeed = 2.0;
        initShip();
        levelUp();
        playing = true;
    }
    
	/**
	 * For testing convenience, initializes ship
	 */
    public void initShip() {
        ship = new AnimatedShip(this);
    }

    @Override
    /**
     * Updates the animated objects for the next frame of the animation, checks for collisions
     * and repaints the window.
     */ 
    public void nextFrame() { 
        
        if (ship != null) {
            ship.nextFrame();
        }
        
        //Delete bullets that reach end of screen
        for (int bulletIndex = 0; bulletIndex < bullets.size(); bulletIndex++) {
            if (bullets.get(bulletIndex) != null) {
                if (bullets.get(bulletIndex).isAtEdge()) {
                    bullets.remove(bulletIndex);
                } else {
                    bullets.get(bulletIndex).nextFrame();
                }
            }
        }

        for (int asteroidIndex = 0; asteroidIndex < asteroids.size(); asteroidIndex++) {
            if (asteroids.get(asteroidIndex) != null) {
                asteroids.get(asteroidIndex).nextFrame();
            }
        }
        
        checkInteractions();
        repaint();
    }

    /**
     * Check whether two object collide.
     * This tests bullet/asteroid collision and ship/asteroid collision.
     * If a collision occurs, the bullet/asteroid is removed and score increases
     * or the ship loses a life
     */
    private void checkInteractions() {

        // Check if a bullet and an asteroid collide
        for (int bulletIndex = 0; bulletIndex < bullets.size(); bulletIndex++) {
            if (bullets.get(bulletIndex) != null) {
                for (int asteroidIndex = 0; asteroidIndex < asteroids.size(); asteroidIndex++) {
                    if (asteroids.get(asteroidIndex) != null) {
                        if (checkCollision(bullets.get(bulletIndex), asteroids.get(asteroidIndex))) {
                            asteroids.remove(asteroidIndex);
                            bullets.remove(bulletIndex);
                            scoreIncrease(50);
                            break;
                        }
                    }
                }
                if (asteroids.size() == 0) {
                    levelUp();
                }
            }
        }

        // Checks if ship collides with asteroid
        for (int asteroidIndex = 0; asteroidIndex < asteroids.size(); asteroidIndex++) {
            if (asteroids.get(asteroidIndex) != null) {
                if (checkCollision(ship, asteroids.get(asteroidIndex))) {
                    scoreIncrease(50);
                    // Temporary invincibility for ship (prevents dying immediately upon respawn)
                    if (!ship.isInDeathAnimation()) {
                        loseLife();
						asteroids.remove(asteroidIndex);
						if (asteroids.size() == 0) {
							levelUp();
						}
                    }
                }
            }
        }
    }

    /**
     * Check whether two object collide. This tests whether their shapes intersect.
     * 
     * @param object1 the first shape to test
     * @param object2 the second shape to test
     * @return true if the shapes intersect
     */
    public boolean checkCollision(AnimatedObject object1, AnimatedObject object2) {
        return object2.getShape().intersects(object1.getShape().getBounds2D());
    }
    
    /**
	 * Paint the animation by painting the objects in the animation.
	 * implicitely called by the repaint() method
	 * 
	 * @param g the graphic context to draw on
	 */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.white);
        Font titleFont = new Font("PLAIN", Font.PLAIN, 75);
        Font largeFont = new Font("PLAIN", Font.PLAIN, 25);
        Font smallFont = new Font("PLAIN", Font.PLAIN, 16);
        // Display title, credits, and how to start
        if(!hasStarted) {     
            g.setFont(titleFont);
            g.drawString("ASTEROIDS", 90, 190);
            
            g.setFont(largeFont);
            g.drawString("By: Alycea, Bea, Isha, & Rianna", 115, 235);
            g.drawString("Press S to START!", 180, (WINDOW_HEIGHT / 2) + 50);
        }
		//update user status banners  
        if(playing) {
            g.setFont(smallFont);
            // Display player's current stats
            g.drawString("Score: " + String.valueOf(score), 5, 15);
            g.drawString("Lives: " + String.valueOf(lives), 5, 30);
            g.drawString("Level: " + String.valueOf(level), 5, 45);
       
            // Draw the game objects
            for (AnimatedBullet animatedBullet : bullets) {
                if(animatedBullet != null){
                    animatedBullet.paint((Graphics2D) g);
                }
            }
          
            if(ship!=null){
                ship.paint((Graphics2D) g.create());
            }
            
            for(AnimatedAsteroid animatedAsteroid : asteroids) {
                if(animatedAsteroid!= null) {
                    animatedAsteroid.paint((Graphics2D)g.create());
                }
            }      

        } else if(hasStarted && !playing) {         
            if(lives == 0) {
                g.setFont(titleFont);
                g.drawString("GAME OVER", 80, 280);
                g.setFont(largeFont);
                g.drawString("Final Score: " + String.valueOf(score), 200, 350);
                g.setColor(Color.RED);
                g.drawString("Press R to restart", 200, 390);
            }
        }
    }

    @Override
	/**
	 * This is called on the downward action when the user presses a key.
	 * It notifies the game class about presses of up arrow, right
	 * arrow, left arrow, "R","S","H" keys, and the space bar. 
	 * All other keys are ignored.
	 * 
	 * For the commands that deal with user input, this method updates 
	 * a hash map to unsure the desired output continues until they have
	 * released the key
	 * 
	 * @param e information about the key pressed
	 */
    public void keyPressed(KeyEvent E) {
        int e = E.getKeyCode();
        if(keysPressed.containsKey(e)){
            keysPressed.replace(e, true);
        }
        if(keysPressed.get(KeyEvent.VK_UP)){
            ship.setMovement(true);
        }
        if(keysPressed.get(KeyEvent.VK_DOWN)){
            ship.setMovement(false);
        }
        if(keysPressed.get(KeyEvent.VK_RIGHT)){
            ship.rotate(0);
        }
        if(keysPressed.get(KeyEvent.VK_LEFT)){
            ship.rotate(1);
        }
        if(keysPressed.get(KeyEvent.VK_SPACE)){
            double[] directions = ship.shoot();
            spawnBullet(directions);
        }
        if(keysPressed.get(KeyEvent.VK_H)){
            ship.hyperspace(WINDOW_WIDTH);
        }
		//one-tap keys only
        if(KeyEvent.VK_R ==e ){
            if(!playing){
                startGame();
            }
        }
        else if(KeyEvent.VK_S ==e ){
            if (!hasStarted) {
                initGame();
                hasStarted = true;
            }
        }   
    }

    @Override
    /**
     * This is called when the user releases the key after pressing it.
     * It updates the hash map to indicate the user no longer wishes
     * for that action to be performed.
     * 
     * @param e information about the key released
     */
    public void keyReleased(KeyEvent e) {
        if(keysPressed.containsKey(e.getKeyCode())){
            keysPressed.replace(e.getKeyCode(), false);
        }
    }
    
    @Override
    /**
     * This is called when the user presses and releases a key without
     * moving the mouse in between.  Does nothing.
     * @param e information about the key typed.
     */
    public void keyTyped(KeyEvent e) {
        // Nothing to do
    }
    
    /**
     * Method that gets the ship's current direction and coordinates
     * then generates a bullet with that information
     * @param directions {ship's current x coord, ship's current y coord, rotateAngle, ship's currentSpeed}
     */
    public void spawnBullet(double[] directions) {
        AnimatedBullet bullet = new AnimatedBullet(this, directions);
        bullets.add(bullet);
        }
    
    /**
     * Adds asteroid to asteroids arrayList
     * @param numAsteroids the number of asteroids to be spawned
     * @param asteroidSpeed the speed of each asteroid
     * @return the arrayList of asteroids
     */
    public ArrayList<AnimatedAsteroid> spawnAsteroids(int numAsteroids, double asteroidSpeed) {
        for(int i = 0; i < numAsteroids; i++) {
            AnimatedAsteroid asteroid = new AnimatedAsteroid(this, asteroidSpeed);
            asteroids.add(asteroid);
        }      
        return asteroids;
    }
    
    /** Levels that affect the gameplay. Every time there 
     * are no asteroids on the screen, the level goes up. 
     * Number of asteroids increases by 50%.
     * Asteroid speed increases by 50%.
     */
    private void levelUp() {
        if (asteroids.isEmpty()) {
            level++;
            if(level == 1) {
                spawnAsteroids(5, 2);
                return;
            }       
            double increaseAsteroids = 5;
            double increaseSpeed = 2;
            // Increase by 50% each level
            for(int i = 1; i < level; i++) {
                increaseAsteroids = 1.5*increaseAsteroids;
                numAsteroids = (int)Math.round(increaseAsteroids);
                increaseSpeed = 1.5*increaseSpeed;
                asteroidSpeed = increaseSpeed;
            }
            spawnAsteroids(numAsteroids, asteroidSpeed);
        }
    }
    
    /**
     * Player gains a life every 10000 points
     * @param score - players current score
     */
    private void gainLife(int score) {
        if(score > 0 && score % 10000 == 0) {
            System.out.println("Extra life!");
            lives++;
        }
    }
    
    /**
     * Player loses a life every time they get hit
     * When they run out of lives, the game ends
     */
    private void loseLife() {
        lives--;
        if (lives <= 0) {
            gameOver();
        } else {
            // Respawn ship and initialize 24 frame spawn protection
            ship = new AnimatedShip(this);
            ship.setDeathAnimation();
        }
    }
    
    /**
     * Adds points to players score and checks if score is high enough to gain life
     * 50 points for shooting an asteroid
     * Score rolls over at 99990 points
     * @param points - how many points to add to score
     */
    private void scoreIncrease(int points) {
        score+= points;
        gainLife(score);
        if(score > 99990) {
            score = score%99990;
        } 
    }
   
    /**
     * Ends the game
     */
    private void gameOver() {
        playing = false;
        stop();
    }
    
    /**
     * The startGame method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     */
    private void startGame(){
        // Create the window, and set title and size.
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

        // Start the animation
        newGame.start();
    }
 
    /**
     * The main method creates a window for the animation to run in,
     * initializes the animation and starts it running.
     * 
     * @param args none
     */
    public static void main(String[] args) {
        MyGame newGame = new MyGame();
        newGame.startGame();
    }
}
