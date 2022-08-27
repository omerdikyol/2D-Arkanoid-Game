import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/*
 * Ball.java
 * 
 * To create our ball object and set its actions according to cases. (movement, bounce etc.)
 */

@SuppressWarnings("serial")
public class Ball extends JLabel implements ActionListener{
	
	// Variables
	private int ball_size = 20;
	private int ball_pos_x = Game.WIDTH/2 + 10;
	private int ball_pos_y = Game.HEIGHT/2;
	private static int ball_speed = -3;
	private int ball_dir_x = getBall_speed();
	private int ball_dir_y = getBall_speed();
	
	// Ball Image
	private ImageIcon icon;
	
	// Declaring variables to reach and keep variables from different files
	private Game ourGame;
	private Paddle ourPaddle;
	private Level ourLevel;
	private BrickCreator ourBrickCreator;
	private Scoreboard ourSBoard;
	
	// Timer for Movement
	private Timer timer;
	private int delay = 10;
	
	// Bricks
	private ArrayList<Brick> bricks;
	
	public Ball(Level level, Paddle paddle, Game game){
		setBounds(ball_pos_x, ball_pos_y, ball_size, ball_size);
		icon = new ImageIcon(getClass().getResource("ball.png"));
		setIcon(icon);
		setVisible(true);
		
		// Timer for ball's movement
		timer = new Timer(delay,this);
		getTimer().start();
		
		// Get our game objects we will use
		ourPaddle = paddle;
		ourGame = game;
		ourLevel = level;
		ourBrickCreator = level.getBrickCreator();
		ourSBoard = ourLevel.getSboard();
		
		bricks = level.getBricks();

	}
	
	public void resetBall() { // Returns ball to its initial values
		
		int[] directions = new int[]{-getBall_speed(), getBall_speed()};
		ball_pos_x = Game.WIDTH/2 + 2;
		ball_pos_y = Game.HEIGHT/2;
		
		ball_dir_y = getBall_speed();
		
		// Choosing Ball's horizontal direction randomly every time level restarts
		ball_dir_x = directions[new Random().nextInt(directions.length)];
		
		
	}
	
