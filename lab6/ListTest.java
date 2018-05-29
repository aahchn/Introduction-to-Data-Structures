// Aaron Chan 
// lab6
// ListTest.java
// 5/24/16

public class ListTest{
    public static void main(String[] args){
	List<String> X = new List<String>();
	List<String> Y = new List<String>();
	List<List<String>> Z = new List<List<String>>();
	int i, j, k;

	X.add(1, "a");
	X.add(2, "b");
	Y.add(1, "c");
	Z.add(1, X);
	Z.add(2, Y);

	System.out.println("X: " + X);
	System.out.println("Y: " + Y);
	System.out.println("Z: " + Z);
	System.out.println("X.equals(X) is " + X.equals(X));
	System.out.println("X.equals(Y) is " + X.equals(Y));
	System.out.println("X.equals(Z) is " + X.equals(Z));
	System.out.println("X.size() is " + X.size());
	System.out.println("Y.size() is " + Y.size());
	System.out.println("Z.size() is " + Z.size());

	X.remove(1);
	Y.remove(1);

	System.out.println("X.size() is " + X.size());
	System.out.println("Y.size() is " + Y.size());
	System.out.println("Z: " + Z);
	System.out.println();
	try{
	    System.out.println(X.get(200));
	}catch(ListIndexOutOfBoundsException e){
	    System.out.println("Caught Exception: ");
	    System.out.println(e);
	    System.out.println("Ignoring error");
	}
	System.out.println();
	System.out.println("Z.get(1) is "+ Z.get(1));
    }
}
