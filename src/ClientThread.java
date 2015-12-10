import java.lang.*;

public class ClientThread extends Thread  implements Runnable{
  public Client client;
  
  public ClientThread(Client client){
    this.client = client;
  }
  @Override
  public void run(){
    // TODO:
    // polla number of questions om > 0 
    // polla check claimed om   > 1
    // decrementera number of questions om nanting blev claimed
    System.out.println(Thread.currentThread().getId() + " THE THREAD ID");
  }
}
