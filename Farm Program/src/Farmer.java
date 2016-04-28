/** Farmer.java
 * 
 * This class is home to the Farmer object.
 * The Farmer has objects he uses to run his farm, as well as a few variables to indicate his actions:
 * (1) The Farmer gets a FarmerState to display what his current actions are:
 * 		Harvesting pumpkins, Loitering when there is nothing to do and Delivering pumpkins the the UPuS.
 * (2) The farmer gets three objects to use for running his farm:
 * 	   One Farm (for growing pumpkins), One Barn (for storing pumpkins), and One Website (to receive orders).
 * (3) There are three variables used to help quide the farmer on what he is doing or what he can do:
 * 	   pumpkins (to state how many pumpkins he picked from the farm), harvestable to indicate if he is able to
 * 	   harvest pumpkins from the farm (that is if the barn isnt full). driving boolean to determine if the farmer
 * 	   is currently driving to the delivery service or not.
 * 
 */

public class Farmer implements Runnable {

	public enum FarmerState { 	LOITERING, HARVESTING, DELIVERING, UPROOTING 	}

	private Farm farm;
	private Barn barn;
	private Website website;
	private FarmerState state;			
	private boolean harvestable;
	private boolean driving;
	private int pumpkins;						

	/** Farmer(Farm farm, Barn barn, Website website)
	 * 
	 * The farmer is created with one farm, one barn and one website that was initialized in the Time class.
	 * all of his variables and objects are instantiated here.
	 * Because he just started his farm he is currently loitering around looking for something to do. He hasn't
	 * harvested any pumpkins, so pumpkins = 0, and further more he has not started doing any actions, and so he has
	 * not up rooted any plants (plantUpRooted = false). the driving variable is false to start with because the farmer
	 * is currently loitering on the farm.
	 * 
	 */

	public Farmer(Farm farm, Barn barn, Website website) {			
		this.farm = farm;
		this.barn = barn;
		this.website = website;
		state = FarmerState.LOITERING;
		harvestable = true;
		pumpkins = 0;
		driving = false;
	}

	/** run()
	 * 
	 * this is the method implemented to run the Farmer as a thread.
	 * The Farmers run initializes with responsibities:
	 * (1) Check the barn capacity and decide if farmer can fit more things in the barn.
	 * (2) Check if he can put pumpkins in the barn, if the farmer has decided previously that he cannot.
	 * (3) Check if the farm is ready for harvest and he can fit items in the barn.
	 * (4) Check if he has orders on his website and pumpkins to spare to fill those orders.
	 * (5) otherwise if there is nothing to do he waits until there is something to do.
	 * 
	 */

	@Override
	public synchronized void run() {
		while (true) {
			if (barn.isFull() && harvestable) {
				upRooting();
				harvestable = false;
			}
			if (!harvestable && barn.isEmpty()) {
				harvestable = true;
			}
			if (farm.isReady() && harvestable ) {
				harvesting();
			}
			if (website.hasOrders() && barn.hasPumpkins()) {
				delivering();
			}
			try {	
				state = FarmerState.LOITERING;
				if ( !(farm.hasPumpkins() && harvestable) && !(website.hasOrders() && barn.hasPumpkins()) ) {
					wait(); 
				}
			} catch (InterruptedException e) {}
		}
	}

	/** beginHarvest(long time)
	 * 
	 * This method begins the harvest, the time is passed in
	 * when the harvesting of pumpkins has begun, and will
	 * set the value of the pumpkins the farmer gathered from calling
	 * the harvestPumpkins method.
	 * 
	 * @param time
	 */

	public synchronized void beginHarvest(long time) {
		pumpkins = farm.harvestPumpkins(time);
	}

	/** completeHarvest()
	 * 
	 * This method completes the harvest and returns the number
	 * of pumpkins that were brought from the farm to the barn.
	 * 
	 */

	public synchronized int completeHarvest() {
		barn.addPumpkins(pumpkins); 						
		int returnCollected = pumpkins;
		pumpkins = 0;
		return returnCollected;
	}

	/** completeDelivery()
	 * 
	 * This method removes one pumpkin from the barn for delivery
	 * 
	 */

	public synchronized void completeDelivery(int orders) {
		for(int i =0; i < orders; i++) {
			barn.removePumpkin(); 
		}
	}

	/** shouldPlantSeeds()
	 * 
	 * This is a boolean method that is used to check wheather or not
	 * the farmer should plants seeds if his barn capacity is getting low.
	 * 
	 */

	public synchronized boolean shouldPlantSeeds() {
		if (barn.countPumpkins() <= 2 && barn.countPumpkins() > 0) {
			return true;
		}
		return false;
	}

	/** currentState() 
	 * 
	 * this method returns the current that that the farmer is in
	 *
	 */

	public FarmerState currentState() {
		return state;
	}

	/** loitering()
	 * 
	 * this method puts the farmer into the loitering state
	 * 
	 */

	public synchronized void loitering() {
		state = FarmerState.LOITERING;
	}

	/** harvesting()
	 * 
	 * This method puts the farmer into the harvesting FarmerState, sending
	 * the farmer to the Farm. Also puts the thread to sleep so that the time 
	 * method can calculate how many pumpkins the farmer harvested.
	 * 
	 */

	public synchronized void harvesting() {
		state = FarmerState.HARVESTING;
		while (state == FarmerState.HARVESTING) {
			try {
				wait();
			} catch (InterruptedException e) {			}
		}
	}	

	/** delivering()
	 * 
	 * This method puts the farmer in the Delivery state, and
	 * sends the farmer to go Deliver the pumpkins. Also puts the thread 
	 * to sleep so that the time method can calculate how long it takes
	 * the farmer to complete the delivery.
	 * 
	 */

	public synchronized void delivering() {
		state = FarmerState.DELIVERING;
		while (state == FarmerState.DELIVERING) {
			try {
				wait();
			} catch (InterruptedException e) {	}
		}
	}

	/** upRooting()
	 * 
	 * this method sets the farmer to upRooting state.
	 * 
	 */

	private void upRooting()  {
		state = FarmerState.UPROOTING;
		while (state == FarmerState.UPROOTING) {
			try {
				wait();
			} catch (InterruptedException e) {	}
		}
	}
	
	/** getDriving()
	 * 
	 * this method returns the value currently stored 
	 * in the driving boolean variable.
	 * 
	 */
	
	public boolean getDriving() {
		return driving;
	}
	
	/** isDriving()
	 * 
	 * this method sets the driving variable to true.
	 * 
	 */
	
	public void isDriving() {
		driving = true;
	}
	
	/** notDriving()
	 * 
	 * this method sets the driving variable to false.
	 * 
	 */
	
	public void notDriving() {
		driving = false;
	}
}