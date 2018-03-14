package elevatorsystem;

import elevator.Elevator;

/**
 * CST8288 Object Oriented Programming Design Patterns
 * Assignment 1
 * Due: February 16th 2018
 * 
 * @author lock0134@algonquinlive.com
 * Nicholas Lockhart
 * 040905348
 * 
 * Interface: ElevatorPanel
 * Description: The panel inside the Elevator as part of the control
 * 				of the system.
 *
 */
public interface ElevatorPanel {

	/**
	 * Requests a floor for the passed in elevator.
	 * 
	 * @param floor
	 * @param elevator
	 */
	public void requestStop(int floor, Elevator elevator);
	
	public void requestStops(final Elevator elevator, final int...floors);
}
