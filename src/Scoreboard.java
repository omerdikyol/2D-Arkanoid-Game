import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

/*
 * Scoreboard.java
 * 
 * Create our scoreboard label which is used in levels
 */

@SuppressWarnings("serial")
public class Scoreboard extends JLabel{
	
	private JLabel scoreLabel,levelLabel,livesLabel;
	
	public Scoreboard(int level,int score, int lives) {
		setLayout(new GridLayout(0,3, -40 , 0));
	
		// Create Score :
		scoreLabel = new JLabel();
		scoreLabel.setText(String.format("Score: %d",score));
		scoreLabel.setForeground(Color.white);
		scoreLabel.setHorizontalAlignment(JLabel.LEFT);
		add(scoreLabel);
		
		// Create Level
		levelLabel = new JLabel();
		levelLabel.setText(String.format("Level %d",level));
		levelLabel.setForeground(Color.white);
		levelLabel.setHorizontalAlignment(JLabel.CENTER);
		add(levelLabel);
		
		// Create Lives
		livesLabel = new JLabel();
		livesLabel.setText(String.format("Lives: %d", lives));
		livesLabel.setForeground(Color.white);
		livesLabel.setHorizontalAlignment(JLabel.RIGHT);
		add(livesLabel);
		
		setVisible(true);
	}
	
	// Update Score board in every change ( Used in Level )
	public void updateBoard(int score, int lives) {
		scoreLabel.setText(String.format("Score: %d",score));
		livesLabel.setText(String.format("Lives: %d", lives));
	}

}
