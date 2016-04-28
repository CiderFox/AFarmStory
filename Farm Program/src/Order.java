/** Order.java
 * 
 * this class creates order objects that are used for the website class.
 * the order has an OrderState to tell what state the order is in; weather it
 * is pending or completed. The order also holds two time values:
 * (1) when the order arrived.
 * (2) when the order was completed.
 *
 */

public class Order {
	public enum OrderState { COMPLETED, PENDING }
	private OrderState state;
	private long receivedTime;
	private long completedTime;
	
	/** Order(long time)
	 * 
	 * and order is created with the recieval time as the time that
	 * is passed into the method.
	 * the order is initialized with the pending state, and the completed time 
	 * of the order is set to zero because it is in the pending state.
	 *
	 */
	
	public Order(long time) {
		state = OrderState.PENDING;
		this.receivedTime = time;
		completedTime = 0;
	}
	
	/** setCompletedTime(long time)
	 * 
	 * this method sets the time that an order was completed with
	 * the time that was passed into this method.
	 *
	 */
	
	public void setCompletedTime(long time) {
		completedTime = time;
	}
	
	/** getCompletedTime()
	 * 
	 * this method returns the completed time of a given
	 * order.
	 * 
	 */
	
	public long getCompletedTime() {
		return completedTime;
	}
	
	/** getRecievedTime()
	 * 
	 * this method returns the time value of when a given
	 * order had been received.
	 * 
	 */
	
	public long getReceivedTime() {
		return receivedTime;
	}
	
	/** currentState()
	 * 
	 * this method returns the current state of an order.
	 * this method will return either PENDING or COMPLETED.
	 * 
	 */
	
	public OrderState currentState() {
		return state;
	}
	
	/** completeOrder()
	 * 
	 * this method completes an order by setting
	 * its current PENDING OrderState to COMPLETED.
	 */
	
	public void completeOrder() {
		state = OrderState.COMPLETED;
	}
}
