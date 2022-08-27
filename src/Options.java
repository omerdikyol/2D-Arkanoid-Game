import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/*
 * Options.java
 * 
 * Creating our Options Page
 */

@SuppressWarnings("serial")
public class Options extends JPanel {
	
	// Our title and Label to add Sliders and ComboBoxes
	private JLabel optionsTitle, optionsLabel;
	
	// Back Button
	private JButton back;
	
	// Objects to change settings
	private JComboBox<String> paddleControl;
	private JSlider row,column,gap, ball_speed;
	private JCheckBox audio;
	
	// Background
	protected Image backgroundImage;
	private ImageIcon myBackgroundIcon;
	
	// Paddle Control Setting 
	// 1 -> left and right buttons
	// 0 -> mouse
	private static int mouse_or_key = 1;
	
	// Audio Settings
	// 1 -> Audio Enabled
	// 0 -> Audio Disabled
	private static int audio_enabled = 1;
	
	// Default Settings
	private static int currRow=5, currColumn=5, currGap=2;

	public Options(Game game) {
		
		setLayout(null);
		
		// Setting Background
		try {
			myBackgroundIcon = new ImageIcon(game.BG);
			backgroundImage = myBackgroundIcon.getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Creating Title
		optionsTitle = new JLabel("OPTIONS", SwingConstants.CENTER);
		optionsTitle.setFont(new Font("Neuropol",Font.BOLD, 36));
		optionsTitle.setForeground(Color.white);
		optionsTitle.setBounds(100, 0, Game.WIDTH - 200, 100);
		add(optionsTitle);

		
		// Creating back button
		back =LevelManager.createBackButton(game);
		add(back);
		
		// Creating Options
		optionsLabel = new JLabel();
		optionsLabel.setLayout(new GridLayout(0,2, 0, 0));
		optionsLabel.setVisible(true);
		optionsLabel.setBounds(50, 100, Game.WIDTH - 100, Game.HEIGHT-200);
		optionsLabel.setForeground(Color.white);
		add(optionsLabel);
		
		
		// Audio Effects
		createText("Audio Effects");
		audio = new JCheckBox();
		audio.setVisible(true);
		audio.setFocusable(false);
		audio.setOpaque(false);
		if(Options.getAudio_enabled() == 1) audio.setSelected(true);
		audio.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				setAudio_enabled((e.getStateChange()==1) ? 1:0);
			}
		});
		optionsLabel.add(audio);
		
		
		// Paddle Control ( Keyboard or Mouse ) 
		createText("Paddle Control :");
		String[] choices = {"Keyboard", "Mouse"};
		paddleControl = new JComboBox<String>(choices);
		paddleControl.setVisible(true);
		paddleControl.setFocusable(false);
		paddleControl.setOpaque(false);
		// Save the choice if user leaves options page
		if(Options.getMouse_or_key() == 1) paddleControl.setSelectedIndex(0);
		else paddleControl.setSelectedIndex(1);
		paddleControl.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String s = (String) paddleControl.getSelectedItem();
				if(s.equals("Keyboard")) {
					Options.setMouse_or_key(1);
				}
				else if(s.equals("Mouse")) {
					Options.setMouse_or_key(0);
				}
			}
			
		});
		optionsLabel.add(paddleControl);

		// Brick row, column, gap, ball_speed Options
		createText("Column Amount of Brick: ");
		column = createSlider(1,8,Options.getCurrColumn());
		column.setVisible(true);
		column.setFocusable(false);
		column.setOpaque(false);
		column.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				Options.setCurrColumn(value);
			}
			
		});
		optionsLabel.add(column);
		
		
		createText("Row Amount of Brick: ");
		row = createSlider(1,8,Options.getCurrRow());
		row.setVisible(true);
		row.setFocusable(false);
		row.setOpaque(false);
		row.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				Options.setCurrRow(value);
			}
			
		});
		optionsLabel.add(row);
		
		createText("Gap Between Bricks: ");
		gap = createSlider(1,6,Options.getCurrGap());
		gap.setVisible(true);
		gap.setFocusable(false);
		gap.setOpaque(false);
		gap.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				Options.setCurrGap(value);
			}
		});
		optionsLabel.add(gap);
		gap.setFocusable(false);
		
		createText("Ball Speed: ");
		ball_speed = createSlider(1,5, -Ball.getBall_speed());
		ball_speed.setVisible(true);
		ball_speed.setFocusable(false);
		ball_speed.setOpaque(false);
		ball_speed.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int value = source.getValue();
				Ball.setBall_speed(-value);
			}
		});
		optionsLabel.add(ball_speed);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw the background image.
		g.drawImage(backgroundImage, 0, 0, null);
	}
	
	// Function to Create Text
	public JLabel createText(String name) {
		JLabel jl = new JLabel(name);
		jl.setFont(new Font("Neuropol",Font.BOLD, 14));
		jl.setForeground(Color.white);
		jl.setBounds(100, 0, Game.WIDTH - 200, 100);
		optionsLabel.add(jl);
		return jl;
	}
	
	// Function to Create Slider
	public JSlider createSlider(int min, int max, int init) {
		JSlider s = new JSlider(JSlider.HORIZONTAL, min,max, init);
		s.setOpaque(false);
		s.setFont(new Font("Neuropol",Font.BOLD, 14));
		s.setPaintTrack(true);
		s.setPaintTicks(true);
		s.setPaintLabels(true);
		s.setMajorTickSpacing(1);;
		s.setForeground(Color.white);
		optionsLabel.add(s);
		return s;
	}

	// Getters and Setters
	
	public JButton getBack() {
		return back;
	}
	
	public static int getCurrColumn() {
		return currColumn;
	}

	public static void setCurrColumn(int currCol) {
		Options.currColumn = currCol;
	}

	public static int getCurrRow() {
		return currRow;
	}

	public static void setCurrRow(int currRow) {
		Options.currRow = currRow;
	}

	public static int getCurrGap() {
		return currGap;
	}

	public static void setCurrGap(int currGap) {
		Options.currGap = currGap;
	}

	public static int getMouse_or_key() {
		return mouse_or_key;
	}

	public static void setMouse_or_key(int mouse_or_key) {
		Options.mouse_or_key = mouse_or_key;
	}

	public static int getAudio_enabled() {
		return audio_enabled;
	}

	public static void setAudio_enabled(int audio_enabled) {
		Options.audio_enabled = audio_enabled;
	}

}
