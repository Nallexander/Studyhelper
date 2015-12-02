import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
        
public class Server implements Hello {   

	public LinkedList<String> helpList
	
	public Server() {
		 helpList = new Linkedlist();
	}

    public String sayHello() {
        return "Hejhalloj";
    }
    public void printSizeHelpList(){
    System.out.println("number of items in the list: " + helpList.size());
    }
        
    public static void main(String args[]) {
    	
        try {
            Server server = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(server, 0);
            
            server.helpList.add("Hej Tony");
        	server.helpList.add("Hej Douglas");
        	
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);

            System.err.println("Server ready");
            
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
