/** Time extends PocketWatch
 * 
 *  This class gives Time to the PocketWatch class.
 *  Time starts the objects needed to run a farm business:
 *  
 *  One Farmer who is initialized with One Farm to grow their merchandise,
 *  One barn that is used to store their stash of merchandise, and One 
 *  website they use to receive orders for customers who want to purchase their 
 *  merchandise.
 *  
 *  This Class also initializes a DeliveryService that is used to transport the
 *  merchandise. As well as initializes a Journal, that is used to record the happenings
 *  on the farm.
 *  
 *  Two threads are started up within Time, The FarmerThread that sends jack to start
 *  working on the farm, and the ClockThread that starts the time on the PocketWatch.
 * 
 */

public class Time extends PocketWatch {
	private Journal notebook;						
	private Farm farm;	
	private Barn barn;
	private Website website;
	private Farmer jack;	
	private DeliveryService deliveryService;
	private int number;

	Thread jackThread;							
	Thread timeThread;	

	/** Time()
	 * 
	 * This is the constructor that initializes all the variables used for the Farm.
	 * (1) creates a file used for outputting all the recordings of the farm.
	 * (2) creates the farmer with his farm, barn and website.
	 * (3) creates the delivery service.
	 * (4) creates the threads for the farmer and time, and starts them both
	 * (5) A method is called to start the business up for the farmer. This method
	 * initializes the farm to have 1000 plants, and sets the timer for when an order
	 * should be expected to come in.
	 * 
	 */

	public Time() {
		notebook = new Journal();
		notebook.openJournal();
		farm = new Farm();
		barn = new Barn();
		website = new Website();
		jack = new Farmer(farm, barn, website);
		deliveryService = new DeliveryService();
		startFarm();
		jackThread = new Thread(jack);
		jackThread.start();
		timeThread = new Thread(this);
		timeThread.start();
	}

	/** startFarm()
	 * 
	 * This method is used to start up the business. Starts the farm with 1000 plants
	 * and also starts the timer for the website, indicating when the next order should arrive.
	 * 
	 */

	public void startFarm() {
		for (int i = 0; i < 1000; i++) {
			farm.addPlant(new Plant(getTime() + Farm.nextPumpkin()));
			notebook.note(getTime(), "Seed Planted", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
		}
		website.setNextOrderTime(100000);
	}

	/** timeTicking()
	 * 
	 * This method was created in the PocketWatch class
	 * and sets the time for the simulation to run; 
	 * which is 1000000 units.
	 * 
	 */

	@Override
	protected boolean timeTicking() {	
		return getTime() <= 1000000;
	}

	/** runTime()
	 * 
	 * this method is called while the continueCondition is valid.
	 * This is the run() method of the PocketWatch Thread, and gives the 
	 * PocketWatch something to look for and notifies anything to the farmer:
	 * 
	 * methods listed in order that they are displayed in the method:
	 * (1) if there is a new order placed on the website, notify() farmer.
	 * (2) if there is a new ripe pumpkin on the farm, notify() farmer.
	 * (3) if farmer is noticed to be delivering, record the time it takes for them to deliver.
	 * (4) if farmer is noticed to be harvesting, record how many pumpkins were harvested. 
	 * 		(and adjust the timer for picking pumpkins accordingly 2n; where n = number of pumpkins).
	 * (5) if farmer is noticed planting seeds, record that there were seeds planted on the farm.
	 * (6) if farmer is noticed uprooting a plant, record that there were 5 plants removed from farm.
	 * 
	 */


	@Override
	public synchronized void runTime() {

		while (getTime() == website.nextOrderTime()) {
			website.setNextOrderTime(getTime());
			website.receiveOrder(getTime());
			notebook.note(getTime(), "Order Placed", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
			synchronized(jack) {
				jack.notify(); 
			}
		}
		while (getTime() == farm.nextRipeTime()) {
			if ( farm.ripenPumpkins(getTime() + Farm.nextPumpkin()) ) {
				notebook.note(getTime(), "Ripe Pumpkin", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
				farm.setHarvestReady();
				synchronized(jack) {
					jack.notify();
				}
			}
		}	
		if (jack.currentState() == Farmer.FarmerState.DELIVERING && deliveryService.timerAlarm() < getTime()) {
			deliveryService.setDuration();
			deliveryService.setTimer(getTime());
			number = website.completeOrder(getTime(), barn.countPumpkins());
			notebook.note(getTime(), "Begin delivery", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
			jack.isDriving();
		}	
		if (getTime() == deliveryService.timerAlarm()) {	
			jack.completeDelivery(number);
			notebook.note(getTime(), "Delivery Time: " + deliveryService.getDuration()+ "", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
			website.updateRecords();
			deliveryService.resetDuration();
			if (jack.shouldPlantSeeds()) {
				barn.removePumpkin();
				for(int i =0; i < 4; i++) {
					farm.plantSeeds(new Plant(getTime() + Farm.nextPumpkin()));
					notebook.note(getTime(), "Seed Planted", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
				}
			}
			synchronized(jack) {
				jack.notDriving();
				jack.loitering(); 
				jack.notify();
			}
		}
		if (jack.currentState() == Farmer.FarmerState.HARVESTING && farm.timerAlarm() < getTime()) {
			farm.setTimer(getTime() + (2 * farm.countRipePumpkins() ) );
			notebook.note(getTime(), "Begin Harvest", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
			jack.beginHarvest(getTime());		
		}

		if (getTime() == farm.timerAlarm()) {
			int collected = jack.completeHarvest();
			notebook.note(getTime(), "Harvested " + collected, barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());			
			synchronized(jack) {
				jack.loitering(); 
				jack.notify();
			}	
		}
		if(jack.currentState() == Farmer.FarmerState.UPROOTING) {
			for(int i =0; i < 5; i++) {
				farm.removePlant();
				notebook.note(getTime(), "Plant Uprooted", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(), website.countPendingOrders());
			}
			synchronized(jack) {
				jack.loitering(); 
				jack.notify();
			}
		}
	}

	/** stopTime()
	 * 
	 * This method is used when the continueCondition is no longer valid.
	 * This method will print out the results of the farm to the journal file containing:
	 * (1) the amount of pumpkins in the barn.
	 * (2) the number of orders that were filled
	 * (3) the mean time that it took the farmer to complete a customer order.
	 * (4) the standard deviation of the orders fill time.
	 * 
	 * This method also exits the program, killing any active threads.
	 * This method will check to see if jack is driving or not before it terminates
	 * 
	 */

	@Override
	public void stopTime() {
		if(jack.getDriving() == false) {
			notebook.closeJournal(barn.countPumpkins(), website.getCompletedOrders(), website.getMean(), website.getStandardDeviation());
			System.exit(0);
		}else {
			jack.completeDelivery(number);
			notebook.note(getTime(), "Delivery Time: " + deliveryService.getDuration()+ "", barn.countPumpkins(), farm.countPlants(), farm.countRipePumpkins(),website.countPendingOrders());
			website.updateRecords();
			notebook.closeJournal(barn.countPumpkins(), website.getCompletedOrders(), website.getMean(), website.getStandardDeviation());
			System.exit(0);
	
		}
	}
}