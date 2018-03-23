package elevatorsystem;

import java.util.List;

public class QuickSort {

	public static void quickSortDown(List<Integer> A, int p, int r) {
		if (p < r) {
			int q = partitionDown(A, p, r);
			quickSortDown(A, p, q);
			quickSortDown(A, q + 1, r);
		}
	}

	private static int partitionDown(List<Integer> A, int p, int r) {
		int x = A.get(p); // pivot
		int i = p;
		int j = r;
		while(true) {
			//ignore all the numbers greater than X to left
			while (A.get(i) > x) {
				i++;
			}
			//ignore all numbers lesser than X to right
			while (A.get(j) < x) {
				j--;
			}

			//swap a number lesser than X on left with a number greater than X on right
			if (i < j) {
				int temp = A.get(i);
				A.set(i, j);
				A.set(j, temp);
				i++;
				j--;
			} else {
				//Now the array is so sorted, that all numbers lesser than X are on right of it and greater than X are to left of it. Hence return position of X
				return j;
			}
		}
	}

	public static void quickSortUp(List<Integer> A, int p, int r) {
		if (p < r) {
			int q = partitionUp(A, p, r);
			quickSortUp(A, p, q);
			quickSortUp(A, q + 1, r);
		}
	}

	private static int partitionUp(List<Integer> A, int p, int r) {
		int x = A.get(p); // pivot
		int i = p;
		int j = r;
		while(true) {

			while (A.get(i) < x) {
				i++;
			}

			while (A.get(j) > x) {
				j--;
			}

			if (i < j) {
				int temp = A.get(i);
				A.set(i, j);
				A.set(j, temp);
				i++;
				j--;
			} else {
				return j;
			}
		}
	}
}
