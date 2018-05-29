// Aaron Chan -  aahchan
// 4/7/2016
// pa1
// Recursion.java
// Uses the recursive methods to display items from an array.

class Recursion {   
    // reverseArray1()
    // Places the leftmost n elements of X[] into the rightmost n positions in
    // Y[] in reverse order
    static void reverseArray1(int[] X, int n, int[] Y){
	if(n > 0){
	    Y[X.length - n] = X[n - 1];   //makes left side of X = right side of Y
	    reverseArray1 (X, n-1, Y);    //recurs on the next values
       }
    }
    // reverseArray2()
    // Places the rightmost n elements of X[] into the leftmost n positions in
    // Y[] in reverse order.
    static void reverseArray2(int[] X, int n, int[] Y){
	if(n > 0){
	    Y[n - 1] = X[X.length - n]; //dual of reverseArray1
	    reverseArray2(X, n-1, Y);   //recurs on the next values
       }
    }
    // reverseArray3()
    // Reverses the subarray X[i...j].
    // Swaps position using temp within the array
    static void reverseArray3(int[] X, int i, int j){
	int temp;
	if(i < X.length && j > 0 && i <= j){ 
	    temp = X[i];  
	    X[i] = X[j]; 
	    X[j] = temp;    //swapping the values with a temp function
	    i++;            //increments i to the next value
	    j--;            //decrements j to the previous value
	    reverseArray3(X, i, j); //recurs on what just happens
    }
}
    // maxArrayIndex()
    // returns the index of the largest value in int array X
    static int maxArrayIndex(int[] X, int p, int r){
	int q;
	if(p < r){
	    q = (p + r)/2; //splits array into 2 subarrays
	    int maximum1 = maxArrayIndex(X, p, q); //splits first half of array into 2 continuously
	    int maximum2 = maxArrayIndex(X, ++q, r); // splits 2nd half into 2 continously
	    return((X[maximum1] > X[maximum2]) ? maximum1 : maximum2); //compares the maximum of both arrays and returns the higher one.
	}
	else return p; 
    }

    // minArrayIndex()
    // returns the index of the smallest value in int array X
    static int minArrayIndex(int[] X, int p, int r){
	int q;
	if(p < r){ 
	    q = (p + r)/2; // splits array into 2 sub arrays
	    int minimum1 = minArrayIndex(X, p, q);
	    int minimum2 = minArrayIndex(X, ++q, r);
	    return((X[minimum1] < X[minimum2]) ? minimum1 : minimum2); //returns minimum of the 2 subarrays after comparing them
	} 
	else return r;
    }

    // main()
    public static void main(String[] args){
      
	int[] A = {-1, 2, 6, 12, 9, 2, -5, -2, 8, 5, 7};
	int[] B = new int[A.length];
	int[] C = new int[A.length];
	int minIndex = minArrayIndex(A, 0, A.length-1);
	int maxIndex = maxArrayIndex(A, 0, A.length-1);
      
	for(int x: A) System.out.print(x+" ");
	System.out.println(); 
      
	System.out.println( "minIndex = " + minIndex );  
	System.out.println( "maxIndex = " + maxIndex );  

	reverseArray1(A, A.length, B);
	for(int x: B) System.out.print(x+" ");
	System.out.println();
      
	reverseArray2(A, A.length, C);
	for(int x: C) System.out.print(x+" ");
	System.out.println();
      
	reverseArray3(A, 0, A.length-1);
	for(int x: A) System.out.print(x+" ");
	System.out.println();        
    }
    }
