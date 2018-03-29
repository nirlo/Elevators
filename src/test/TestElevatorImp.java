package test;

import static org.junit.Assert.*;

import org.junit.Test;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystemImp;

public class TestElevatorImp {

	ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
	ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);

	@Test
	public void constructorTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		assertEquals(e.getCapacity(), 0, 0.000001);
		assertEquals(e.getFloor(), 0, 0.000000001);
	}
	
	@Test
	public void moveToTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.moveTo(2);
		assertEquals(e.getFloor(), 2, 0.000001);
		assertEquals(e.getPowerConsumed(), 4, 0.00001);
	}
	
	@Test
	public void moveToTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.moveTo(5);
		e.moveTo(3);
		assertEquals(e.getFloor(), 3, 0.000001);
		assertEquals(e.getPowerConsumed(), 11, 0.00001);
	}
	
	@Test
	public void requestStopTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.requestStop(5);
		e.requestStop(3);
		assertEquals(e.getFloor(), 3, 0.000001);
		assertEquals(e.getPowerConsumed(), 11, 0.00001);
	}
	
	@Test
	public void getCapacityTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.addPersons(1);
		assertEquals(e.getCapacity(), 1, 0.00001);
	}
	
	@Test
	public void getFloorTest() {
		assertEquals(e.getFloor(), 0, 0.00001);
	}
	
	@Test
	public void getPowerTest1() {
		assertEquals(e.getPowerConsumed(), 0, 0.00001);
	}
	
	@Test
	public void getPowerTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.requestStop(5);
		e.requestStop(3);
		assertEquals(e.getPowerConsumed(), 11, 0.00001);
	}
	
	@Test
	public void getStateTest() {
		assertEquals(e.getState(), MovingState.Idle);
	}
	
	@Test
	public void isFullTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.addPersons(1);
		assertTrue(e.isFull());
	}

	@Test
	public void isFullTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		assertFalse(e.isFull());
	}
	
	@Test
	public void isEmptyTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		e.addPersons(1);
		assertFalse(e.isEmpty());
	}
	
	@Test
	public void isEmptyTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		assertTrue(e.isEmpty());
	}
	
	@Test
	public void equalsTest() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		assertTrue(e.equals(e));
	}
	
	@Test
	public void equalsTest2() {
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system, 0);
		ElevatorImp e1 = new ElevatorImp(1, (ElevatorPanel) system, 2);
		assertFalse(e.equals(e1));
	}
	
}
