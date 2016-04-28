/** Barn.java
 * 
 * This class creates a Barn object that is used to store ripe
 * pumpkins for delivery for customer orders.
 * 
 */
import java.util.Stack;


public class Barn {
	public enum BarnState { EMPTY, FULL, HASITEMS }
	private BarnState state;
	private Stack<Pumpkin> pumpkins;

	/** Barn()
	 * 
	 * This constructor initializes the barn object with the EMPTY
	 * BarnState, signaling that there currently is nothing in the barn.
	 * There is also a Stack of pumpkins initialized; a stack is used because 
	 * there is not an extravagant use of the container holding the pumpkins, 
	 * other then adding and removing to the stash with no real need to know
	 * which ones we are removing etc.
	 */
	
	public Barn() {
		state = BarnState.EMPTY;
		pumpkins = new Stack<Pumpkin>();
	}
	
	/** countPumpkins()
	 * 
	 * this method returns the size of the stack; or
	 * rather how many pumpkins are currently in the barn.
	 * 
	 */
	
	public int countPumpkins() {
		return pumpkins.size();
	}
	
	/** hasPumpkins()
	 * 
	 * this is a boolean method that returns true if there is 
	 * a pumpkin in the barn.
	 * 
	 */
	
	public boolean hasPumpkins() {
		if(countPumpkins() >= 1) {
			return true;
		}
		return false;
	}
	
	/** removePumpkin()
	 * 
	 * this method removes a pumpkin from the barn.
	 * 
	 */
	
	public void removePumpkin() {
		pumpkins.pop();
	}
	
	/** addPumpkins(int number)
	 * 
	 * this method adds pumpkins to the barn. the amount of
	 * pumpkins added is indicated by the value of number
	 * that is passed into this method.
	 * 
	 */
	
	public void addPumpkins(int number) {
		for(int i = 0; i < number; i++) {
			pumpkins.push(new Pumpkin());
		}
	}
	
	/** isFull()
	 * 
	 * this is a boolean method that checks if the barn is getting full.
	 * if the barn has 9000 or more pumpkins, then the barn is set to the
	 * full state and returns true.
	 * 
	 */
	
	public boolean isFull() {
		if(countPumpkins() >= 9000) {
			state = BarnState.FULL;
			return true;
		}
		return false;
	}
	
	/** isEmpty()
	 * 
	 * this is a boolean method that checks if the barn is getting empty.
	 * if the barn has  1000 or less pumpkins, then the barn is set to the 
	 * empty state and returns true.
	 * 
	 */
	
	public boolean isEmpty() {
		if(countPumpkins() <= 1000) {
			state = BarnState.EMPTY;
			return true;
		}
		return false;
	}
}