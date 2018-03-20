package elevatorsystem;

import java.util.List;

public class QuickSort {
	
	public static void sort( List<Integer> array, final int size){
		if( array == null || array.size() < 2){
			return;
		}
		sortRecursive( array, 0, size - 1);
	}

	public static void sortRecursive( List<Integer> array, final int low, final int high){
		// base case all data has been sorted
		if( low > high){
			return;
		}

		int pivot = partition( array, low, high);

		//break array on pivot point
		sortRecursive( array, low, pivot - 1);
		sortRecursive( array, pivot + 1, high);
	}

	private static int partition( List<Integer> array, final int low, final int high){
		int pivot = array.get(high);// choose last element as pivot
		int i = (low - 1); // assume left most element is smallest
		for( int j = low; j < high; j++){// start at smallest index
			// If current element is smaller than or equal to pivot
			if( array.get(j) <= pivot){
				i++;// keep track of the last partitioned number smaller than pivot
				swap( array, i, j);// swap smaller than pivot number with index j
			}
		}
		// bring pivot to correct position
		swap( array, i + 1, high);
		return i + 1;
	}

	private static void swap( List<Integer> array, final int source, final int dest){
		int num = array.get(source);
		array.set(source, array.get(dest));
		array.set(dest, num);
	}
	
	public static void sortDesc( List<Integer> array, final int size){
		if( array == null || array.size() < 2){
			return;
		}
		sortRecursiveDesc( array, 0, size - 1);
	}

	public static void sortRecursiveDesc( List<Integer> array, final int low, final int high){
		// base case all data has been sorted
		if( high > low){
			return;
		}

		int pivot = partitionDesc( array, low, high);

		//break array on pivot point
		sortRecursive( array, low, pivot - 1);
		sortRecursive( array, pivot + 1, high);
	}

	private static int partitionDesc( List<Integer> array, final int low, final int high){
		int pivot = array.get(low);// choose last element as pivot
		int i = (high - 1); // assume left most element is smallest
		for( int j = high; j < low; j++){// start at smallest index
			// If current element is smaller than or equal to pivot
			if( array.get(j) <= pivot){
				i++;// keep track of the last partitioned number smaller than pivot
				swap( array, i, j);// swap smaller than pivot number with index j
			}
		}
		// bring pivot to correct position
		swap( array, i + 1, low);
		return i + 1;
	}
	
}
