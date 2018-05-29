// Aaron Chan - aahchan
// 4/11/2016
// lab2
// FileReverse.java
// A program that takes 2 command line args giving names of input and output files

import java.io.*;
import java.util.Scanner;

class FileReverse{
    public static void main(String[] args) throws IOException{
	Scanner in = null;
	PrintWriter out = null;
	String line = null;
	// check number of command line arguments is at least 2
	if(args.length < 2){
	    System.out.println("Usage: FileCopy <input file> <output file>");
	    System.exit(1);
	}
	// open files
	in = new Scanner(new File(args[0]));
       	out = new PrintWriter(new FileWriter(args[1]));
	// read lines from in, extract and print tokens from each line
	while(in.hasNext()){
	    line = in.next();
	    out.println(stringReverse(line, line.length()));
	}
	// close files
	in.close();
	out.close();
    }
    public static String stringReverse(String s, int n){
	String result = "";
	if(n > 0){
	    result = s.charAt(n - 1) + stringReverse(s, n - 1);
	}
	return result;
    }
}
