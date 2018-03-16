package elevatorsystem;

public class QuickSort {
	
	public static void sort( int[] array, final int size){
		if( array == null || array.length < 2){
			return;
		}
		sortRecursive( array, 0, size - 1);
	}

	public static void sortRecursive( int[] array, final int low, final int high){
		// base case all data has been sorted
		if( low > high){
			return;
		}

		int pivot = partition( array, low, high);

		//break array on pivot point
		sortRecursive( array, low, pivot - 1);
		sortRecursive( array, pivot + 1, high);
	}

	private static int partition( int[] array, final int low, final int high){
		int pivot = array[high];// choose last element as pivot
		int i = (low - 1); // assume left most element is smallest
		for( int j = low; j < high; j++){// start at smallest index
			// If current element is smaller than or equal to pivot
			if( array[j] <= pivot){
				i++;// keep track of the last partitioned number smaller than pivot
				swap( array, i, j);// swap smaller than pivot number with index j
			}
		}
		// bring pivot to correct position
		swap( array, i + 1, high);
		return i + 1;
	}

	private static void swap( int[] array, final int source, final int dest){
		int num = array[source];
		array[source] = array[dest];
		array[dest] = num;
	}
}
