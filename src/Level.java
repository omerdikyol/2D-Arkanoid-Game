import java.awt.Graphics;
import java.awt.Image;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

/*
 * Level.java
 * 
 * Create our level by calling paddle and ball, and setting variables like health, score etc.
 */

@SuppressWarnings("serial")
public class Level extends JPanel{
	
	// Background
	protected Image backgroundImage;
	private ImageIcon myBackgroundIcon;

	Timer timer;
	
	// Game ( For Checking if our player entered to the any level, so we can start the ball movement )
	private Game ourGame;
	
	// Paddle
	private Paddle myPaddle;
	
	// Ball
	private Ball myBall;

	// Player Health
	private int ourHealth;
	
	// BrickCreator
	private BrickCreator brickCreator;
	private ArrayList<Brick> bricks;
	
	// Score Board
	private Scoreboard sboard;
	private int ourScore = 0;
	private String nameInput;
	
	// File Writer for keeping scores
	private FileWriter fw;
	
	// Level for keeping level
	private int ourLevel;
	
	public Level(Game game, int level, int score, int health){
		
		// Setting our window
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(null);
		setOpaque(false);
		
		// Declaring variables to get them from different files and send them to next.
		ourGame = game;
		ourLevel = level;
		ourScore = score;	// Used it for transfer current score to the next level
		ourHealth = health; // Used it for transfer current health to the next level
		
		// Creating bricks
		brickCreator = new BrickCreator(this,Options.getCurrColumn(),Options.getCurrRow(),level);		// Row and Column are changeable from options page.
		bricks = brickCreator.getBricks();
		

		// Adding score board to the top
		sboard = new Scoreboard(level,0,3);
		sboard.setBounds(0,0,Game.WIDTH-30,30);
		add(sboard);
		
		// Creating paddle and adding keyboard and mouse control
		myPaddle = new Paddle(this);
		add(myPaddle);
		addKeyListener(myPaddle);
		addMouseMotionListener(myPaddle);
		
		// Creating ball
		myBall = new Ball(this, myPaddle, ourGame);
		add(myBall);

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}
	

	public void decreaseHealth() {	// Decrease our health if ball touches bottom.
		setOurHealth(getOurHealth() -1);
		if (getOurHealth() == 0) {
			gameOver();
		}
	}
	

	public void gameOver() {
		myBall.getTimer().stop();	// Stop the ball
		
		// Get name data from user
		nameInput = (String)JOptionPane.showInputDialog(this, "Enter Your Name:", "Game Over", JOptionPane.PLAIN_MESSAGE);
		// Get current date
		Date date = new Date();							
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		try {
			fw = new FileWriter(getClass().getResource("scores.txt").getPath(), true);
			// Append score, name, brick amount and date to the 'scores.txt' file
			fw.write(String.format("%d | %s | Brick per Level: %d | Date: %s \n", ourScore, nameInput, Options.getCurrRow()*Options.getCurrColumn(), formatter.format(date)));
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Go back to the main menu
		ourGame.getCd().show(ourGame.getContentPane(), "MainMenu");
	}
	
	public void hitEffect(int effect_type) { // Makes sound 
		if(Options.getAudio_enabled() == 1) {
			String effect;
			switch(effect_type) {
			case 0: // Next Level
				effect = "next_level.wav";
				break;
			case 1: // End of The Game
				effect = "final.wav";
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
	
	
	public void nextLevel() { // Called after all bricks are broken
		myBall.getTimer().stop();	// Stop the ball
		
		// If we are not at the final level, we should head to the next level.
		if(ourLevel != Game.getMaxLevel()) {
			hitEffect(0); // Make Sound
			
			// Get next Level's name
			String nextLevel = String.format("Level%d", ourLevel+1);
			// Create next level and send current score and health to it
			ourGame.createLevel(new Level(ourGame, ourLevel + 1, ourScore, ourHealth), nextLevel);
			ourGame.setIn_level(true);
			// Head to the next level
			ourGame.getCd().show(ourGame.getContentPane(), nextLevel); 
		}
		
		// If it is final level, we should head to the main menu.
		else {
			try {
				Clip clip = null;
				if(Options.getAudio_enabled()== 1) {
					// Start the victory music!
					AudioInputStream audio = AudioSystem.getAudioInputStream(getClass().getResource("final.wav"));
					clip = AudioSystem.getClip();
					clip.open(audio);
					clip.start();
					clip.loop(Clip.LOOP_CONTINUOUSLY);
				}
				// Tell user that he/she is the pro gamer!
				nameInput = (String)JOptionPane.showInputDialog(this, "Enter Your Name:", "You've finished the game!", JOptionPane.PLAIN_MESSAGE);
				if(Options.getAudio_enabled()== 1) 
					clip.stop();	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				// Get Current date
				Date date = new Date();	
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				fw = new FileWriter(getClass().getResource("scores.txt").getPath(), true);
				
				// Append score, name, brick amount and date to the 'scores.txt' file
				fw.write(String.format("%d | %s | Brick per Level: %d | Date: %s \n", ourScore, nameInput, Options.getCurrRow()*Options.getCurrColumn(), formatter.format(date)));
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			// Thank you.
			String info = "That was the last Level. Heading to the Main Menu!";
			JOptionPane.showMessageDialog(null, info, "Thank you for playing", JOptionPane.INFORMATION_MESSAGE);		
			// Go back to Main Menu
			ourGame.getCd().show(ourGame.getContentPane(), "MainMenu");
		}
	}

	// Getters and Setters
	
	public Paddle getMyPaddle() {
		return myPaddle;
	}

	public BrickCreator getBrickCreator() {
		return brickCreator;
	}

	public int getOurScore() {
		return ourScore;
	}

	public void setOurScore(int ourScore) {
		this.ourScore = ourScore;
	}

	public int getOurHealth() {
		return ourHealth;
	}
	
	public void setOurHealth(int ourHealth) {
		this.ourHealth = ourHealth;
	}

	public ArrayList<Brick> getBricks() {
		return bricks;
	}

	public Scoreboard getSboard() {
		return sboard;
	}
}
