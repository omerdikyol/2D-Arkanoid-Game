import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/*
 * Paddle.java
 * 
 * To create our paddle object and set its movement.
 */

@SuppressWarnings("serial")
public class Paddle extends JLabel implements KeyListener, MouseMotionListener{
	
	// Variables
	private int paddle_size = 100;
	private int paddle_pos_x = (Game.WIDTH-getPaddle_size())/2;
	private int paddle_pos_y = 600;
	
	// Paddle image
	private ImageIcon icon;
	
	public Paddle(Level level) {
		setBounds(paddle_pos_x, paddle_pos_y, paddle_size, 20);
		icon = new ImageIcon(getClass().getResource("paddle.png"));
		setIcon(icon);
		setVisible(true);
		
		addKeyListener(this);
	}

	// Function to control paddle with left and right or A and D keys
	@Override
	public void keyPressed(KeyEvent e) {
		// If Keyboard is chosen from the Settings
		if(Options.getMouse_or_key() == 1) {
			// Paddle's Movement
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
				if(paddle_pos_x < Game.WIDTH - paddle_size - 10) {	// Paddle's Limit for Right Movement 
					// Move to the right
					paddle_pos_x += 15;
					setLocation(paddle_pos_x, paddle_pos_y);
				}
			}
			
			else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
			{
				if(paddle_pos_x > 0) {	// Paddle's Limit for Left Movement 
					// Move to the left
					paddle_pos_x -= 15;
					setLocation(paddle_pos_x, paddle_pos_y);
				}
			}
			
			// If i change settings from Options page, this listener was losing focus so it was not reading my bindings,
			// with this function, i request focus again so that i can move the paddle with keyboard.
			getTopLevelAncestor().requestFocus();
			
			repaint();
		}
	}


	// Function to control paddle with mouse movement
	@Override
	public void mouseMoved(MouseEvent e) {
		if(Options.getMouse_or_key() == 0) {
			double mouse_x = e.getPoint().getX(); 			// Get current x position of mouse
			paddle_pos_x = (int) mouse_x - paddle_size/2;	// Put cursor in the middle of paddle
			setLocation(paddle_pos_x, paddle_pos_y);		// Set Paddle's position
			repaint();
		}
	}
	
	// Getters and Setters
	
	public int getPaddle_pos_x() {
		return paddle_pos_x;
	}

	public void setPaddle_pos_x(int paddle_pos) {
		this.paddle_pos_x = paddle_pos;
	}

	public int getPaddle_size() {
		return paddle_size;
	}
	
	public int getPaddle_pos_y() {
		return paddle_pos_y;
	}
	
	// Unnecessary Functions
	@Override
	public void mouseDragged(MouseEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
