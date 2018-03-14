package test;

import static org.junit.Assert.*;

import org.junit.Test;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystemImp;

public class TestElevatorImp {

	ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
	ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);

	@Test
	public void constructorTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertEquals(e.getCapacity(), 0, 0.000001);
		assertEquals(e.getFloor(), 0, 0.000000001);
	}
	
	@Test
	public void moveToTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		e.moveTo(2);
		assertEquals(e.getFloor(), 2, 0.000001);
		assertEquals(e.getPowerConsumed(), 4, 0.00001);
	}
	
	@Test
	public void moveToTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		e.moveTo(5);
		e.moveTo(3);
		assertEquals(e.getFloor(), 3, 0.000001);
		assertEquals(e.getPowerConsumed(), 11, 0.00001);
	}
	
	@Test
	public void addPersonsTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertFalse(e.addPersons(3));
	}
	
	@Test
	public void addPersonsTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertTrue(e.addPersons(1));
	}
	
	@Test
	public void addPersonsTest3() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertFalse(e.addPersons(0));
	}
	
	@Test
	public void requestStopTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		e.requestStop(5);
		e.requestStop(3);
		assertEquals(e.getFloor(), 3, 0.000001);
		assertEquals(e.getPowerConsumed(), 11, 0.00001);
	}
	
	@Test
	public void getCapacityTest() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
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
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
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
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		e.addPersons(1);
		assertTrue(e.isFull());
	}

	@Test
	public void isFullTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertFalse(e.isFull());
	}
	
	@Test
	public void isEmptyTest1() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		e.addPersons(1);
		assertFalse(e.isEmpty());
	}
	
	@Test
	public void isEmptyTest2() {
		ElevatorSystemImp system = new ElevatorSystemImp(0, 20);
		ElevatorImp e = new ElevatorImp(1, (ElevatorPanel) system);
		assertTrue(e.isEmpty());
	}
	
	
}
