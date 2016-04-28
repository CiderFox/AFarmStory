/** Journal.java
 * 
 * this class is used to record the happenings on the farm, and outputs these happenings
 * to a file within the directory. 
 * 
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

public class Journal {
	private BufferedWriter writer;
	private File file;
	
	/** Journal()
	 * 
	 * this constructor creates a new file by calling the method
	 * setFile() within this class.
	 * 
	 */
	
	public Journal() {
		try {
			setFile(new File("notebook.txt"));
		} catch (IOException e) {
			System.out.println("Error creating file.");
		}
	}
	
	/** set(String string, int length)
	 * 
	 * this method takes in a sting and sets the format according
	 * to the length that was passed in.
	 * 
	 */
	
	public static String set(String string, int length) {
	    return String.format("%1$"+length+ "s", string);
	}
	
	/** note(long time, String event, int stash, int plants, int pumpkins,  int orders)
	 * 
	 * This method takes in the items that need to be recorded as per assignment indication
	 * and uses the set method to format the information in the file.
	 * 
	 */

	public void note(long time, String event, int stash, int plants, int pumpkins,  int orders) {		
		note(set("TIME: ", 6)
				+ set(Long.toString(time), 7) 
				+ set(event, 19) 
				+ set("BARN: ", 13) 
				+ set(Integer.toString(stash), 4) 
				+ set("PLANTS: ", 15) 
				+ set(Integer.toString(plants), 4) 
				+ set("RIPENED: ", 20) 
				+ set(Integer.toString(pumpkins), 4) 
				+ set("REQUESTS: ", 20)
				+ set(Integer.toString(orders), 4));
	}
	
	/** openJournal()
	 * 
	 * this method starts the story of the farm.
	 * this is just my element of fun added to the program.
	 * 
	 */
	
	public void openJournal() {
		note("Once Upon A Time...");
		note("");
		note("There was a Farmer named Jack.");
		note("");
		note("He had one farm, A pumpkin patch to grow Pumpkins");
		note("");
		note("He had one barn used as a stash, to store his ripe pumpkins");
		note("");
		note("He had one website, that he used to recieve customer orders");
		note("");
		note("He had one deliver service, UPuS, he used to delivery a customer's order");
		note("");
		note("This is his Journal that he used to record his story...");
		note("");
		note("---------------------------------------------------------------------------------------------------------------------");
		note("Journal Entry 	    Event	       Stash	        Plants	               Pumpkins	              Pending Orders ");
		note("---------------------------------------------------------------------------------------------------------------------");
	}
	
	/** closeJournal( int pumpkins, int orders, double mean, double standard )
	 * 
	 * this method finishes the story of the farm.
	 * this method reports the final information about the farm:
	 * (1) how many pumpkins were still in the stash.
	 * (2) the number of orders that were completed.
	 * (3) the mean time for orders.
	 * (4) the standard diviation for orders.
	 * 
	 */
	
	public void closeJournal( int pumpkins, int orders, double mean, double standard ) {
		DecimalFormat df = new DecimalFormat("#.##");
		note("");
		note("---------------------------------------------------------------------------------------------------------------------");
		note("Journal Entry 	    Event	       Stash	        Plants	               Pumpkins	              Pending Orders ");
		note("---------------------------------------------------------------------------------------------------------------------");
		note("");
		note("There are no more journal entries...");
		note("");
		note("Jack had pumpkins left in his barn: " + pumpkins + "");
		note("");
		note("Jack completed a total of orders: " + orders + "");
		note("");
		note("Jack's mean time to fill an order was: " + Double.valueOf(df.format(mean)) + " time units.");
		note("");
		note("Jack's standard deviation of orders fill time was: " + Double.valueOf(df.format(standard)) + " time units.");
		note("");
		note("The End.");
	}
	
	/** note(String string)
	 * 
	 * this method has the buffered writer write each string to the file.
	 * 
	 */
	
	public void note(String string) {
		if (writer != null) {
			try {
				writer.write(string);
				writer.write(System.getProperty("line.separator"));
				writer.flush();
			} catch (IOException e) {}
		}
	}

	/** setFile(File outFile)
	 * 
	 * this method creates the new file for the farm
	 * information to be written to	
	 * 
	 */
	
	public void setFile(File outFile) throws IOException {
		if (!outFile.exists()) {
			outFile.createNewFile();
		}
		file = outFile;
		FileWriter fw = new FileWriter(outFile.getAbsoluteFile());
		writer = new BufferedWriter(fw);
	}
}