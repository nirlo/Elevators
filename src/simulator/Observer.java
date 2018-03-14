package simulator;

import java.util.List;

/**
 * CST8288 Object Oriented Programming Design Patterns
 * Assignment 1
 * Due: February 16th 2018
 * 
 * @author lock0134@algonquinlive.com
 * Nicholas Lockhart
 * 040905348
 * 
 * Interface: Observer
 * Description: provides the specific method for Observers
 *
 */
public interface Observer {
	
	/**
	 * Called by the Observed object, passing updated data from that object into
	 * the observer.
	 * 
	 * @param	List	The information about how the elevator moves through the system
	 */
	public void update(List<Integer> list);
}
