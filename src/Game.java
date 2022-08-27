import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/*
 * Game.java
 * 
 * Create our game and call functions we created.
 */

@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, KeyListener{
	
	// Window size
	public static final int HEIGHT = 800;
	public static final int WIDTH = 600;
	
	// Background
	public URL BG = getClass().getResource("bg.jpg");
	
	// Game Windows
	private MainMenu myMainMenu;
	private Help myHelp;
	private LevelManager myLevelManager;
	private Level level1, level2, level3;
	private Scores myScores;
	private Options myOptions;
	
	// Components
	private CardLayout cd;
	private Container contentPane;
	
	// Checking if user entered one of the levels
	private boolean in_level = false;
	
	// Checking if user entered to options
	public boolean in_options = false;
	
	// Maximum Level we Created
	private static final int MAX_LEVEL = 3;
	
	public Game() {

		// Setting up components
		cd = new CardLayout();
		contentPane = getContentPane();
		contentPane.setLayout(getCd());
		addKeyListener(this);
		

		// Setting up window settings
		setLayout(getCd());
		setTitle("Arkanoid");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // User shouldn't close the game with exit button, should use CTRL + Q
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);
		ImageIcon logo = new ImageIcon(getClass().getResource("comet.png"));
		setIconImage(logo.getImage());
		
		
		// Creating Main Menu		
		myMainMenu = new MainMenu(this);
		createPages(myMainMenu, "MainMenu");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		// Setting Main Menu Button Operations
		
		// New Game
		if(src == myMainMenu.getB1()) {
			myLevelManager = new LevelManager(this);
			createPages(myLevelManager, "LevelManager");
			
			cd.show(contentPane, "LevelManager");
		}
		
		// Options
		else if (src == myMainMenu.getB2()) {
			myOptions = new Options(this);
			createPages(myOptions, "Options");
			in_options = true;
			
			cd.show(contentPane, "Options");
		}
		
		// Scores
		else if (src == myMainMenu.getB3()) {
			myScores = new Scores(this);
			createPages(myScores, "Scores");
			
			cd.show(contentPane, "Scores");
		}
		
		// Help
		else if (src == myMainMenu.getB4()) {
			myHelp = new Help(this);
			createPages(myHelp, "Help");
	
			cd.show(contentPane, "Help");
		}
		
		// About
		else if (src == myMainMenu.getB5()) {
			String info = "Developer Name: Ömer Dikyol\nSchool Number: 20200702002\nEmail: omer.dikyol@std.yeditepe.edu.tr";
			JOptionPane.showMessageDialog(null, info, "About", JOptionPane.PLAIN_MESSAGE);
			
		}
		
		// Exit
		else if (src == myMainMenu.getB6()) {
			System.exit(0);
		}	
		
		
		// Setting Level Manager Button Operations
		if(myLevelManager != null) {
			// Create Level 1
			if (src == myLevelManager.getLevel1()) {
				level1 = new Level(this, 1, 0, 3);
				createLevel(level1, "Level1");
				in_level = true;
				
				cd.show(contentPane, "Level1");
			}
			// Create Level 2
			else if (src == myLevelManager.getLevel2()) {
				level2 = new Level(this, 2, 0, 3);
				createLevel(level2, "Level2");
				in_level = true;
				
				cd.show(contentPane, "Level2");
			}
			// Create Level 3
			else if (src == myLevelManager.getLevel3()) {
				level3 = new Level(this, 3, 0, 3);
				createLevel(level3, "Level3");
				in_level = true;
				
				cd.show(contentPane, "Level3");
			}
			// MainMenu Button
			else if(src == myLevelManager.getBack()) {
				cd.show(contentPane, "MainMenu");
			}
			
		}
		// Setting Back Button's Operations
		if (myHelp != null && src == myHelp.getBack()) {
			myHelp = null;
			cd.show(contentPane, "MainMenu");
		}
		else if (myScores != null && src == myScores.getBack()) {
			myScores = null;
			cd.show(contentPane, "MainMenu");
		}
		else if (myOptions != null && src == myOptions.getBack()) {
			in_options = false;
			myOptions = null;
			cd.show(contentPane, "MainMenu");
		}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// If CTRL + Q is pressed any time in game
        if ( ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) && (e.getKeyCode() == KeyEvent.VK_Q) ) {
            System.exit(0);
        }
	}
	
	public void createPages(Component c, String name) {
		// Create windows
		add(c);
		c.setBounds(0, 0, HEIGHT, WIDTH);
		contentPane.add(c, name);
	}
	
	public void createLevel(Level l, String name) {
		// Create Levels
		add(l);
		l.setBounds(0, 0, HEIGHT, WIDTH);
		contentPane.add(l, name);
		// Add control off paddle
		addKeyListener(l.getMyPaddle());
	}

	// Getters and Setters
	public boolean isIn_level() {
		return in_level;
	}

	public void setIn_level(boolean in_level) {
		this.in_level = in_level;
	}
	
	public CardLayout getCd() {
		return cd;
	}

	public static int getMaxLevel() {
		return MAX_LEVEL;
	}
	
	// Unnecessary Functions
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}




}
