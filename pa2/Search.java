// Aaron Chan - aahchan
// pa2
// Search.java
// A program that searches for key words from a file
// 4/17/16

import java.io.*;
import java.util.Scanner;

class Search{
    public static void main(String[] args) throws IOException{
	
	String line = null;
	String[] token = null;
	Scanner input = null;
	int[] lineNumber = null;
    
  
	if(args.length < 2){
	    System.err.println("Usage: Search file target1 [target2 ..]");
	    System.exit(1); //doesn't have required amount of targets and exits
	}
    
	//while-loop scans and counts the number of lines in the file
	input = new Scanner(new File(args[0])); //args[0] has argument 0 from lineCount
	int lineCount = 0;
	while( input.hasNextLine() ){
	    lineCount = lineCount + 1; //moves on to next line
	    line = input.nextLine();
	}
    
	//intialize's the length of the String and int array
	token = new String[lineCount]; 
	lineNumber = new int[lineCount];
	input = new Scanner(new File(args[0]));//re-scans the file
    
	//adds number to the array
	for(int i=1; i<=lineNumber.length; i++){
	    lineNumber[i-1] = i;
	}
	//Scans the file, putting the word in the String array
	for(int i = 0; input.hasNextLine(); i++){
	    line = input.nextLine();
	    token[i] = line;
	}
	//puts the string Array in order
	mergeSort(token,lineNumber, 0, token.length-1);
	//prints if the target is found and on what line
	for(int i=1; i<args.length; i++){
	    System.out.println( binarySearch(token, lineNumber, 0, token.length-1, args[i]));
	}
    
	input.close();
    
    }
  
    //mergeSort 
    static void mergeSort(String[] A, int[] lineNumber, int p, int r){
	int q;
	if(p < r){
	    q = (p + r)/2;
	    mergeSort(A, lineNumber, p, q);
	    mergeSort(A, lineNumber, q+1, r); 
	    merge(A, lineNumber, p, q, r);
	}
    }

    // merge()
    // merges sorted subarrays A[p..q] and A[q+1..r]
    static void merge(String[] A, int[] lineNumber, int p, int q, int r){
	int n1 = q - p + 1; //calculate length of first half
	int n2 = r - q; // calculate length of 2nd half
	String[] L = new String[n1]; 
	String[] R = new String[n2];
	int[] leftNum = new int[n1]; //allocationg space for arrays
	int[] rightNum = new int[n2];
	int i, j, k; // i goes to left half, j goes thru right half, k goes thru the final positions in A
    
	for(i=0; i < n1; i++){ //copies data to another array
	    L[i] = A[p + i]; //copying it one by one
	    leftNum[i] = lineNumber[p + i];
	}
	for(j=0; j < n2; j++){ //copies data to another array
	    R[j] = A[q + j + 1]; //copying it one by one
	    rightNum[j] = lineNumber[q + j + 1];
	}

	i = 0;
	j = 0;
	for(k = p; k <= r; k++){ // k controls the itteration
	    if( i < n1 && j < n2){
		if( L[i].compareTo(R[j]) > 0 ){ //if L[i] is winner, it gets copied
		    A[k] = L[i];
		    lineNumber[k] = leftNum[i]; 
		    i++;
		}
		else{ //if on right half, it gets copied
		    A[k] = R[j];
		    lineNumber[k] = rightNum[j];
		    j++;
		} 
	    }
	    else if( i < n1){ //if still on left half, just copy whats left on the left half and increment
		A[k] = L[i];
		lineNumber[k] = leftNum[i];
		i++;
	    }
	    else{  
		A[k] = R[j];
		lineNumber[k] = rightNum[j];
		j++;
	    } 
	}
    }

    // binarySearch()
    // pre: Array A[p..r] is sorted
    public static String binarySearch(String[] A, int[] lineNumber, int p, int r, String target){
	int q;
	if( p == r ){
	    return target + " not found";
	}
	else{
	    q = (p + r)/2;
	    if( A[q].compareTo(target) == 0){
		return target + " found on line " + lineNumber[q];
	    }
	    else if( A[q].compareTo(target)<0 ) {
		return binarySearch(A, lineNumber, p, q, target);
	    }
	    else{ 
		return binarySearch(A, lineNumber, q+1, r, target);
	    }
	}
    
    } 
}

