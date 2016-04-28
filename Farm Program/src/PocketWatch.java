/** PocketWatch.java
 * 
 * This class creates a PocketWatch object that keeps track of time.
 *
 */

public abstract class PocketWatch implements Runnable {	
	private long time;				

	/** PicketWatch()
	 * 
	 * this constructor creates a PocketWatch with the time variable
	 * set to 0.
	 * 
	 */
	public PocketWatch() {
		time = 0;
	}

	/** run()
	 * 
	 * this class implements runnable, so the run() method has been over written.
	 * the run method will continue to run while the timeTicking() method still
	 * returns true. the only action this method does is increases the time value within 
	 * the PocketWatch by one unit every time that it is called.
	 * When the timeTicking() method returns false, then the stopTime() method is called
	 * before we exit out of the run() method.
	 * 
	 */

	@Override
	public void run() {
		while (timeTicking()) {	
			runTime();
			time++;
		}
		stopTime();
	}

	/** getTime()
	 * 
	 * returns that value of time
	 * 
	 * 
	 */

	public long getTime() {
		return time;
	}

	/** runTime()
	 * 
	 * this is an abstract method that is defined in the Time class.
	 * 
	 */

	public abstract void runTime();

	/** stopTime()
	 * 
	 * this is an abstract method that is defined in the Time class.
	 * 
	 */

	public abstract void stopTime();

	/** timeTicking()
	 * 
	 * this is an abstract method that is defined in the Time class.
	 * 
	 */

	protected abstract boolean timeTicking();	
}