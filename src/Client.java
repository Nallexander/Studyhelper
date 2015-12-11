import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.util.*;
import java.lang.*;


public class Client {
    private int serverTries = 3;
    private int numberOfServers = 1; //TODO
    private int numberOfQuestions;

  
    private Client(int numServers) {

	this.numberOfServers = numServers;
	this.numberOfQuestions = 0;
    }
    private Replication servers = new Replication(this.numberOfServers, this.serverTries);



    public synchronized int getNumberOfQuestions(){
	return this.numberOfQuestions;
    }
    public synchronized void decrementNumberOfQuestions(){
	this.numberOfQuestions = this.numberOfQuestions - 1;
    }

    public synchronized void incrementNumberOfQuestions(){
	this.numberOfQuestions = this.numberOfQuestions + 1;
    }

  
  
    public boolean isNumeric(String str){
	for (char c : str.toCharArray()) {
          
	    if (!Character.isDigit(c)){
		return false;
	    }
	}
	return true;
    }
    public int getInput() {
	Scanner scan = new Scanner(System.in);
	String  stringin=scan.nextLine();
	if (isNumeric(stringin)){
	    int input = Integer.parseInt(stringin);
	    return input ;
	}
	else {
	    return 0;
	}
    }
  
    public void mainMenu(){
	System.out.print("What do you want to do?\n\n");
	System.out.println("To choose an option enter the corresponding number");
	System.out.println("[1] Ask for help");
	System.out.println("[2] Help someone with a question");
	System.out.println("[3] Show all questions ");
	System.out.println("[4] Remove your Question");
	System.out.println("[5] Quit");
    }

  
    private boolean compareString(String reply, String compareTo){
	return reply.toLowerCase().equals(compareTo);
    		
    }

    /*    
	  private void stubsMethod(Studyhelper stub1, Studyhelper stub2, Studyhelper stub3, int method, String courseName, String title, String question, String location, String userName, String other) {
	  int stub1Tries = 0;
	  int stub2Tries = 0;
	  int stub3Tries = 0;


	  if (server1Active == true) {
	  while (stub1Tries < stubTries) {
	  try{
	  stub1.addHelpObject(courseName, title, question, location, userName, other);
	  stub1Tries = stubTries + 1;
	  }
	  catch (Exception e) {
	  stub1Tries++;
	  }
	  }
	  if (stub1Tries == stubTries) {
	  server1Active = false;
	  }
	  }

	  if (server2Active == true) {
	  while (stub2Tries < stubTries) {
	  try{
	  stub2.addHelpObject(courseName, title, question, location, userName, other);
	  stub2Tries = stubTries + 1;
	  }
	  catch (Exception e) {
	  stub2Tries++;
	  }
	  }
	  if (stub2Tries == stubTries) {
	  server2Active = false;
	  }
	  }

	  if (server3Active == true) {
	  while (stub3Tries < stubTries) {
	  try{
	  stub3.addHelpObject(courseName, title, question, location, userName, other);
	  stub3Tries = stubTries + 1;
	  }
	  catch (Exception e) {
	  stub3Tries++;
	  }
	  }
	  if (stub3Tries == stubTries) {
	  server3Active = false;
	  }
	  }




	  }
    
    */  
    public void addQuestion(LinkedList<Studyhelper> stubList){
	int i = 1;
	int j;
	Scanner in = new Scanner(System.in);    	
	System.out.println("Press q to quit, press u if you want to redo last category");
	String input = "initialize";
	String courseName = "";
	String title = "";
	String question = "";
	String location = "";
	String userName = "";
	String other = "";
    	
	while(i < 9 && i >=0){
    	
	    input = input.toLowerCase();
	    if(!(input.equals("u"))){
		j = i-1;
		System.out.println("\nQuestion editor, you first have entered " + j + " of 6 categories");
	    }
	    if (input.equals("q")){
		return;
	    }
	    if (input.equals("u")){
		if (i <= 2){ //special fall vi kan inte undoa fran forsta caset
		    System.out.println("Nothing to undo");
		    i = i - 1;
		    System.out.println("\nQuestion editor, you second have entered " + 0 + " of 6 categories");
		}
		else {
		    i = i - 2;
		    j = i - 1;
		    System.out.println("\nQuestion editor, you third have entered " + j + " of 6 categories");
		}
	    }
    		
	    switch(i){
	    case 1:
		System.out.println("Enter coursename");	
		courseName = in.nextLine();
		input = courseName;
		i++;
		break;
    
	    case 2:
		System.out.println("Enter title");	
		title = in.nextLine();
		input = title;
		i++;
		break;
	    case 3:
		System.out.println("Enter your question");	
		question = in.nextLine();
		input = question;
		i++;
		break;
	    case 4:
		System.out.println("Enter location");	
		location = in.nextLine();
		input = location;
		i++;
		break;
	    case 5:
		System.out.println("Enter name (optional), leave empty if you want to be anonymous");	
		userName = in.nextLine();
		input = userName;
		i++;
		break;
	    case 6:
		System.out.println("Enter other info you want to share, leave blank if not");	
		other = in.nextLine();
		input = other;
		i++;
		break;	
	    case 7:
		i++;
		boolean loop = true;
		System.out.println("You have now entered \n Coursename:" + courseName +
				   "\n Title:" + title +
				   "\n Question:" + question + 
				   "\n Location:" + location +
				   "\n Name:" + userName +
				   "\n Other:" + other +"\n");
		while(loop == true){
		    System.out.println("Enter 'ok' if you want to send this question in, press q to quit or u to undo");
		    String confirmation = in.nextLine();
		    confirmation = confirmation.toLowerCase();
		    input = confirmation;
		    if (confirmation.equals("ok")){
			try{
			    //stub.addHelpObject(courseName, title, question, location, userName, other);
			    
			    servers.replicatedAddHelpObject(stubList, 1, courseName, title, question, location, userName, other);
			} catch (Exception e) {
			    System.err.println("Client exception: " + e.toString());
			    e.printStackTrace();
			}
                        
			System.out.println("Question added, please wait for someone to claim your question");
                        incrementNumberOfQuestions(); //Client added another question
			return;
           
		    }
		    if (confirmation.equals("q")){
			return;
		    }
		    if (confirmation.equals("u")){
			loop = false;
		    }
				
		}
		break;
	    }
	}
    
    }

