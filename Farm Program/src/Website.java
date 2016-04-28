/** Website.java
 * 
 * This class creates a Website object that holds order objects
 * and keeps records of when the orders were completed.
 * 
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Website {
	private Queue<Order> pendingOrders;			
	private List<Order> completedOrders;			
	private double mean;				
	private double standardDeviation;	
	private static Random random;
	private long nextOrderTime;

	/** Website()
	 * 
	 * this constructor creates a new website with variables:
	 * (1) pendingOrders -- a Queue that holds the orders in order that they came in
	 * (2) completedOrders -- a List that holds all the orders that have been succesfully completed by the farmer.
	 * (3) mean -- this is the mean value that it took for a farmer to complete an order.
	 * (4) standardDeviation -- this is the standard deviation that it took for a farmer to complete an order.
	 * (5) nextOrderTime -- is used to create the next time an order should come to the website.
	 * 
	 */

	public Website() {
		random = new Random();	
		pendingOrders = new LinkedList<Order>();
		completedOrders = new ArrayList<Order>();
		mean = 0;
		standardDeviation = 0;
		nextOrderTime = 0;
	}

	/** setNextOrderTime(long time)
	 * 
	 * this method sets the nextOrderTime value by adding the time that was passed in
	 * to the method with the time received from calling the nextOrder() method.
	 * 
	 */

	public void setNextOrderTime(long time) {	

			nextOrderTime = time + nextOrder();
		
	}

	/** nextOrder()
	 * 
	 * this method returns the next time to expect an order to arrive.
	 * 
	 */


	public static long nextOrder() {
		return (long)(-120 * Math.log(random.nextDouble()));
	}

	/** nextOrderTime()
	 * 
	 * returns the value currently held by the nextOrderTime variable.
	 * 
	 */

	public long nextOrderTime() {	
		return nextOrderTime;
	}

	/** countPendingOrders()
	 * 
	 * returns the size value of the pendingOrders Queue.
	 * 
	 */

	public int countPendingOrders() {  		
		return pendingOrders.size();
	}

	/** hasOrders()
	 * 
	 * boolean method that returns true if the size of pendingOrders
	 * is more than 0;
	 * 
	 */

	public boolean hasOrders() {
		if(countPendingOrders() > 0) {
			return true;
		}
		return false;
	}

	/** receiveOrder(long time)
	 * 
	 * this method creates a new Order object and instantiating 
	 * the time that it arrived with the time value that was
	 * passed into this method.
	 * 
	 */

	public void receiveOrder(long time) {		
		pendingOrders.add(new Order(time));		
	}

	/** getCompletedOrders()
	 * 
	 * this returns the size value of the list of completedOrders.
	 * 
	 */

	public int getCompletedOrders() {
		return completedOrders.size();
	}

	/** getMean()
	 * 
	 * this method returns the value currently held by mean.
	 * 
	 */

	public double getMean() {
		return mean;
	}

	/** getStandardDeviation()
	 * 
	 * this method returns the value currently held by standardDeviation.
	 * 
	 */

	public double getStandardDeviation() {
		return standardDeviation;
	}

	/** completeOrder(long time)
	 * 
	 * this method completes and order from the pending queue of orders.
	 * it removes the order from the queue, sets its completed time, and 
	 * adds the now completed order to the list of completed orders.
	 * 
	 */

	public int completeOrder(long time, int pumpkins) {
		int count = 0;
		if (pumpkins > pendingOrders.size()) {
			pumpkins = pendingOrders.size();
		}
		for(int i = 0; i < pumpkins; i++){
			Order order = pendingOrders.poll();
			long completedTime = time - order.getReceivedTime();
			order.setCompletedTime(completedTime);
			order.completeOrder();
			completedOrders.add(order);
			count++;
		}
		return count;
	}

	/** updateRecords(long time)
	 * 
	 * this method updates the values of the mean and standard deviation.
	 * 
	 */

	public void updateRecords() {	
		mean = 0;
		for(Order time : completedOrders) {
			mean += (double)time.getCompletedTime();
		}	
		mean = mean / getCompletedOrders();	
		standardDeviation = 0;	
		for (Order time : completedOrders) {
			standardDeviation += Math.pow( (((double)time.getCompletedTime()) - mean), 2);
		}	
		standardDeviation = Math.sqrt( standardDeviation / getCompletedOrders() );
	}
}
