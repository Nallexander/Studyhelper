import java.lang.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientThread extends Thread implements Runnable{
    public Client client;
    public Studyhelper stub;
    private boolean running;

  
    public ClientThread(Client client, Studyhelper stub){
	this.client = client;
	this.running = true;
	this.stub = stub;
    }

    protected synchronized boolean checkIfClaimMessageIsSent(String claimedString) { 
	
	int claimedInt = Integer.parseInt(claimedString);
	for (int i = 0; i <= claimedInt; i++) {
	    if (this.client.claimedList.size() < i) {
		this.client.claimedList.add(false);
	    }	
	}
	if (!this.client.claimedList.get(claimedInt-1)) {
	    this.client.claimedList.set(claimedInt-1, true);
	    return true;
	}
	return false;
	
	
    }
    @Override
    public void run(){
	String claimedID = "";
	while(running){
	    try {
		Thread.sleep(3000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
		System.err.println("sleep did not work");
		Thread.currentThread().interrupt();
	    }


      
	    if(this.client.getNumberOfQuestions() > 0){  
		try{
		    claimedID = this.stub.helpObjectClaimedID();
		}
		catch(Exception e){
		    System.err.println("helpObjectClaimedID FAILED");
		}
		if(!(claimedID.equals("TEST"))){ //def-programmering? 
		    if (this.checkIfClaimMessageIsSent(claimedID)) {
			System.out.println("Your question [" + claimedID + "]Has been claimed!");
			this.client.decrementNumberOfQuestions();
		    }
		}
	    }
      
	}
    }
}
