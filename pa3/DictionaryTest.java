// Aaron Chan - aahchan
// 4/27/16
// pa3
// DictionaryTest.java
// To serve as a test client for the Dictionary ADT while it is under construction

class DictionaryTest{
    public static void main (String[] args){
	Dictionary Test = new Dictionary();
	System.out.println(Test.isEmpty());
	Test.insert("4","f");
	System.out.println(Test.isEmpty());
	Test.insert("5","d");
	System.out.println(Test.size());
	Test.insert("6","3");
	Test.insert("7","c");
	Test.insert("3","e");
	Test.insert("9","3");
	//Test.delete("4");
	//Test.insert("6","e"); //throws exception 
	System.out.println(Test);
	System.out.println(Test.lookup("7"));
	System.out.println("********");
	Test.delete("6");
	System.out.println(Test.size());
	System.out.println(Test.lookup("6"));
	Test.delete("5");
	Test.delete("4");
	System.out.println(Test.lookup("9"));
	System.out.println(Test.size());
	System.out.println(Test);
	Test.makeEmpty();
	System.out.println(Test.size() + " " + Test.isEmpty());
    
    }
}
