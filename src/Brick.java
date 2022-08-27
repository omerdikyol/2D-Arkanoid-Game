import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

/*
 * Brick.java
 * 
 * To create our brick object and set its attributes depends on difficulty. (durability, color etc.)
 */

@SuppressWarnings("serial")
public class Brick extends JLabel {
	
	private int durability;

	Brick(Color c, Level level){
		setLayout(null);
		
		// Look to color and Set durability 
		if (c == Color.red) 
			durability = 1;
		else if (c == Color.orange) 
			durability = 2;
		else if (c == Color.black) 
			durability = 3;
		else
			durability = 1;
		
		setOpaque(true);
		setBackground(c);
		setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
		setVisible(true);
		
	}
	
	// Call when brick collided with ball
	// Change color if durability decreases
	// Returns 1 if brick should have to break.
	public int collide() {		
		this.durability--;
		switch(durability) {
		case 0: // Brick should break
			return 1;
		case 1:
			setBackground(Color.red);
			break;
		case 2:
			setBackground(Color.orange);
			break;
		}
		return 0;
	}

	public int getDurability() {
		return durability;
	}
}
