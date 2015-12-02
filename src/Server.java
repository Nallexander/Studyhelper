import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
        
public class Server implements Hello {   
    public Server() {}

    public String sayHello() {
        return "Hejhalloj";
    }
    public LinkedList<String> getHelpList(){
    return hl;	
    }
        
    public static void main(String args[]) {
    	public LinkedList<String> hl = new Linkedlist();
    	hl.add("Hej Tony");
    	hl.add("Hej Douglas");
    	
        try {
            Server obj = new Server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);

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
