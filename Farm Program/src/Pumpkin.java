/** Pumpkin.java
 * 
 * This class is not needed, but kept this class to keep the object
 * oriented idea. 
 * This class creates pumpkin objects that are used for the Barn class.
 * 
 */

import java.util.Random;

public class Pumpkin {
	private String color;
	private boolean ripe;

	/** Pumpkin()
	 * 
	 * this constructor creates a pumpkin with the ripe value set to true.
	 * there is also a color value that calls the setColor() method to
	 * decide the color of the pumpkin.
	 * 
	 */
	
	public Pumpkin() {
		color = setColor();
		ripe = true;
	}
	
	/** getColor()
	 * 
	 * this method returns the color value of a given pumpkin
	 * 
	 */
	
	public String getColor() {
		return color;
	}

	/** setColor()
	 * 
	 * this method uses an instance of the random class to generate a random
	 * number between 1 and 3.
	 * based on the value of the random number, the method instatiates the color 
	 * value.
	 *
	 */
	
	public String setColor() {
		int min = 1;
		int max = 3;
		Random random = new Random();
		switch(random.nextInt(max - min + 1) + min) {
		case 1:
			 color = "Orange";
		case 2:
			 color = "Yellow";
		case 3:
			 color = "White";
		}
		return color;
	}
}
