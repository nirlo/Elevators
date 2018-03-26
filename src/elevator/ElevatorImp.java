package elevator;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import elevatorsystem.ElevatorPanel;
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
 * Class: ElevatorImp
 * Description: Implements the Elevator interface. Provides methods for 
 * 				moving the elevator. Part of the Model.
 *
 */
public class ElevatorImp extends Observable implements Elevator {
	/**
	 * Sets the amount of power consumed when the elevator starts or when the
	 *  elevator stops.
	 */
	private static final int POWER_START_STOP = 2;

	/**
	 * Sets the amount of power consumed when the elevator between floors moving
	 * from destination to source.
	 */
	private static final int POWER_CONTINUOUS = 1;

	/**
	 * Sets the limit to consumed power for start and stop. If reached, the 
	 * elevator will enter MovingState Idle.
	 */
	//private static final long SLEEP_START_STOP = 50;

	/**
	 * Sets the limit to consumed power for continuous usage. If reached, the
	 * elevator will enter MovingState Idle.
	 */
	//private static final long SLEEP_CONINUOUS = 25;

	/**
	 * The Moving state of the elevator.
	 */
	private volatile MovingState state;

	/**
	 * The Panel interface for the elevator, user in simulation uses this
	 * to request stops.
	 */
	private final ElevatorPanel panel;

	/**
	 * The limit to how many persons can be in the elevator at once.
	 */
	private final double MAX_CAPACITY_PERSONS;

	/**
	 * Holds the power consumed during operation.
	 */
	private double powerUsed;

	/**
	 * Holds the current floor that the elevator is situated at.
	 */
	private int currentFloor;

	/**
	 * Holds the current capacity of the elevator. Cannot exceed 
	 * MAX_CAPACITY_PERSONS
	 */
	private int capacity;
	
	private final int ID;

	/**
	 * 
	 */
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	/**
	 * Constructor for Elevator implementation. Will set the current floor to 0
	 * and the initial MovingState to Idle.
	 * 
	 * @param 	CAPACITY_PERSONS		The capacity of the elevator.
	 * @param 	panel					The control for user inside the elevator
	 * 									to request stops.
	 */
	public ElevatorImp(double CAPACITY_PERSONS, ElevatorPanel panel, int id) {
		MAX_CAPACITY_PERSONS = CAPACITY_PERSONS;
		ID = id;
		this.panel = panel;
		currentFloor = 0;
		state = MovingState.Idle;
	}

	/**
	 * Moves the elevator to the requested floor. Calls on processMovingState
	 * to change state to the appropriate state, then consumes the appropriate
	 * amount of power. Iterates through the amount of moves needed to reach
	 * destination.
	 * 
	 * @param	int						The next floor for the elevator to move to
	 */
	@Override
	public void moveTo(int floor) throws UnsupportedOperationException {
		try {
			while(currentFloor != floor){
				switch(getState()) {
				case Idle:
					if(floor > currentFloor)
						setState(MovingState.SlowUp);
					else
						setState(MovingState.SlowDown);
				case SlowDown:
				case SlowUp:
				case Up:
				case Down: //All cases fall into this and computes moving and power
					if(state.isGoingDown())
						currentFloor--;
					else
						currentFloor++;
					
					if(state.isGoingSlow())
						powerUsed += POWER_START_STOP;
					else
						powerUsed += POWER_CONTINUOUS;
					

					processMovingState(floor);
					break;
				default:
					UnsupportedOperationException e = new UnsupportedOperationException();
					System.out.println(e);
				} 
				
				Thread.sleep(50);

				setChanged();
				notifyObservers(floor);

			}
		}catch(UnsupportedOperationException | InterruptedException e) {
			e.printStackTrace();
		}	

	}

	/**
	 * Ensures that the state is in the appropriate MovingState by checking
	 * the amount of moves needs from the floor passed in from moveTo method.
	 * Operations will always start and stop at Idle, becoming slow up/down, 
	 * and will be up/down between source and destination.
	 * 
	 * @param 	floor					Destination floor passed in from moveTo
	 */
	private void processMovingState(int floor) {
		if(currentFloor != floor) {
			if(Math.abs(currentFloor - floor) != 1) //checks to see whether the elevator should continue in a normal
				setState(state.normal());
			else
				setState(state.slow());
		} else
			setState(MovingState.Idle);
	}


	@Override
	public void addPersons(int persons) {
		System.out.println("Adding fat people");
		if(capacity+persons <= MAX_CAPACITY_PERSONS && persons > 0) {
			capacity += persons;
		}
	}


	@Override
	public void requestStop(int floor) {
		moveTo(floor);
	}


	@Override
	public int getCapacity() {
		return capacity;
	}


	@Override
	public int getFloor() {
		return currentFloor;
	}


	@Override
	public double getPowerConsumed() {
		return powerUsed;
	}

	@Override
	public MovingState getState() {
		return state;
	}

	@Override
	public boolean isFull() {
		if(capacity < MAX_CAPACITY_PERSONS)
			return false;
		else
			return true;
	}

	@Override
	public boolean isEmpty() {
		if(capacity == 0)
			return true;
		else
			return false;
	}

	/**
	 * Retrieves the ElevatorPanel.
	 * 
	 * @return	ElevatorPanel			panel
	 */
	public ElevatorPanel getPanel() {
		return panel;
	}

	public void setState(MovingState s) {
		state = s;
	}

	/**
	 * Observer to be added to observed list
	 * 
	 * @param observer
	 */
	public void addObserver(Observer observer) {observers.add(observer);}

	public void notifyObservers(int floor) {
		for(Observer o: observers) {
			int power = (int) getPowerConsumed();
			List<Integer> list = new ArrayList<Integer>();
			list.add(ID);
			list.add((Integer)getFloor());
			list.add((Integer)floor);
			list.add((Integer)power);
			o.update(list);
		}
	}

	@Override
	public boolean isIdle() {
		return(state == MovingState.Idle);
	}

	@Override
	public int id() {
		return ID;
	}

	@Override
	public void requestStops(int... floors) {
		System.out.println("What the fuck");
		panel.requestStops(this, floors);
	}

	
	
	public boolean equals(Object obj) {
		if(obj instanceof ElevatorImp) {
			ElevatorImp b = (ElevatorImp) obj; 
			return (this.ID == b.ID);
		}
		return false;	
	}

	@Override
	public void addObservers(Observer observer) {
		addObserver(observer);
	}

}
