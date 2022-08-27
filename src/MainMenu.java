import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * MainMenu.java
 * 
 * Creating Our Main Menu
 */

@SuppressWarnings("serial")
public class MainMenu extends JPanel{
	
	// Main Menu Buttons
	private JButton b1,b2,b3,b4,b5,b6;
	
	// Background
	protected Image backgroundImage;
	private ImageIcon myBackgroundIcon;
	
	// Button Menu
	private JLabel buttonMenu;
	private GridLayout layout;
	private int menu_width;
	private int menu_height;
	
	// Logo
	private JLabel logo;
	private ImageIcon logoIcon;

	
	public MainMenu(Game game) { // I initialized game value so i can use ActionListener on the file Game.java
		setLayout(null);

		// Setting Background
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Creating Logo
		logo = new JLabel();
		logoIcon = new ImageIcon(getClass().getResource("newmet.gif"));
		logo.setIcon(logoIcon);
		logo.setVisible(true);
		logo.setBounds(Game.WIDTH/4, 100, Game.WIDTH - 200, 200);
		add(logo);
		

		// Creating Button Menu
		menu_width = Game.WIDTH - 200;
		menu_height = 200;
		buttonMenu = new JLabel();
		layout = new GridLayout(3,3,10,10);
		buttonMenu.setLayout(layout);
		buttonMenu.setVisible(true);
		buttonMenu.setOpaque(false);
		buttonMenu.setBounds(100,Game.HEIGHT - menu_height - 100,menu_width,menu_height);
		add(buttonMenu);

		// Creating Buttons
		b1 = createButton("New Game", game);
		b2 = createButton("Options", game);
		b3 = createButton("Scores",game);
		b4 = createButton("Help",game);
		b5 = createButton("About",game);
		b6 = createButton("Exit", game);
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

	// Getters
	
	public JButton getB1() {
		return b1;
	}

	public JButton getB2() {
		return b2;
	}

	public JButton getB3() {
		return b3;
	}

	public JButton getB4() {
		return b4;
	}

	public JButton getB5() {
		return b5;
	}

	public JButton getB6() {
		return b6;
	}
}
