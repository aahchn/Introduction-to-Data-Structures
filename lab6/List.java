// Aaron Chan - aahchan
// 5/24/16
// lab6
// List.java
// Linked List implementation of IntegerList ADT

public class List<T> implements ListInterface<T>{
    //Inner Node Class
    private class Node{
	T item;
	Node next; 

	Node(T x){
	    item = x;
	    next = null;
	}
    }

    //Fields for List Class
    private Node head;
    private int numItems;

    //List()
    //constructor for List class
    public List(){
	head = null;
	numItems = 0;
    }

    //Helper functions

    //Find()
    //return reference to Node at index of this list 
    private Node find(int index){
	Node N = head; 
	for(int i = 1; i < index; i++){
	    N = N.next;
	}
	return N;
    }


    //ADT operations

    //isEmpty()
    //returns true of IntegerList is empty
    public boolean isEmpty(){
	return (numItems == 0);
    }

    //size()
    //returns number of elements in list
    public int size(){
	return numItems;
    }

    //get()
    //returns item at position index in list
    public T get(int index) throws ListIndexOutOfBoundsException {
	if (index < 1 || index > numItems) {
	    throw new ListIndexOutOfBoundsException(index + " is not a valid index");
	}
	Node N = find(index);
	return N.item;
    }

    //add()
    //insert newItem into list at position index
    public  void add(int index, T newItem) throws ListIndexOutOfBoundsException{
	if(index < 1 || index > numItems + 1){
	    throw new ListIndexOutOfBoundsException(index + " is not a valid index");
	}
	if (index == 1) {
	    Node N = new Node(newItem);
	    N.next = head;
	    head = N;
	} else {
	    Node X = find(index-1);
	    Node Y = X.next;
	    X.next = new Node(newItem);
	    X = X.next;
	    X.next = Y;
	}
	numItems++;
    }

    //remove()
    //deletes item at the position index
    public void remove(int index) throws ListIndexOutOfBoundsException{
	if (index < 1 || index > numItems){
	    throw new ListIndexOutOfBoundsException(index + " is not a valid index");
	}
	if (index == 1) {
	    Node N = head;
	    head = head.next;
	    N.next = null;
	} else {
	    Node A = find(index-1);
	    Node B = A.next;
	    A.next = B.next;
	    B.next = null;
	}
	numItems--;
    }

    //removeAll()
    //deletes everything 
    public void removeAll(){
	head = null;
	numItems = 0;
    }

    //toString()
    //prints to stdout
    public String toString(){
	String s = "";
	for (Node N = head; N != null; N = N.next){
	    s += N.item.toString() + " ";
	}
	return s;
    }

    //equals()
    //returns true/false if contents of the list equals on another list
    @SuppressWarnings("unchecked")
	public boolean equals(Object rhs) {
	boolean eq = false;
	List<T> R = null;
	Node N = null;
	Node M = null;

	if (this.getClass() == rhs.getClass()) {
	    R = (List<T>)rhs;
	    eq = (this.numItems == R.numItems);

	    N = this.head;
	    M = R.head;
	    while (eq && N != null) {
		eq = (N.item == M.item);
		N = N.next;
		M = M.next;
	    }
	}
	return eq;
    }
}
