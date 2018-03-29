package test;

import static org.junit.Assert.*;

import org.junit.Test;
import elevatorsystem.QuickSort;

public class QuickSortTest {

	@Test
	public void testQuickSort() {
		int[] a = {5, 10, 3, 50, 20, 2};
		QuickSort.quickSort(a, 0, a.length-1);
		for(int i: a) {
			System.out.println(i);
		}
		int[] b = {2, 3, 5, 10, 20, 50};
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i])
				fail();
		}
		assertTrue(true);
	}
	
	@Test
	public void testQuickSortReverse() {
		int[] a = {5, 10, 3, 50, 20, 2};
		QuickSort.quickSortReverse(a, 0, a.length-1);
		int[] b = {50, 20, 10, 5, 3, 2};
		for(int i = 0; i < a.length; i++) {
			if(a[i] != b[i])
				fail();
		}
		assertTrue(true);
	}

}
