import java.rmi.server.RemoteServer;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.rmi.server.*;
        
public class Server extends RemoteServer implements Studyhelper{   
    //public static String SERVER_IP = "an ip adress"; //serverns ip address måste ändras när vi kör på olika datorer!" 
    private LinkedList<HelpObject> helpList;
    private Server server;
    private int globalQuestionID = 0;
	
    public Server() {
	helpList = new LinkedList();
	
    }

    public int getNumberOfUnclaimedQuestions() {
	int num = 0; 
	try {

	    for (int i = 0; i < this.helpList.size(); i++) {
	    
		if (!this.helpList.get(i).isClaimed() &&
		    RemoteServer.getClientHost().equals(this.helpList.get(i).getClientAddress())) {
		    num++;
		}
	    }
	    }
	catch (ServerNotActiveException e) {
	    System.err.println("getNumberOfUnclaimedQuestions does not work");
	    }
	return num;
    }
	
    public boolean checkIfClaimed(String questionID){
    	try{
    	for (int i = 0; i < this.helpList.size(); i++){
    	    if (this.helpList.get(i).getQuestionID().equals(questionID)){
    		if ((this.helpList.get(i).isClaimed())){ //if object can be claimed then claim
    			return true;
    		}
    	    }
    	}
    		return false;
    }
    	catch (Exception e) {
    		
    	    System.err.println("Woops: " + e.toString());
    	    	
    	}  
    	return false;
    }
    

    public String claimHelpObject(String questionID){
    	
	for (int i = 0; i < this.helpList.size(); i++){
	    if (this.helpList.get(i).getQuestionID().equals(questionID)){
		if (!(this.helpList.get(i).isClaimed())){ //if object can be claimed then claim
			try{
		    this.helpList.get(i).claim(true);
		    
		    String claimAddress = RemoteServer.getClientHost();
		    this.helpList.get(i).setClaimAddress(claimAddress);
		    
		    
		    return("You have claimed the question with ID " + questionID);
		}
		catch (Exception e) {
			
		    System.err.println("Could not resolve client IP: " + e.toString());
		    	
		}  
		}
		else{
		    return("The question with ID " + questionID + " has already been claimed");
		}
	    }
	}
	return("This question ID does not exist");
    }
  
    /* Returns true if item was successfully deleted */
    public boolean deleteHelpObject(String questionID){
	try {
	    for (int i = 0; i < this.helpList.size(); i++) {
		if (questionID.equals(this.helpList.get(i).getQuestionID())) {
		    if ((RemoteServer.getClientHost()).equals(this.helpList.get(i).getIP())) {
			this.helpList.remove(i);
			return true;
		    }
		    break;
		    
		}
	    }
	    return false;
	}
	catch (Exception e) {
		
	    System.err.println("Could not resolve client IP: " + e.toString());
	    	
	}  
	return false;
    }

    




    public void addHelpObject(String courseName, String title, String message, String location, String userName, String other){
	try {
	    String clientAddress = RemoteServer.getClientHost();
	    String questionID = Integer.toString(this.globalQuestionID);
	    HelpObject newHelpObject = new HelpObject(courseName, title, message, location, userName, other, clientAddress,questionID);
	    this.globalQuestionID++;
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

    public String helpObjectClaimedID(){
	String clientAddress = "";
	try{
	    clientAddress = RemoteServer.getClientHost();
	} 
	catch (Exception e){
	    System.err.println("Could not resolve client IP: " + e.toString());
	}
	int size = helpList.size();
	for (int i = 0; i < size; i++){
	    if (clientAddress.equals(helpList.get(i).getClientAddress()) && helpList.get(i).isClaimed() && !(helpList.get(i).isPolled())){
		helpList.get(i).setPolledTrue();
		return helpList.get(i).getQuestionID();
	    } 
	}
	return "TEST";
    }
  
    public String printSizeHelpList(){
	return ("number of items in the list: " + helpList.size());
    }
        
    public String printExtendedInfo(int index) {
	return this.helpList.get(index).extendedInfoString();
    }
  
    public String printExtendedInfoID(String questionID){
	for (int i = 0; i < this.helpList.size(); i++){
	    if (this.helpList.get(i).getQuestionID().equals(questionID)){
		return(this.helpList.get(i).extendedInfoString());
	    }
	}
	return("This question does not exist anymore, it has been deleted or claimed");
    }
  
  
    public String printHelpList() {
	String printedList = "";
	for (int i = 0; i < this.helpList.size(); i++){
	    
	    printedList =  (printedList + this.helpList.get(i).basicInfoString() + "\n");
	}
	return printedList;
    }

    public String printNotClaimedList(){
	String printedList = "";
	for (int i = 0; i < this.helpList.size(); i++){
	    if(!(this.helpList.get(i).isClaimed())){
		printedList =  (printedList + this.helpList.get(i).basicInfoString() + "\n");
	    }
	}
	return printedList;
    }
    
    public String printOwnQuestionsOnly(){
	String address = "";
	String printedList = "";
	try {
	    address = RemoteServer.getClientHost();
	}
	catch (Exception e) {
	    System.err.println("Could not resolve client IP: " + e.toString());
	}
	for (int i = 0; i < this.helpList.size(); i++) {
	    if (address.equals(this.helpList.get(i).getClientAddress())){
		printedList = (printedList + this.helpList.get(i).basicInfoString() + "\n");
	    }
	}
	return printedList;
    }
    
    public String printOwnClaimsOnly(){
    	String address = "";
    	String printedList = "";
    	try {
    	    address = RemoteServer.getClientHost();
    	}
    	catch (Exception e) {
    	    System.err.println("Could not resolve client IP: " + e.toString());
    	}
    	for (int i = 0; i < this.helpList.size(); i++) {
    	    if (address.equals(this.helpList.get(i).getClaimAddress())){
    		printedList = (printedList + this.helpList.get(i).basicInfoString() + "\n");
    	    }
    	}
    	return printedList;
        }
  
    public static void main(String args[]) {
    	
	try {
	    Server server = new Server();
	    Studyhelper stub = (Studyhelper) UnicastRemoteObject.exportObject(server, 0);	                
	    // Bind the remote object's stub in the registry
	    System.out.println("argument: " + args[0]);
	    Registry registry = LocateRegistry.getRegistry((args.length != 0) ? Integer.parseInt(args[0]) : null );
      

	    //System.setProperty("java.rmi.server.hostname", server.SERVER_IP); // simons ville ha denna rad
	    registry.bind("Studyhelper", stub);
	    
	    System.err.println("Server ready");
            
	} catch (Exception e) {
	    System.err.println("Server exception: " + e.toString());
	    e.printStackTrace();
	}
    }
}
