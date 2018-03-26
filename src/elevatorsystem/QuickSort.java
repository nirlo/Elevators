package elevatorsystem;


public class QuickSort {

	/**
	 * 
	 * 
	 * @param a					The Array to be sorted
	 * @param left				The left most portion of the section to be sorted (begins with 0 and 
	 * 							changes through the recursion)
	 * @param right				The right most portion of the section to be sorted (begins as the final 
	 * 							element in the array
	 */
	public static int Partition(int[] a,int left, int right) {
		int X = a[left];
		int i = left+1;
		int j = right;
		int t;
		
		do {
			while ((i<=j)&&(a[i] <= X))
				i++;
			while ((i<=j)&&(a[j] > X))
			j--;
			
			//doing swap here
			if (i<j) {
				
				t = a[i];
				a[i]= a[j];
				a[j]=t; 
			}
			
		}while (i<=j);
		
		t = a[left];
		a[left] = a[j];
		a[j] =t;
		
		return j;
	}
	
	/**
	 * 
	 * @param a					The Array to be sorted
	 * @param left				The left most portion of the section to be sorted (begins with 0 and 
	 * 							changes through the recursion)
	 * @param right				The right most portion of the section to be sorted (begins as the final 
	 * 							element in the array
	 */
	public static int PartitionReverse(int a[],int left, int right) {
		int X = a[left];
		int i = left+1;
		int j = right;
		int t;
		
		do {
			while ((i<=j)&&(a[i] >= X))
				i++;
			while ((i<=j)&&(a[j] < X))
			j--;
			
			//doing swap here
			if (i<j) {
				
				t = a[i];
				a[i]= a[j];
				a[j]=t; 
			}
			
		}while (i<=j);
		
		t = a[left];
		a[left] = a[j];
		a[j] =t;
		
		return j;
	}
	

	
	/**
	 * Implements an O(n log n) sort
	 * 
	 * @param a					The Array to be sorted
	 * @param left				The left most portion of the section to be sorted (begins with 0 and 
	 * 							changes through the recursion)
	 * @param right				The right most portion of the section to be sorted (begins as the final 
	 * 							element in the array
	 */
	public static void quickSort(int[]a,int left,int right) {
		
		int k ;
		if (left < right) {
			
			k = Partition(a, left, right);
			quickSort(a,left,k-1);
			quickSort(a,k+1,right);
		}
	}
	
	/**
	 * Implements an O(n log n) sort
	 * 
	 * @param a					The Array to be sorted
	 * @param left				The left most portion of the section to be sorted (begins with 0 and 
	 * 							changes through the recursion)
	 * @param right				The right most portion of the section to be sorted (begins as the final 
	 * 							element in the array
	 */
	public static void quickSortReverse(int[] a,int left,int right) {
		
		int k ;
		if (left < right) {
			
			k = PartitionReverse(a, left, right);
			quickSortReverse(a,left,k-1);
			quickSortReverse(a,k+1,right);
		}
	}
}