    public static boolean isInteger(String str) {
	if (str == null) {
	    return false;
	}
	int length = str.length();
	if (length == 0) {
	    return false;
	}
	int i = 0;
	if (str.charAt(0) == '-') {
	    if (length == 1) {
		return false;
	    }
	    i = 1;
	}
	for (; i < length; i++) {
	    char c = str.charAt(i);
	    if (c < '0' || c > '9') {
		return false;
	    }
	}
	return true;
    }
    public void HelpListOptions(LinkedList<Studyhelper> stubList, boolean show, boolean delete, boolean claim){
	Scanner in = new Scanner(System.in);    	
	String helpList = "";
	String notClaimedList="";

	while(true){
	    System.out.println("Press 'b' to go back");
	    if(show==true){System.out.println("Press corresponding number to show extended info");}
	    if(delete == true){System.out.println("Press corresponding number to delete your help request");}
	    if(claim ==true){System.out.println("Press corresponding number to claim help request");}
	    try{  
		notClaimedList = servers.replicatedPrintNotClaimedList(stubList);
		helpList = servers.replicatedPrintHelpList(stubList);
	    } catch (Exception e) {
		System.err.println("Client exception: " + e.toString());
		e.printStackTrace();
	    }
	    if ((show == true) || (delete == true)){
		System.out.print(helpList);
	    }
	    if (claim==true){
		System.out.print(notClaimedList);
	    }

	    String input = in.nextLine();

	    if(isInteger(input)){
		int intPut = Integer.parseInt(input);
		String info = "";
		String claimedOrNot="";
		if(show==true){
		    try{
			info = servers.replicatedPrintExtendedInfoID(stubList, input);
		    } catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		    }
		    System.out.print(info);
		}
		if(delete == true){
		    String temp = ""; // change this later if we can use clientaddress as argument or not
		    try{
			servers.replicatedDeleteHelpObject(stubList, input);
		    } catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		    }
   
		}
		if(claim ==true){
		    try{
			claimedOrNot = servers.replicatedClaimHelpObject(stubList, input);
		    } catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		    }
		    System.out.print(claimedOrNot);
		}
	    }
	    if (input.equals("b")) {
		return;  
	    }
	    else
		System.out.println("Invalid operation, please try again");
	}
 
    }
  
    public void intface(LinkedList<Studyhelper> stubList) {
	boolean should_quit = false;
	while (!should_quit) {
	    mainMenu();
	    switch(getInput()){
	    case 1: 
		addQuestion(stubList);
		break;
	    case 2:
		//  answer_question
		HelpListOptions(stubList, false, false, true);
		//System.out.println("You have answered the question! Good job! :)");
		break;
	    case 3:
		// show list
		HelpListOptions(stubList, true, false, false);
		break;
	    case 4:
		HelpListOptions(stubList, false, true, false);
		System.out.println("Your question has now been removed from the system!");
		break;
	    case 5:
		// TODO: should_quit = exit();
		System.out.println("Bye bye! :)");
		System.exit(0);
		break;
	    default:
		System.out.println("Your choice could not be parsed, please try again! :)");
	    }
	}	
    }

    public static void main(String[] args) {
	LinkedList<Studyhelper> stubList = new LinkedList();
	Client client  = new Client(args.length/2);
	client.servers.updateReplicas(client.numberOfServers, client.serverTries);

	try {
	    if (args.length == 0) { //No argument given
		Registry registry = LocateRegistry.getRegistry();
		stubList.add((Studyhelper) registry.lookup("Studyhelper"));
		LinkedList<Thread> threadList = client.servers.replicatedNewThread(client, stubList);
		client.servers.replicatedThreadStart(threadList);
		client.intface(stubList);

	    }
	
	    else if (args.length == 1){ //One  argument given
		String host = args[0];
		Registry registry = LocateRegistry.getRegistry(host);   
		stubList.add((Studyhelper) registry.lookup("Studyhelper"));
		LinkedList<Thread> threadList = client.servers.replicatedNewThread(client, stubList);
		client.servers.replicatedThreadStart(threadList);
		client.intface(stubList);

	    }
	    System.out.println("Before args.length > 1");
	    if (args.length > 1) { //More than one argument given
	    

		for (int i = 0; i < args.length; i = i+2) {
		    System.out.println("for");
		    Registry registry = LocateRegistry.getRegistry(args[i], Integer.parseInt(args[i+1])); 
		    stubList.add((Studyhelper) registry.lookup("Studyhelper"));
		}
		LinkedList<Thread> threadList = client.servers.replicatedNewThread(client, stubList);
		client.servers.replicatedThreadStart(threadList);
		client.intface(stubList);

		
		
	    }

	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}

	

	
    }
  

}
