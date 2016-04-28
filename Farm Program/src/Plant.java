

/** Plant.java
 * 
 * This is the Plant class that creates plant objects that are used
 * to plant on the farm and grow pumpkins
 *
 */


public class Plant {
	private int pumpkins;				
	private long nextPumpkin;		
	
	/** Plant()
	 * 
	 * This constructor initializes the Plant object to have currently
	 * no ripened pumpkins on it's vines, and also initializes when the 
	 * next pumpkin should be grown to zero.
	 * 
	 */
	
	public Plant() {
		pumpkins = 0;
		nextPumpkin = 0;
	}
	
	/** Plant(long time)
	 * 
	 * this constructor initializes the plant to start growing a pumpkin.
	 * the time for when the pumpkin should be ripe is used with time
	 * that is passed into the method.
	 * 
	 */
	
	public Plant(long time) {
		nextPumpkin = time;
	}

	/** ripenPumpkin
	 * 
	 * this method is used to ripen pumpkins on the plants.
	 * this method returns true if the pumpkin was able to be
	 * ripened.
	 * 
	 */
	
	public boolean ripenPumpkin() {
		if (pumpkins < 2) {
			pumpkins++;
			return true;
		} else {
			return false;
		}
	}
	
	/** getPumpkins()
	 * 
	 * this method returns the number of pumpkins a plant has ripened.
	 * 
	 */
	
	public int getPumpkins() {
		return pumpkins;
	}
	
	/** pickPumpkin()
	 * 
	 * this method is used to pick the pumpkins off of the plant,
	 * and resets the value of pumpkins on the plant to zero.
	 * 
	 */
	
	public int pickPumpkin() {
		int harvest = pumpkins;
		pumpkins = 0;
		return harvest;
	}
	
	/** getRipeTime()
	 * 
	 * this method returns the time when the pumpkin on a particular plant
	 * should be ripened.
	 * 
	 */
	
	public long getRipeTime() {
		return nextPumpkin;
	}
	
	/** setRipeTime(long time)
	 * 
	 * this method is used to set the ripening time of a pumpkin
	 * with the time that was passed in the method.
	 */
	
	public void setRipeTime(long time) {
		nextPumpkin = time;
	}

}