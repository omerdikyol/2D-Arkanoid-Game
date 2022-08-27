import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/*
 * LevelManager.java
 * 
 * Creating LevelManager page where we can choose our level
 */

@SuppressWarnings("serial")
public class LevelManager extends JPanel{
	
	// Level and Back Buttons
	private JButton level1,level2,level3;
	private JButton back;
	
	// Background
	protected Image backgroundImage;
	private ImageIcon myBackgroundIcon;
	
	private JLabel selectLevel;
	
	// Button Menu
	private JLabel buttonMenu;
	private GridLayout layout;
	private int menu_width;
	private int menu_height;
	
	
	public LevelManager(Game game) { // I initialized game value so i can use ActionListener on the file Game.java
		setLayout(null);

		// Setting Background
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Creating "Choose Level" text
		selectLevel = new JLabel("Select Level",SwingConstants.CENTER);
		selectLevel.setVisible(true);
		selectLevel.setBounds(100, 100, Game.WIDTH - 200, 200);
		selectLevel.setFont(new Font("Neuropol",Font.BOLD, 36));
		selectLevel.setForeground(Color.white);
		add(selectLevel);

		// Creating Button Menu
		menu_width = Game.WIDTH - 200;
		menu_height = 300;
		buttonMenu = new JLabel();
		layout = new GridLayout(5,5,10,10);
		buttonMenu.setLayout(layout);
		buttonMenu.setVisible(true);
		buttonMenu.setOpaque(false);
		buttonMenu.setBounds(100,Game.HEIGHT - menu_height - 100,menu_width,menu_height);
		add(buttonMenu);

		// Creating Buttons
		level1 = createButton("Level1", game);
		level2 = createButton("Level2", game);
		level3 = createButton("Level3",game);
		
		// Creating Back Button
		back = createButton("MainMenu",game);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}
	
	public JButton createButton(String name, Game game){
		JButton b = new JButton(name);
		b.setVisible(true);
		b.setFocusable(false);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setFont(new Font("Gameplay",Font.PLAIN, 12));
		b.setForeground(Color.white);
		b.addActionListener(game);	// To use ActionListener on Game.java
		buttonMenu.add(b);
		return b;
	}
	
	public static JButton createBackButton(Game game) {
		JButton b = new JButton("Back");
		b.setVisible(true);
		b.setFocusable(false);
		b.setOpaque(false);
		b.setContentAreaFilled(false);
		b.setFont(new Font("Gameplay",Font.PLAIN, 12));
		b.setForeground(Color.white);
		b.addActionListener(game);	// To use ActionListener on Game.java
		b.setBounds(Game.WIDTH - 200, Game.HEIGHT- 100, 100, 30);
		return b;
	}

	// Getters 
	
	public JButton getLevel1() {
		return level1;
	}

	public JButton getLevel2() {
		return level2;
	}

	public JButton getLevel3() {
		return level3;
	}

	public JButton getBack() {
		return back;
	}
}