	public void hitEffect(int collide_place) { // Makes sound when collide
		String effect;
		if(Options.getAudio_enabled() == 1) {
			switch(collide_place) {
			case 0: // Walls
				effect = "hit0.wav";
				break;	
			case 1: // Paddle
				effect = "hit1.wav";
				break;
			case 2: // Bricks
				effect = "hit2.wav";
				break;
			case 3: // Ground
				effect = "hit3.wav";
				break;
			default:
				effect = null;
				break;
			}
			
			try {
				AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource(effect));
				Clip clip = AudioSystem.getClip();
				clip.open(audio);
				clip.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}	

	public void actionPerformed(ActionEvent e) {
		
		if (ourGame.isIn_level()) {	// If player entered to the level
			
			// Setting ball's default movement
			ball_pos_x += ball_dir_x;
			ball_pos_y += ball_dir_y;
			setLocation(ball_pos_x, ball_pos_y);
			
			// Checking if ball hit the walls
			if(ball_pos_x <= 0 || ball_pos_x >= Game.WIDTH - ball_size*3/2) {		// Left and Right sides of window
				hitEffect(0); // Make sound
				ball_dir_x *= -1;
			}
			
			else if(ball_pos_y < 0) {	// Top of window
				hitEffect(0); // Make sound
				ball_dir_y *= -1;
			}
			
			else if(ball_pos_y >= Game.HEIGHT - 100) { // Bottom of window ( Health decreases and ball resets )
				hitEffect(3); // Make sound
				resetBall();
				ourLevel.decreaseHealth();
			}
			
			
			// Checking if ball collides with paddle
			if(ball_pos_y == ourPaddle.getPaddle_pos_y() - ball_size) {
				
				int paddle_size = ourPaddle.getPaddle_size();
				int paddle_x = ourPaddle.getPaddle_pos_x();
				
				// I divided the paddle into 3 separate parts.
				
				
				// If ball hits paddle from the left side. It will return at a slightly different angle
				if(ball_pos_x + ball_size/2 >= paddle_x && ball_pos_x + ball_size/2 < paddle_x + paddle_size/3){
					hitEffect(1); // Make sound
					ball_dir_y *= -1.24;
				}
				
				// If ball hits paddle from the middle side. It will return at the same angle
				else if(ball_pos_x + ball_size/2 >= paddle_x + paddle_size/3 && ball_pos_x + ball_size/2 < paddle_x + paddle_size*2/3) {
					hitEffect(1); // Make sound
					ball_dir_y *= -1;
				}
				
				// If ball hits paddle from the right side. It will return at a slightly different angle
				else if(ball_pos_x + ball_size/2 >= paddle_x + paddle_size*2/3 &&  ball_pos_x + ball_size/2 <= paddle_x + paddle_size) {
					hitEffect(1); // Make sound
					ball_dir_y *= -1.24;
				}
			}
			
			
			// Checking if ball collides with bricks
			
			Rectangle ball = new Rectangle(ball_pos_x, ball_pos_y, ball_size, ball_size);	// Current state of our ball
			
			Iterator<Brick> itr = bricks.iterator();	// Iterator to go between bricks
			while(itr.hasNext()) {
				int brick_pos_x, brick_pos_y;
				Brick brick = itr.next();
				
				if(ball.intersects(brick.getBounds())) {	// If ball touched brick
					
					hitEffect(2); // Make sound
					
					brick_pos_x = brick.getX();
					brick_pos_y = brick.getY();
					
					int middle_of_ball_x = ball_pos_x + ball_size/2;
					int middle_of_ball_y = ball_pos_y + ball_size/2;
					
					// Creating Four Sides of Ball as Lines
					Line2D top = new Line2D.Float(ball_pos_x, ball_pos_y, ball_pos_x + ball_size, ball_pos_y);
					Line2D bottom = new Line2D.Float(ball_pos_x, ball_pos_y + ball_size, ball_pos_x + ball_size, ball_pos_y + ball_size);
					Line2D left = new Line2D.Float(ball_pos_x, ball_pos_y, ball_pos_x, ball_pos_y + ball_size);
					Line2D right = new Line2D.Float(ball_pos_x + ball_size, ball_pos_y, ball_pos_x + ball_size, ball_pos_y + ball_size);
					
					
					// We need to check which side of the ball hits brick
					// Also, we need to check if ball is in the correct position ( if hits from right side of itself, it should be on the left side of brick etc. )
					
					// Hitting from bottom of brick
					if(top.intersects(brick.getBounds()) && middle_of_ball_y > brick_pos_y + brick.getHeight()) {
						ball_dir_y *= -1;
						ourLevel.setOurScore(ourLevel.getOurScore() + 10);
					}
					// Hitting from top of brick
					else if(bottom.intersects(brick.getBounds()) && middle_of_ball_y < brick_pos_y) {
						ball_dir_y *= -1;
						ourLevel.setOurScore(ourLevel.getOurScore() + 10);
					}
					// Hitting from right of brick
					else if(left.intersects(brick.getBounds()) && middle_of_ball_x > brick_pos_x + brick.getWidth())  {
						ball_dir_x *= -1;
						ourLevel.setOurScore(ourLevel.getOurScore() + 10);
					}
					// Hitting from left of brick
					else if(right.intersects(brick.getBounds()) && middle_of_ball_x < brick_pos_x )  {
						ball_dir_x *= -1;
						ourLevel.setOurScore(ourLevel.getOurScore() + 10);
					}
			
					// if brick should break, collide() returns 1; so if it returns 1, we should remove brick from our game.
					if(brick.collide() == 1) {
						ourBrickCreator.setBrickCount(ourBrickCreator.getBrickCount() - 1);
						brick.setVisible(false);
						ourLevel.remove(brick);
						bricks.remove(brick);
						break;
					}

				}
			}
			
			// Update ScoreBoard
			ourSBoard.updateBoard(ourLevel.getOurScore(), ourLevel.getOurHealth());
			if(ourBrickCreator.getBrickCount() == 0) {
				ourLevel.nextLevel();
				ourLevel.setEnabled(false);
			}
			
		}
	}

	public Timer getTimer() {
		return timer;
	}

	public static int getBall_speed() {
		return ball_speed;
	}

	public static void setBall_speed(int ball_speed) {
		Ball.ball_speed = ball_speed;
	}
}
