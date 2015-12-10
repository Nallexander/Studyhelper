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
  @Override
  public void run(){
    // TODO:
    // polla number of questions om > 0 
    // polla check claimed om   > 1
    // decrementera number of questions om nanting blev claimed

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
        String claimedID = "";
        try{
          claimedID = this.stub.helpObjectClaimedID();
        }
        catch(Exception e){
          System.err.println("helpObjectClaimedID FAILED");
        }
        System.out.println("Your question [" + claimedID + "]Has been claimed!");
        this.client.decrementNumberOfQuestions();
      }
      
    }
  }
}
