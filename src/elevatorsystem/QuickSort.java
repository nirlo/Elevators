package elevatorsystem;


public class QuickSort {

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
	
	public static int Partition2(int a[],int left, int right) {
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
	

	
	
	public static void quickSort(int[]a,int left,int right) {
		
		int k ;
		if (left < right) {
			
			k = Partition(a, left, right);
			quickSort(a,left,k-1);
			quickSort(a,k+1,right);
		}
	}
	
	
public static void quickSortReverse(int[] a,int left,int right) {
		
		int k ;
		if (left < right) {
			
			k = Partition2(a, left, right);
			quickSortReverse(a,left,k-1);
			quickSortReverse(a,k+1,right);
		}
	}
}
