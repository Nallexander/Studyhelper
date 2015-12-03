import java.rmi.server.RemoteServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
        
public class Server extends RemoteServer implements Studyhelper{   
    //public static String SERVER_IP = "an ip adress"; //serverns ip address måste ändras när vi kör på olika datorer!" 
    private LinkedList<HelpObject> helpList;
    private Server server;
	
    public Server() {
	helpList = new LinkedList();
	
    }

    public void claimHelpObject(int index){
	//TODO claim the in the list
    }

    public void deleteHelpObject(int index, String clientAddress){
	//TODO delete index check kolla om rätt person deletar
    }
    public void addHelpObject(String courseName, String title, String message, String location, String userName, String other){
	try {
	    String clientAddress = this.getClientHost();
	    HelpObject newHelpObject = new HelpObject(courseName, title, message, location, userName, other, clientAddress);
	    this.helpList.add(newHelpObject);
	}
	catch (Exception e) {
	    System.err.println("Could not resolve client IP: " + e.toString());
	    
	}
	
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
	    Studyhelper stub = (Studyhelper) UnicastRemoteObject.exportObject(server, 0);
            
                
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
