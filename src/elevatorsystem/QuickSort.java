package elevatorsystem;

import java.util.List;

public class QuickSort {
	
	public static void sort( List<Integer> l, final int size){
		if( l == null || l.size() < 2){
			return;
		}
		sortRecursive( l, 0, size - 1);
	}

	public static void sortRecursive( List<Integer> l, final int low, final int high){
		// base case all data has been sorted
		if( low > high){
			return;
		}

		int pivot = partition( l, low, high);

		//break array on pivot point
		sortRecursive( l, low, pivot - 1);
		sortRecursive( l, pivot + 1, high);
	}

	private static int partition( List<Integer> l, final int low, final int high){
		int pivot = l.get(high);// choose last element as pivot
		int i = (low - 1); // assume left most element is smallest
		for( int j = low; j < high; j++){// start at smallest index
			// If current element is smaller than or equal to pivot
			if( l.get(j) <= pivot){
				i++;// keep track of the last partitioned number smaller than pivot
				swap( l, i, j);// swap smaller than pivot number with index j
			}
		}
		// bring pivot to correct position
		swap( l, i + 1, high);
		return i + 1;
	}

	private static void swap( List<Integer> l, final int source, final int dest){
		int num = l.get(source);
		l.set(source, l.get(dest));
		l.set(dest, num);
	}
	
	public static void sortDesc( List<Integer> l, final int size){
		if( l == null || l.size() < 2){
			return;
		}
		sortRecursiveDesc( l, 0, size - 1);
	}

	public static void sortRecursiveDesc( List<Integer> l, final int low, final int high){
		// base case all data has been sorted
		if( high > low){
			return;
		}

		int pivot = partitionDesc( l, low, high);

		//break array on pivot point
		sortRecursiveDesc( l, low, pivot - 1);
		sortRecursiveDesc( l, pivot + 1, high);
	}

	private static int partitionDesc( List<Integer> l, final int low, final int high){
		int pivot = l.get(low);// choose last element as pivot
		int i = (high - 1); // assume left most element is smallest
		for( int j = high; j < low; j++){// start at smallest index
			// If current element is smaller than or equal to pivot
			if( l.get(j) <= pivot){
				i++;// keep track of the last partitioned number smaller than pivot
				swap( l, i, j);// swap smaller than pivot number with index j
			}
		}
		// bring pivot to correct position
		swap( l, i + 1, high);
		return i + 1;
	}
}
