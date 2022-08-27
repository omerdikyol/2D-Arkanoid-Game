import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * Help.java
 * 
 * Create our help page
 */

@SuppressWarnings("serial")
public class Help extends JPanel{
	
	// Title
	private JLabel helpTitle;
	// Back Button
	private JButton back;
	
	// Background
	private Image backgroundImage;
	private ImageIcon myBackgroundIcon;
	
	// Help Image
	private JLabel help;
	private ImageIcon helpIcon;
	
	public Help(Game game) {
		setLayout(null);
		
		// Creating Title
		helpTitle = new JLabel("HELP", SwingConstants.CENTER);
		helpTitle.setFont(new Font("Neuropol",Font.BOLD, 36));
		helpTitle.setForeground(Color.white);
		helpTitle.setBounds(100, 0, Game.WIDTH - 200, 100);
		add(helpTitle);
		
		// Creating Help Image and Putting it to page
		help = new JLabel();
		helpIcon = new ImageIcon(getClass().getResource("help.png"));
		help.setIcon(helpIcon);
		help.setVisible(true);
		help.setBounds(Game.WIDTH/12 , 20, Game.WIDTH*5/6, Game.HEIGHT*5/6);
		add(help);
		
		// Creating back button
		back =LevelManager.createBackButton(game);
		add(back);
		
		// Setting Background
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}

	public JButton getBack() {
		return back;
	}

}
