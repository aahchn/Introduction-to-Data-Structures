// Aaron Chan
// lab7
// Dictionary.java
// 5/30/16

public class Dictionary implements DictionaryInterface{

    // private types and functions ------------------------------------------------

    // NodeObj
    private class Node{
	String key;
	String value;
	Node left;
	Node right;
	// newNode()
	// constructor for private Node type
	Node(String k, String v) {
	    key = k;
	    value = v;
	    left = right = null;
	}
    } 
    private Node root;
    private int numPairs;

    // Dictionary Constructor
    public Dictionary(){
	root = null;
	numPairs = 0;
    } 

    //findKey()
    //returns node with key k in subtee at R
    Node findKey(Node R, String key){
	if(R==null || (key.equals(R.key)))
	    return R;
	if( (key.compareTo(R.key)<0 )) 
	    return findKey(R.left, key);
	else // strcmp(k, R->key)>0
	    return findKey(R.right, key);
    }


    // findParent()
    // returns parent of N in subtree rooted at R, or returns NULL
    Node findParent(Node N, Node R){
	Node P = null;
	if( N!=R ){
	    P = R;
	    while( P.left!=N && P.right!=N ){
		if((N.key.compareTo(P.key)<0))
		    P = P.left;
		else
		    P = P.right;
	    }
	}   
	return P;
    }


    //findLeftmost()
    //returns leftmost node at subtree of R, or returns NULL
    Node findLeftmost(Node R){
	Node L = R;
	if( L!=null ) for( ; L.left!=null; L=L.left);
	return L;
    }

    //printInOrder()
    //prints key/value pairs at subtree of R of increasing order
    String printInOrder(Node R){
	String S = ""; 
	if( R!=null ){
	    S += printInOrder(R.left);
	    S += R.key + " " + R.value + "\n";
	    S +=  printInOrder(R.right);
	}
	return S; 
    }

    //deleteAll()
    //delete all nodes in subtree N
    void deleteAll(Node N){
	if( N!=null ){
	    deleteAll(N.left);
	    deleteAll(N.right);
	} 
    }

    //public functions------
    
    //isEmpty()
    //returns 1 if S is empty, 0 if not empty
    public boolean isEmpty(){
	return (numPairs == 0);
    }

    //size()
    //returns number of key/value pairs
    public int size(){
	return(numPairs);
    }

    //lookup()
    //returns value such that the key/value pair is in D, null it nothing exist
    public String lookup(String key){
	Node N = findKey(root, key);
	return ( N==null ? null : N.value );
    }

    // insert()
    // inserts new key/value pair into D
    public void insert(String key, String value) throws KeyCollisionException{
	if( findKey(root, key)!=null ){
	    throw new KeyCollisionException("Cannot insert duplicate key");
	}
	Node N = new Node(key, value);
	Node B = null;
	Node A = root;
	while( A!=null ){
	    B = A;
	    if( key.compareTo(A.key)<0 ) 
		A = A.left;
	    else A =  A.right;
	}
	if( B==null ){ 
	    root = N;
	}
	else if( key.compareTo(B.key)<0 ){ 
	    B.left = N;
	}
	else B.right = N;
	numPairs++;
    } 

    // delete()
    // deletes pair with the key k
    public void delete(String key) throws KeyNotFoundException{
	Node N = findKey(root, key);
	Node P;
	Node S;
	if( N==null ){
	    throw new KeyNotFoundException("Cannot delete non-existent keys");
	}
	if( N.left==null && N.right==null ){  // case 1:  N has no children
	    if( N==root ){
		root = null;
	    }else{
		P = findParent(N, root);
		if( P.right==N ) P.right = null;
		else P.left = null;
	    }
	}else if( N.right==null ){            // case 2a:  N has a left child only
	    if( N==root ){
		root = N.left;
	    }else{
		P = findParent(N, root);
		if( P.right==N ) P.right = N.left;
		else P.left = N.left;
	    }
	}else if( N.left==null ){            // case 2b: N has a right child only
	    if( N==root ){
		root = N.right;
	    }else{
		P = findParent(N, root);
		if( P.right==N ) P.right = N.right;
		else P.left = N.right;
	    }
	}else{                       // case 3:  N has two children
	    S = findLeftmost(N.right);
	    N.key = S.key;
	    N.value = S.value;
	    P = findParent(S, N);
	    if( P.right==S ) P.right = S.right;
	    else P.left = S.right;
	}
	numPairs--;
    }

    // makeEmpty()
    // resets D to the empty state.
    public void makeEmpty(){
	deleteAll(root);
	root = null;
	numPairs = 0;
    }

    // printDictionary()
    // prints text representation of D to the file pointed to by out
    public String toString(){
	Node N = root;
	return printInOrder(N);
    }   
} 
