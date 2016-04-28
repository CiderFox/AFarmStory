/** Farm.java
 * 
 * This class is home to the Farm Object.
 * The farm has two Farm States to tell whether the farm is ready for harvesting or not.
 * The farm has two PriorityBlockingQueues which are used for switching between ripe and not
 * ripened plants.
 * There is one instance of the random class which is used for getting the next time to ripen plants.
 * there is a timer, which is used for how long it should take the farmer to pick pumpkins.
 * 
 */

import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;


public class Farm {
	public enum FarmState {  HARVESTREADY, GROWING	}
	private FarmState state;
	private PriorityBlockingQueue<Plant> plants;
	private PriorityBlockingQueue<Plant> growingPlants;
	private static Random random;
	private long timer;
	
	/** Farm()
	 * 
	 * this constructor creates the farm with an initial capactiy of 1000.
	 * The state of the farm is set to growing.
	 * and the timer is set to the starting value.
	 * 
	 */
	
	public Farm() {
		state = FarmState.GROWING;
		plants = new PriorityBlockingQueue<Plant>(1000, new Compare());
		growingPlants = new PriorityBlockingQueue<Plant>(1000, new Compare());
		random = new Random();
		timer = -1;
	}
	/** setHarvestReady()
	 * 
	 * this method is used to set the farm to be ready for harvest.
	 * 
	 */
	
	public void setHarvestReady() {
		state = FarmState.HARVESTREADY;
	}
	
	/** isReady()
	 * 
	 * this is a boolean method that returns true if the farm is set
	 * to harvest ready and there is something to pick on the farm.
	 * 
	 */
	
	public boolean isReady() {
		if (state.equals(FarmState.HARVESTREADY) && countRipePumpkins() > 0) {
			return true;
		}
		return false;
	}
	
	/** countPlants()
	 * 
	 * this method returns the size of the array, which tells how many plants
	 * are currently on the farm
	 * 
	 */
	
	public int countPlants() {
		return plants.size();
	}
	
	/** countRipePumpkins()
	 * 
	 * this method counts the number of pumpkins that are ready for picking
	 * on the farm.
	 * 
	 */
	
	public int countRipePumpkins() {
		int pumpkins = 0;
		for (Plant plant : plants) {
			if (plant != null) pumpkins += plant.getPumpkins();
		}
		return pumpkins;
	}
	
	/** hasPumpkins()
	 * 
	 * boolean method that returns true if the size of ripe
	 * pumpkins on the farm is more that zero.
	 * 
	 */
	
	public boolean hasPumpkins() {
		if(countRipePumpkins() > 0) {
			return true;
		}
		return false;
	}
	
	/** addPlant()
	 * 
	 * this method adds a plant to both of the queues used on the farm. 
	 *
	 */
	
	public void addPlant(Plant plant) {
		plants.add(plant);
		growingPlants.add(plant);
	}
	
	/** harvestPumpkins(long currentTime)
	 * 
	 * this method allows the farmer to harvest the pumpkins from the farm, starting
	 * at the currentTime that the farmer called this method. this method returns the 
	 * number of pumpkins that were successfully picked from the plants
	 *
	 */
	
	public int harvestPumpkins(long currentTime) {
		int pumpkins = 0;
		for (Plant plant: plants) {
			if (plant.getPumpkins() > 0) {
				pumpkins += plant.pickPumpkin(); 	
				growingPlants.remove(plant);
				plant.setRipeTime(currentTime + Farm.nextPumpkin()); 
				growingPlants.add(plant);						
			}
		}	
		return pumpkins;
	} 
	
	/** plantSeeds(Plant plant)
	 * 
	 * This method is called when the farmer is planting seeds on the farm.
	 * the method its self uses the plant that is passed in and calls the 
	 * addPlant method.
	 *
	 */
	
	public void plantSeeds(Plant plant) {
		addPlant(plant);
	}
	
	/** nextRipeTime()
	 * 
	 * this method returns the value of the ripening time
	 * from the queue. 
	 *
	 */
	
	public long nextRipeTime() {
		if (growingPlants.peek() == null) return 0;
		return growingPlants.peek().getRipeTime();
	}
	
	/** ripenPumpkins(long time)
	 * 
	 * this method ripens the plants by removing them from
	 * the growing queue of plants. this method sets the new ripening
	 * time for each plant that has a pumpkin that has ripened.
	 * 
	 */

	public boolean ripenPumpkins(long time) {
		Plant plant = growingPlants.poll();
		boolean ripened = plant.ripenPumpkin();
		if (ripened) {
			plant.setRipeTime(time);
			growingPlants.add(plant);
		}
		return ripened;
	}
	
	/** removePlant()
	 * 
	 * this method removes a plant from the queue and returns
	 * 
	 */
	
	public Plant removePlant()  {		
		if (!plants.isEmpty()) {
			Plant plant = plants.poll();
			return plant;
		} else {
			throw new NullPointerException(); 	
		}
	}
	
	/** nextPumpkin()
	 * 
	 * this method gets the nextGaussian time for when a pumpkin
	 * should ripen on the farm.
	 *
	 */
	
	public static long nextPumpkin() {
		return (long)(10000 * random.nextGaussian() + 50000);
	}
	
	/** setTimer(long time)
	 * 
	 * this method starts the timer with the time that is passed in
	 * 
	 */
	
	public void setTimer(long time) {
		timer = time;
	}
	
	/** timerAlarm()
	 * 
	 * this method alerts when the time is up for harvesting pumpkins.
	 * 
	 */
	
	public long timerAlarm() {
		return timer;
	}
	
	/** class Compare
	 * 
	 * this class was created so that the plants can be compared
	 * by their given ripening time.
	 *
	 */
	
	public class Compare implements Comparator<Plant> {
		@Override
		public int compare(Plant firstPlant, Plant secondPlant) {
			if(firstPlant != null & secondPlant != null) {
				if (firstPlant.getRipeTime() < secondPlant.getRipeTime()) {
					return -1;
				}
				if (firstPlant.getRipeTime() > secondPlant.getRipeTime()) {
					return 1;
				}
			}
			return 0;
		}
	}
}