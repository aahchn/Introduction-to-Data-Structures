// Aaron Chan - aahchan
// 4/7/2016
// lab2
// CommandLineArguments.java
//

class CommandLineArguments{
    public static void main(String[] args){
	int n = args.length;
	System.out.println("args.length = " + n);
	for(int i=0; i<n; i++) System.out.println(args[i]);
    }
}
