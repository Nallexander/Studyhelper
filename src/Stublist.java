import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class Stublist{
    public LinkedList<Studyhelper> list;
    public void addStubs(String[] args) {
	
	try {
	    if (args.length == 0) { //No argument given
		Registry registry = LocateRegistry.getRegistry();
		this.list.add((Studyhelper) registry.lookup("Studyhelper"));
	    
	    }
	
	    else if (args.length == 1){ //One  argument given
		String host = args[0];
		Registry registry = LocateRegistry.getRegistry(host);   
		this.list.add((Studyhelper) registry.lookup("Studyhelper"));
		
	    }
	    System.out.println("Before args.length > 1");
	    if (args.length > 1) { //More than one argument given
	    

		for (int i = 0; i < args.length; i = i+2) {
		    System.out.println("for");
		    Registry registry = LocateRegistry.getRegistry(args[i], Integer.parseInt(args[i+1])); 
		    this.list.add((Studyhelper) registry.lookup("Studyhelper"));
		}
		
		
		
	    }

	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
