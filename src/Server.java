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
	    String clientAddress = RemoteServer.getClientHost();
	    HelpObject newHelpObject = new HelpObject(courseName, title, message, location, userName, other, clientAddress);
	    this.helpList.add(newHelpObject);
	}
	catch (Exception e) {
	    System.err.println("Could not resolve client IP: " + e.toString());
	    
	}
	
    }
  
    public String sayHello() {
	this.addHelpObject("Transformmetoder", "Projektet", "Omöjligt", "1111", "Alexander", "Hejhej");
	String IP = this.helpList.get(0).getIP();
	//String IP = "Hej";
	return IP;
    }
  
    public String printSizeHelpList(){
	return ("number of items in the list: " + helpList.size());
    }
        
    public String printExtendedInfo(int index) {
	return this.helpList.get(index).extendedInfoString();
    }
    public String printHelpList() {
	String printedList = "";
	for (int i = 0; i < this.helpList.size(); i++){
	    
	    printedList =  (printedList + i + ": " + this.helpList.get(i).basicInfoString() + "\n");
	}
	return printedList;
    }

    public static void main(String args[]) {
    	
	try {
	    Server server = new Server();
	    Studyhelper stub = (Studyhelper) UnicastRemoteObject.exportObject(server, 0);
	    

            
                
	    // Bind the remote object's stub in the registry
	    Registry registry = LocateRegistry.getRegistry();
	    //System.setProperty("java.rmi.server.hostname", server.SERVER_IP); // simons ville ha denna rad
	    registry.bind("Studyhelper", stub);
	    
	    System.err.println("Server ready");
            
	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
