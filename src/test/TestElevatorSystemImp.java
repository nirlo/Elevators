package test;

import static org.junit.Assert.*;

import org.junit.Test;

import elevator.ElevatorImp;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystemImp;

public class TestElevatorSystemImp {

	ElevatorSystemImp system = new ElevatorSystemImp(0, 20);

	@Test
	public void ConstructorTest() {
		assertEquals(system.getMinFloor(), 0, 0.00001);
		assertEquals(system.getMaxFloor(), 20, 0.00001);
	}
	
	@Test
	public void addElevatorTest() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		system.addElevator(e);
		assertSame(e, system.callDown(2));
	}
	
	@Test
	public void requestStopTest() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		system.addElevator(e);
		system.requestStop(2, e);
		assertEquals(system.getCurrentFloor(), 2, 0.0001);
	}
	
	@Test
	public void callUpTest() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		system.addElevator(e);
		system.callUp(3);
		assertEquals(system.getCurrentFloor(), 3, 0.00001);
	}
	
	@Test
	public void callDownTest() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		system.addElevator(e);
		system.callDown(3);
		assertEquals(system.getCurrentFloor(), 3, 0.00001);
	}
	
	@Test
	public void getFloorCountTest() {
		assertEquals(system.getFloorCount(), 21, 0.000001);
	}
	
	@Test
	public void getMaxFloorTest() {
		assertEquals(system.getMaxFloor(), 20, 0.00001);
	}
	
	@Test
	public void getMinFloorTest() {
		assertEquals(system.getMinFloor(), 0, 0.00001);
	}
	
	@Test
	public void getPowerConsumed() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		system.addElevator(e);
		system.requestStop(3, e);
		assertEquals(system.getPowerConsumed(), 5, 0.0001);
	}
}
