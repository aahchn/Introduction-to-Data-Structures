// Aaron Chan - aahchan
// KeyNotFoundException.java
// pa3
// 4/27/16
// Throws exception if there aren't any keys found.

public class KeyNotFoundException extends RuntimeException{
    public KeyNotFoundException(String s){
	super(s);
    }
}
