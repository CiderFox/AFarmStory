/** DeliveryService.java
 * 
 * this class holds values for completing a delivery trip.
 * this class has a duration that holds the time it took for
 * a delivery to complete.
 * this class has a timer that holds the time allowed for a 
 * delivery.
 * 
 */
import java.util.Random;

public class DeliveryService {
	private static Random random;
	private long duration;
	private long timer;
	
	/** DeliveryService()
	 * 
	 * this constructor initializes the values of a delivery 
	 * to the starting times.
	 * 
	 */
	
	public DeliveryService() {
		random = new Random();
		duration = 0;
		timer = -1;
	}
	
	/** getDuration()
	 * 
	 * this method returns the duration of a delivery trip.
	 * 
	 */
	
	public long getDuration(){
		return duration;
	}

	/** deliveryTime()
	 * 
	 * this method returns the time it should take for a farmer
	 * to complete a delivery.
	 * 
	 */
	
	public static long deliveryTime() {
		random = new Random();
		return (long)(20 * random.nextGaussian() + 60);
	}
	
	/** setDuration()
	 * 
	 * this method sets the duration of the trip by callng the
	 * deliveryTime() method.
	 * 
	 */
	
	public void setDuration() {
		duration = DeliveryService.deliveryTime();	
	}
	
	/** resetDuration()
	 * 
	 * this method resets the value of duration to 0.
	 * 
	 */
	
	public void resetDuration() {
		duration = 0;	
	}
	
	/** setTimer(long time)
	 * 
	 * this method sets the timer value with the time that is passed
	 * into the method.
	 * 
	 */
	
	public void setTimer(long time) {
		timer = time + getDuration();		
	}
	
	/** timerAlarm()
	 * 
	 * this method returns the timer value;
	 * 
	 */
	
	public long timerAlarm() {
		return timer;
	}
}
