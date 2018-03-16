package elevatorsystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
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

	private Runnable run = () -> {
		
		AtomicInteger counters[] = new AtomicInteger[stops.size()];
		for( int i = 0; i < counters.length; i++) {
			counters[i] = new AtomicInteger();
		}	
		while(!shutdown) {
			for( Elevator e: stops.keySet()) {
				if( !e.isIdle() || counters[e.id()].get() != 0) {
					continue;
				}
				synchronized(REQUEST_LOCK) {

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
		Callable<Elevator> callElevator = () ->{
			Elevator chosen = null;
			int spaceBtwn = MAX_FLOOR;
			for(Elevator e: stops.keySet()) {
				if(e.isIdle()) {
					if(chosen != null && (Math.abs(e.getFloor() - floor)) < spaceBtwn) {
						chosen = e;
						spaceBtwn = (Math.abs(e.getFloor() - floor));
					}
					else continue;
					chosen = e;
				}
			}
			return chosen;
		};
		try {
			es.submit(callElevator).get().moveTo(floor);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		callDirection = state;
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

	}

	@Override
	public void requestStops(Elevator elevator, int... floors) {

		List<Integer> l = stops.get(elevator);
		IntStream.of(floors).forEach(f->l.add(f));
		es.submit(run);
	}




}
