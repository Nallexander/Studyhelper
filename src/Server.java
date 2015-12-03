import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
        
public class Server implements Hello {   
  //public static String SERVER_IP = "an ip adress"; //serverns ip address måste ändras när vi kör på olika datorer!" 
  private LinkedList<String> helpList; //listan med requests TODO: just nu string, ändra sen till vår objekt
	
  public Server() {
    helpList = new LinkedList();
  }

  public void claimHelpObject(int index){
    //TODO claim the in the list
  }

  public void deleteHelpObject(int index, String clientAddress){
  //TODO delete index check kolla om rätt person deletar
  }
  public void addHelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress){
    HelpObject newHelpObject = new HelpObject(courseName, title, message, location, userName, other, clientAddress);
    //this.helpList.add(newHelpObject);
  }
  
  public String sayHello() {
    return "Hejhalloj";
  }
  
  public String printSizeHelpList(){
      return ("number of items in the list: " + helpList.size());
  }
        
  public static void main(String args[]) {
    	
    try {
      Server server = new Server();
      Hello stub = (Hello) UnicastRemoteObject.exportObject(server, 0);
            
      server.helpList.add("Hej Tony");
      server.helpList.add("Hej Douglas");
                
      // Bind the remote object's stub in the registry
      Registry registry = LocateRegistry.getRegistry();
      //System.setProperty("java.rmi.server.hostname", server.SERVER_IP); // simons ville ha denna rad
      registry.bind("Hello", stub);
      
      System.err.println("Server ready");
            
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
