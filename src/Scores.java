import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Scores.java
 * 
 * Get Scores from scores.txt file, and put it to Scores page according to the rules
 */

@SuppressWarnings("serial")
public class Scores extends JPanel {
	
	// Score Menu
	private JLabel scoresTitle, scoresTable;
	private JButton back;
	
	// File Reader and ArrayList to put our data 
	private FileReader fr;
	private BufferedReader reader;
	private ArrayList<String> var;
	private Iterator<String> itr;
	
	// Background
	protected Image backgroundImage;
	private ImageIcon myBackgroundIcon;
	
	public Scores(Game game) {
		
		
		// Creating Title
		scoresTitle = new JLabel("SCORES", SwingConstants.CENTER);
		scoresTitle.setFont(new Font("Neuropol",Font.BOLD, 36));
		scoresTitle.setForeground(Color.white);
		scoresTitle.setBounds(100, 0, Game.WIDTH - 200, 100);
		add(scoresTitle);
		
		// Creating Score Table (to put all our data to it)
		scoresTable = new JLabel();
		scoresTable.setLayout(new GridLayout(0,4, 0, 0)); // Make it GridLayout to put variables easier
		scoresTable.setVisible(true);
		scoresTable.setBounds(50, 100, Game.WIDTH-100, Game.HEIGHT-200);
		scoresTable.setForeground(Color.white);

		addToTable("Name",18);
		addToTable("Score",18);
		addToTable("Brick Per Level",12);
		addToTable("Date", 18);
		add(scoresTable);
		
		// Window Settings
		setLayout(null);
		
		// Setting Background
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Creating back button
		back =LevelManager.createBackButton(game);
		add(getBack());	
		
		// We are going to use it to get data from file
		var = new ArrayList<String>();
		String s;
			
		try {
			// Getting Data From File Line by Line
			fr = new FileReader(getClass().getResource("scores.txt").getPath());
			reader = new BufferedReader(fr);
			while((s = reader.readLine()) != null) {
				// Add data to ArrayList
				var.add(s);
			}
			
			// Sort data it with our ScoreCompare Class
			ScoreCompare scoreCompare = new ScoreCompare();
			Collections.sort(var, scoreCompare);
			
			
			// Getting highest 10 scores
			int count = 0;	// Count for getting top 10
			itr = var.iterator();
			
			while(itr.hasNext()) {
				if(count == 10) break;
				String line = itr.next();
				
				// Adding values from File to Score Table
				String txtToArray[] = line.split(" | ");
				
				addToTable(txtToArray[2],12); 					// Adding Score
				addToTable(txtToArray[0],12); 					// Adding Name
				addToTable(txtToArray[txtToArray.length-5],12); // Adding Brick Per Level
				addToTable(txtToArray[txtToArray.length-2] + " " + txtToArray[txtToArray.length-1],10);	// Adding date and time
				
				count++;
			}
			
			fr.close();
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}
	
	// Function to add relevant data to the our label scoresTable
	public JLabel addToTable(String name, int fontSize) {
		JLabel jl = new JLabel(name);
		jl.setVerticalAlignment(JLabel.CENTER);
		jl.setFont(new Font("Gameplay",Font.PLAIN, fontSize));
		jl.setForeground(Color.white);
		jl.setVisible(true);
		scoresTable.add(jl);
		
		return jl;
	}
	
	// Getter for back button
	
	public JButton getBack() {
		return back;
	}

}

// Our Comparator class to sort the data by score
class ScoreCompare implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		int o1Score = Integer.parseInt( o1.split(" ")[0] );
		int o2Score = Integer.parseInt( o2.split(" ")[0] );
		return o2Score - o1Score;
	}
}
