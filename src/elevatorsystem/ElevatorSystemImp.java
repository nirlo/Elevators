package elevatorsystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

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
public class ElevatorSystemImp extends Thread implements ElevatorSystem, ElevatorPanel{

	private final Object REQUEST_LOCK = new Object();

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

	private boolean shutdown = false;
	
	

	private ExecutorService es;

	/**
	 * Holds the elevators that are a part of the system. Each elevator
	 * has a List of stops that it will travel through as part of the normal operation.
	 */
	private Map<Elevator, List<Integer>> stops;

	
	private int count = 0;

	/**
	 * I need to describe what is happening in this runnable. 
	 * Run will run the entire time that the system is in use.
	 * It will go through the Map and check if there are values within List.
	 * At this point, the list should be ordered. System will remove the first
	 * number in the list and move the elevator associated with the list to
	 * that floor. 
	 */
	private Runnable run = () -> {
		while(!shutdown) {
			for( Elevator e: stops.keySet()) {
				int move = 0;
				if( !e.isIdle() || stops.get(e).isEmpty()) {
					continue;
				}
				synchronized(REQUEST_LOCK) {
					List<Integer> l = stops.get(e);
					System.out.print(l.toString());
					move = l.remove(0);
				}
				e.moveTo(move);
			}
			
		}
	};

	/**
	 * Constructor. Sets the MAX_FLOOR and MIN_FLOOR.
	 * 
	 * @param	 MIN_FLOOR				The lowest floor associated with this system		
	 * @param	 MAX_FLOOR				The highest floor associated with this system
	 */
	public ElevatorSystemImp(int MIN_FLOOR, int MAX_FLOOR) {
		this.MAX_FLOOR = MAX_FLOOR;
		this.MIN_FLOOR = MIN_FLOOR;
		es = Executors.newCachedThreadPool();
		stops = new HashMap<>();
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
		return call(floor, MovingState.Up);
		
	}

	@Override
	public Elevator callDown(int floor) {
		return call(floor, MovingState.Down);
	}
	/**
	 * Chooses an elevator to go to the designated floor. 
	 * Honestly, I was having a bit too much trouble trying to get
	 * it to choose a best elevator, so I set it to get the elevators
	 * in order
	 * 
	 * @param 	floor			The target floor that the user is currently at
	 * @param 	state			Deprecated
	 */
	private Elevator call(int floor, MovingState state) {
		Elevator ele = null;
		for(Elevator e: stops.keySet()) {
			if(e.id() == count)
				ele = e;
		}
		count++;
		synchronized(REQUEST_LOCK) {
			ele.moveTo(floor);
		}
		return ele;
	}

	@Override
	public void addElevator(Elevator elevator) {
		stops.put(elevator, new LinkedList<Integer>());
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
		return stops.size();
	}

	@Override
	public void addObserver(Observer observer) {
		stops.keySet()
		.stream()
		.forEach(e -> e.addObservers(observer));
	}

	@Override
	public void shutdown() {
		shutdown = true;
		es.shutdown();
	}


	@Override
	public void start() {
		es.submit(run);
	}

	@Override
	public void requestStops(Elevator elevator, int... floors) {

		synchronized(REQUEST_LOCK) {
			//gets the list associated with the elevator
			List<Integer> l = stops.get(elevator);
			//Checks to see what way to order the stops and orders the array of 
			//integers before placing them into the list
			if(elevator.getFloor() == 0)
				QuickSort.quickSort(floors, 0, floors.length-1);
			else
				QuickSort.quickSortReverse(floors, 0, floors.length-1);
			//places the ordered array into the list.
			IntStream.of(floors).forEach(f->l.add(f));
		}
	}




}
