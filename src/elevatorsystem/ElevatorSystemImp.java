package elevatorsystem;

import elevator.Elevator;
import elevator.MovingState;
import simulator.Observer;

/**
 * CST8288 Object Oriented Programming Design Patterns
 * Assignment 1
 * Due: February 16th 2018
 * 
 * @author lock0134@algonquinlive.com
 * Nicholas Lockhart
 * 040905348
 * 
 * Class: ElevatorSystemImp
 * Description: The System in which the elevator will move in.
 * 				Provides the range of floors that an elevator can
 * 				move between. Part of the Control.
 *
 */
public class ElevatorSystemImp implements ElevatorSystem, ElevatorPanel {

	/**
	 * The top floor of the elevator system.
	 */
	private final int MAX_FLOOR;

	/**
	 * The bottom floor of the elevator system.
	 */
	private final int MIN_FLOOR;

	/**
	 * Elevator contained in the system.
	 */
	private Elevator elevator;

	/**
	 * Constructor. Sets the MAX_FLOOR and MIN_FLOOR.
	 * 
	 * @param	 MIN_FLOOR				
	 * @param	 MAX_FLOOR
	 */
	public ElevatorSystemImp(int MIN_FLOOR, int MAX_FLOOR) {
		this.MAX_FLOOR = MAX_FLOOR;
		this.MIN_FLOOR = MIN_FLOOR;
	}

	/**
	 * No-arg constructor. Sets a default for the System.
	 */
	public ElevatorSystemImp() {
		this(0, 4);
	}

	@Override
	public void requestStop(int floor, Elevator elevator) {
		elevator.requestStop(floor);

	}

	@Override
	public Elevator callUp(int floor) {
		call(floor, MovingState.SlowUp);
		return elevator;
	}

	@Override
	public Elevator callDown(int floor) {
		call(floor, MovingState.SlowDown);
		return elevator;
	}
	/**
	 * When called by callDown, this method will set the MovingState of the
	 * elevator to SlowDown and then requests the stop. 
	 * 
	 * @param 	floor			Target floor
	 * @param 	state			State to be set to
	 */
	private void call(int floor, MovingState state) {
		elevator.setState(state);
		elevator.requestStop(floor);
	}
	/**
	 * Checks to see if an elevator exists in the system.
	 * 
	 * @return	boolean			Whether an elevator exists in the system
	 */
	private boolean checkForElevator() {
		return !(elevator == null);
	}

	@Override
	public void addElevator(Elevator elevator) {
		if(!checkForElevator())
			this.elevator = elevator;	
	}

	@Override
	public int getCurrentFloor() {
		return elevator.getFloor();
	}

	@Override
	public int getFloorCount() {
		return MAX_FLOOR + 1;
	}

	@Override
	public int getMaxFloor() {
		return MAX_FLOOR;
	}

	@Override
	public int getMinFloor() {
		return MIN_FLOOR;
	}

	@Override
	public double getPowerConsumed() {
		return elevator.getPowerConsumed();
	}

	@Override
	public int getElevatorCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void requestStops(Elevator elevator, int... floors) {
		// TODO Auto-generated method stub
		
	}


}
