// Aaron Chan
// pa4
// 5/12/16
// Simulation.java
// Program that simulates m number of jobs

import java.io.*;
import java.util.Scanner;

public class Simulation{
    public static void main(String[] args) throws IOException{
	Scanner in;
	PrintWriter trace, report;
	Queue jobQueue, backupQueue;
	int m;

	// Check number of command line arguments
	if(args.length!=1) 
	    Usage();

	// Open file for reading and files for writing
	in = new Scanner(new File(args[0]));
	trace = new PrintWriter(new FileWriter(args[0]+".trc"));
	report = new PrintWriter(new FileWriter(args[0]+".rpt"));

	// Read jobs
	backupQueue = new Queue();
	m = Integer.parseInt(in.nextLine());
	while(in.hasNextLine()){
	    backupQueue.enqueue(getJob(in));
	}

	// Write basic information to report and trace files
	report.println("Report file: "+args[0]+".rpt");
	report.println(m + (m==1 ? " Job:" : " Jobs:"));
	report.println(backupQueue);
	report.println("\n***********************************************************");
	trace.println("Trace file: "+args[0]+".trc");
	trace.println(m + (m==1 ? " Job:" : " Jobs:"));
	trace.println(backupQueue+"\n");

	// Simulation from 1 to m-1 processors
	for(int n=1; n<=m-1; n++){

	    // Declaration and initialization of local variables
	    // Some variables are initialized as -1 in order to satisfy initial conditions
	    Job nextJob, doneJob, auxJob, startJob;
	    Queue[] storProcQ = new Queue[n+1];
	    for(int i=0; i<=n; i++) storProcQ[i] = new Queue(); // Queue for storage and processor queues
	    storProcQ[0] = (Queue) backupQueue.copyQueue(); // Storage queue is at index 0
	    int nextArrival = -1, nextFinish = -1, time = 0, nextTime = 0;
	    int jobsDone = 0, minimumQueue = 0;
	    int j, k, waitTime = 0, maxWait = 0, totalWait = 0;
	    float avgWait = 0;
	    int[] pFinish = new int[n];
	    for(int i=0; i<n; i++) pFinish[i] = -1;
	    boolean jobFinished = false, printTrace = false;

	    // Print some basic information to trace file
	    trace.println("*****************************");
	    trace.println(n + (n==1 ? " processor:" : " processors:"));
	    trace.println("*****************************");
	    timeTrace(trace, storProcQ, time);

	    // Loop for each processor
	    // A variable for finished jobs is used to know when all jobs were processed
	    while(jobsDone!=m){

		// Calculates the time at which the next job on the storage queue will arrive
		// It does not consume it though
		if(!storProcQ[0].isEmpty() && (((Job) storProcQ[0].peek()).getFinish()==-1)){
		    nextJob = (Job) storProcQ[0].peek();
		    nextArrival = nextJob.getArrival();
		}
		    
		// Calculates and computes the finish time for a event arriving at the head of a processor
		for(j=1; j<=n; j++){ 
		    if(!storProcQ[j].isEmpty()){
			auxJob = (Job) storProcQ[j].peek();
			if(auxJob.getArrival()<=time && auxJob.getFinish()==-1){
			    startJob = (Job) storProcQ[j].peek();
			    startJob.computeFinishTime(time);
			    pFinish[j-1] = startJob.getFinish();
			    waitTime = startJob.getWaitTime();
			    totalWait += waitTime;
			    if(waitTime>maxWait) maxWait = waitTime;
			    if(storProcQ[0].isEmpty() ||
			       (!storProcQ[0].isEmpty() && (((Job) storProcQ[0].peek()).getArrival()!=time))){
				printTrace = true;
			    }
			}
		    }
		}
		    
		// Calculates the time for next finishing event
		nextFinish = pFinish[0];
		for(j=0; j<n; j++){ 
		    if(pFinish[j]!=-1 && pFinish[j]<=nextFinish){
			nextFinish = pFinish[j];
		    }else if(nextFinish==-1) nextFinish = pFinish[j];
		}

		// Calculates next time frame at which an event will occur
		// Decision is based on next arrival and next finish events
		if((nextFinish>=time && nextFinish<=nextArrival) ||
		   (nextArrival<time && nextFinish>=time)){
		    nextTime = nextFinish;
		}else{
		    nextTime = nextArrival;
		}
		    
		// Jobs finishing now
		for(j=1; j<=n; j++){ 
		    if(!storProcQ[j].isEmpty()){
			if(((Job) storProcQ[j].peek()).getFinish()==time){
			    doneJob = (Job) storProcQ[j].dequeue();
			    pFinish[j-1] = -1;
			    storProcQ[0].enqueue(doneJob);
			    jobsDone++;
			    jobFinished = (storProcQ[j].isEmpty() ? true : jobFinished);
			}
		    }
		}
		// Control condition for printing a job finished in trace
		if(jobFinished){
		    printTrace = true;
		    jobFinished = false;
		}
		// Jobs arriving now
		if(time==nextArrival){
		    for(j=1; j<=n; j++){
			if(storProcQ[j].length()<storProcQ[minimumQueue].length() ||
			   (storProcQ[j].length()==storProcQ[minimumQueue].length() && j<minimumQueue)){
			    minimumQueue = j;
			}
		    }
		    storProcQ[minimumQueue].enqueue((Job) storProcQ[0].dequeue());
		    if(((Job) storProcQ[minimumQueue].peek()).getFinish()!=-1){
			printTrace = true;
		    }
		    nextArrival = -1;
		}
		    
		// Prints current time frame information to trace file
		// In order to print all events in a certain time frame, nextTime should be different
		// than current time. Also, there should be something to print, expressed by printTrace
		if(nextTime!=time && printTrace){
		    timeTrace(trace, storProcQ, time);
		    printTrace = false;
		}

		// Updates next time frame to analyze
		time = nextTime;
	    }
	           
	    // Print trace after all jobs are finished
	    timeTrace(trace, storProcQ, time);

	    // Calculates avgWait time
	    avgWait = (float) totalWait/m;

	    // Prints some more basic information to report file
	    report.print(n + (n==1 ? " processor: " : " processors: "));
	    report.printf("totalWait=%d, maxWait=%d, averageWait=%.2f\n", totalWait, maxWait, avgWait);

	    // Reset finish times in each job on the storage Queue
	    // Important, because they are referring to backupQueue
	    while(!storProcQ[0].isEmpty()){
		auxJob = (Job) storProcQ[0].dequeue();
		auxJob.resetFinishTime();
	    }
	}

	// Close files
	in.close();
	trace.close();
	report.close();
    }

    // Auxiliar function to print time frame in trace file
    static void timeTrace(PrintWriter trace, Queue[] storProcQ, int time){
	trace.println("time="+time);
	for(int k=0; k<storProcQ.length; k++) trace.println((k)+": "+storProcQ[k]);
	trace.println();
    }


    // Reads a line from an input stream to create a new Job object
    public static Job getJob(Scanner in) {
	String[] s = in.nextLine().split(" ");
	int a = Integer.parseInt(s[0]);
	int d = Integer.parseInt(s[1]);
	return new Job(a, d);
    }
  
    // Auxiliar function to print Usage message 
    static void Usage(){
	System.err.println("Usage: Simulation <input_file>");
	System.exit(1);
   
    }

}

