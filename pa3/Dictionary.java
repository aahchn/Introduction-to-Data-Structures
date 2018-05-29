// Aaron Chan - aahchan
// Dictionary.java
// pa3
// 4/27/16

public class Dictionary implements DictionaryInterface{

    //inner private Node class
    private class Node{
	String key;
	String value;
	Node next;
	//node class constructor
	Node(String key, String value){
	    this.key = key;
	    this.value = value;
	    next = null;
	}
    }
    private Node head, tail;
    private int numItems;
    public Dictionary(){
	head = null;
	tail = null;
	numItems = 0;
    }

    //isEmpty()
    //Returns true if the Dictionary contains no pairs and returns false otherwise
    public boolean isEmpty(){
	return (numItems == 0); 
    }

    //size()
    //Returns the number of (key, value) pairs in the Dictionary.
    public int size(){
	return numItems;
    }

    //lookup()
    //takes a string key and returns value with that key or nothing if keys doesn't exist
    public String lookup(String key){
	Node N = head;
	while(N != null){
	    if(N.key.equals(key)){
		return N.value;
	    }
	     N = N.next;
	}
	return null;
    }

    //insert()
    //takes 2 strings (key and value) that aren't in the linked list and puts it into the linked list
    public void insert(String key, String value) throws DuplicateKeyException{
	if(lookup(key) != null){
	    throw new DuplicateKeyException("cannot insert duplicate keys");
	}
	    else{
		if (head == null){ //makes new node with the head if head is initially null
		    Node N = new Node(key,value);
		    head = N;
		    numItems++;
		}
		else{ //if head != null and N.next is null, terminates loop.
		    Node N = head;
		    while( N != null){
			if(N.next == null){
			    break;
			}
			N = N.next; //if N.next != null, then N refers to the next node.
		    }
		    N.next = new Node(key,value); //if N == null, then makes new node.
		    numItems++;
		}
    }
    }

    //delete()
    //deletes pair with certain key
    public void delete(String key) throws KeyNotFoundException{
	if (lookup(key) == null){ //can't delete nothing.
	    throw new KeyNotFoundException("cannot delete non-existent key");
    }
    else{
	if(numItems <= 1){ //removes the first element if list has only has 1 element
	    Node N = head; //Node N refers to head
	    head = head.next;
	    N.next = null; 
	    numItems--;
	}
	else{ //removes the first or any other element
	    Node N = head;
	    if(N.key.equals(key)){
		head = N.next;
		numItems--;
	    }
	    else{
		while(!N.next.key.equals(key)){
		    N = N.next; //moves to the next node if key doesnt match
		}
		N.next = N.next.next;
		numItems--;
	    }
	}
    }
    }

    //makeEmpty()
    //makes linked list empty
    public void makeEmpty(){
	head = null; //makes head null
	numItems = 0; // makes number of items of list to 0.
    }

    //toString()
    //prints contents of linked list
    public String toString(){
	String s = "";
	Node N = head;
	while( N != null){
	    s += N.key + " " + N.value + "\n"; 
	    N = N.next;
	}
	return s; //return nothing if null, if not, returns N.key and N.value in string form
    }

    //findkey()
    //returns reference to where key is
    public Node findkey(String key){
	Node N = head;
	while(N != null){
	    if(key.equals(N.key)){ //if key is found, break the loop.
		break;
	    }
	    N = N.next;
	}
	if(N == null){ //returns nothing if N is nothing
	    return null;
	}
	else{
	    return  N;
	}
    }   
}
