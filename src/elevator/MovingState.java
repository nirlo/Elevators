package elevator;

/**
 * CST8288 Object Oriented Programming Design Patterns
 * Assignment 1
 * Due: February 16th 2018
 * 
 * @author lock0134@algonquinlive.com
 * Nicholas Lockhart
 * 040905348
 * 
 * Enum: MovingState
 * Description: Provides methods for dealing with the MovingState
 * 				of an Elevator as a State Machine. Part of the model.
 *
 */
public enum MovingState {
	/**
	 * The various states that are possible.
	 * 
	 */
	Idle, Up, Down, SlowUp, SlowDown, Off;

	/**
	 * Will turn the state of the elevator into a slow state. If the Elevator
	 * is Idle, will change to the state to slowup, changed using opposite.
	 * 
	 * @return	State				Returns the appropriate state given the
	 * 								the current state of elevator.
	 */
	public MovingState slow() {
		switch(this) {
		case Up:
			return SlowUp;
		case Down:
			return SlowDown;
		case SlowUp:
		case SlowDown:
			return this;
		default:
			throw new IllegalStateException( "ERROR - " + this.name() + " has no slow chill");
		}
	}

	/**
	 * Will turn the elevator into a normal state of up or down.
	 * 
	 * @return	MovingState			Returns the appropriate state of up or down.
	 */
	public MovingState normal() {
		switch(this) {
		case SlowUp:
			return Up;
		case SlowDown:
			return Down;
		case Up:
		case Down: 
			return this;
		default:
			throw new IllegalStateException( "ERROR - " + this.name() + "has no normal chill");
		}
	}

	/**
	 * Returns the opposite state of the current state
	 * 
	 * @return	MovingState				Opposite of the current state.
	 */
	public MovingState opposite() {
		switch(this) {
		case SlowUp:
			return SlowDown;
		case SlowDown:
			return SlowUp;
		case Up:
			return Down;
		case Down:
			return Up;
		default:
			throw new IllegalStateException( "ERROR - " + this.name() + " has no opposite chill");
		}
	}

	/**
	 * Checks to see if the elevator is going down.
	 * 
	 * @return	boolean				If the elevator is going down
	 */
	public boolean isGoingDown() {
		return (this == Down || this == SlowDown);
	}

	/**
	 * Checks to see if the elevator is going up or down.
	 * 
	 * @return	boolean				If the elevator is going up.
	 */
	public boolean isGoingUp() {
		return (this == Up || this == SlowUp);
	}

	/**
	 * Checks to see if the elevator is going slow.
	 * 
	 * @return	boolean				If the elevator is going slow.
	 */
	public boolean isGoingSlow() {
		return (this == SlowDown || this == SlowUp || this == Idle);
	}
	
	/**
	 * Checking to see if the current MovingState is Idle
	 * 
	 * @return	boolean				
	 */
	public boolean isIdle() {
		return this==Idle;
	}
	
	/**
	 * Checking to see if the current MovingState is Off
	 * 
	 * @return	boolean				
	 */
	public boolean isOff() {
		return this==Off;
	}


}
