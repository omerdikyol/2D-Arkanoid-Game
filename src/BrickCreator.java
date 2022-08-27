import java.awt.Color;
import java.util.ArrayList;

/*
 * BrickCreator.java
 * 
 * Create our Bricks and put it in to level
 */

public class BrickCreator{

	private Brick[][] bricks;
	private int myLevelNum;
	private int brickCount = 1;

	public BrickCreator(Level level, int column, int row, int levelNum) {
		// Get column, row and gap amount from options page
		column = Options.getCurrColumn();
		row = Options.getCurrRow();
		int gap = Options.getCurrGap();
		
		// Create our 2D brick array
		bricks = new Brick[row][column];
		setBrickCount(row*column);
		
		int brick_width, brick_height;
		int brick_space_width, brick_space_height;
		myLevelNum = levelNum;
		
		// Determine the area the bricks will placed in the window
		brick_space_width = Game.WIDTH*2/3;	
		brick_space_height = Game.HEIGHT*1/8;
		
		// find every brick's height and width using brick_space_width and brick_space_height
		brick_width = ( brick_space_width -((column-1)*gap) )/ column;
		brick_height =( brick_space_height-((row-1)*gap) )/ row;
		
		for(int i = 0; i < row; i++){
			
			// Setting up the brick color
			Color c = colorPicker(i);
	
			for(int j = 0; j < column; j++)
			{
				// Create brick and add it to the relevant position
				bricks[i][j] = new Brick(c, level);
				bricks[i][j].setBounds(
						(Game.WIDTH - brick_space_width - 25) / 2 
						+ (brick_width+gap)*j, 100 
						+ (brick_height+gap)*i, brick_width, brick_height);
				level.add(bricks[i][j]);
			}		
		}
		
	}
	
	public Color colorPicker(int i) {
		// Choose bricks colors according to difficulty
		Color c = null;
		switch(myLevelNum) {
		case 1:
			c = Color.red;
			break;
			
		case 2:
			if(i%2 == 0) c = Color.orange;
			else if(i%2 == 1) c = Color.red;
			break;
		
		case 3:
			if(i%3 == 0) c = Color.black;
			else if(i%3 == 1) c = Color.orange;
			else if(i%3 == 2) c = Color.red;
			break;
			
		default:
			c = Color.red;
			break;
		}
		
		return c;
	}
	
	public ArrayList<Brick> getBricks(){
		// Create brick ArrayList from 2D array to send it to our level.
		ArrayList<Brick> b = new ArrayList<Brick>();
		for(int i = 0;i < bricks.length; i++) {
			for(int j = 0; j < bricks[0].length; j++) {
				b.add(bricks[i][j]);
			}
		}
		return b;
	}

	public int getBrickCount() {
		return brickCount;
	}

	public void setBrickCount(int brickCount) {
		this.brickCount = brickCount;
	}
}