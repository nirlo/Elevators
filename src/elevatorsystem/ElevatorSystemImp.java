package elevatorsystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
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

	private Map<Elevator, List<Integer>> stops;

	private MovingState callDirection;
	
	private int count = 0;

	private Runnable run = () -> {
		while(!shutdown) {
			for( Elevator e: stops.keySet()) {
				if( !e.isIdle() || stops.get(e).isEmpty()) {
					continue;
				}
				synchronized(REQUEST_LOCK) {
					System.out.println("I made it to moveTo");
					List<Integer> l = stops.get(e);
					System.out.print(l.toString());
					e.moveTo(l.remove(0));
				}
			}
			
		}
	};

	/**
	 * Constructor. Sets the MAX_FLOOR and MIN_FLOOR.
	 * 
	 * @param	 MIN_FLOOR				
	 * @param	 MAX_FLOOR
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
	 * When called by callDown, this method will set the MovingState of the
	 * elevator to SlowDown and then requests the stop. 
	 * 
	 * @param 	floor			Target floor
	 * @param 	state			State to be set to
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

		System.out.println("woooo");
		synchronized(REQUEST_LOCK) {
			List<Integer> l = stops.get(elevator);

			System.out.println(l.toString());
			IntStream.of(floors).forEach(f->l.add(f));
			
			
			
			if(elevator.getFloor() == 0)
				QuickSort.quickSortUp(l, 0, l.size());
			else
				QuickSort.quickSortDown(l, 0, l.size());
			
			System.out.println(l.toString());
		}
	}




}
