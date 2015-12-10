import java.lang.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientGUIThread extends Thread implements Runnable{
  public ClientGUI client;
  public Studyhelper stub;
  private boolean running;
  
  public ClientGUIThread(ClientGUI client, Studyhelper stub){
    this.client = client;
    this.running = true;
    this.stub = stub;
  }
  @Override
  public void run(){
    // TODO:
    // polla number of questions om > 0 
    // polla check claimed om   > 1
    // decrementera number of questions om nanting blev claimed
    String claimedID = "";
    while(running){
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
        System.err.println("sleep did not work");
        Thread.currentThread().interrupt();
      }

      System.out.println(Thread.currentThread().getId() + " THE THREAD ID");

      
      if(this.client.getNumberOfQuestions() > 0){  
        try{
          claimedID = this.stub.helpObjectClaimedID();
        }
        catch(Exception e){
          System.err.println("helpObjectClaimedID FAILED");
        }
        
        if(!(claimedID.equals("TEST"))){
        this.client.getClaimedIDFromThread(claimedID);
        this.client.decrementNumberOfQuestions();
        }
             }
      
    }
  }
}
