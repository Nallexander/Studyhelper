import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.lang.String;
public class Client {

    private Client() {}
    private int stubTries = 3;
    private boolean server1Active = true;
    private boolean server2Active = true;
    private boolean server3Active = true;

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
	return reply.toLowerCase().equals(compareto);
    		
    }

    /*
    private void stubsVoidFunction(stub stub1, stub stub2, stub stub3, void function) {
        int stub1Tries = 0;
	int stub2Tries = 0;
        int stub3Tries = 0;
	try{
	    while (stub1Tries < stubTries) {
		stub1.function();
	    }
	} catch (Exception e) {
	    stub1Tries++;
	}
	if (stub1Tries == stubTries) {
	    server1Active = false;
	} 
    }
    */
    
    public void addQuestion(Studyhelper stub){
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
			    stub.addHelpObject(courseName, title, question, location, userName, other);
			} catch (Exception e) {
			    System.err.println("Client exception: " + e.toString());
			    e.printStackTrace();
			}
			System.out.println("Question added");
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
    public void HelpListOptions(Studyhelper stub, boolean show, boolean delete, boolean claim){
	Scanner in = new Scanner(System.in);    	
	String helpList = "";
	String notClaimedList="";

	while(true){
	    System.out.println("Press 'b' to go back");
	    if(show==true){System.out.println("Press corresponding number to show extended info");}
	    if(delete == true){System.out.println("Press corresponding number to delete your help request");}
	    if(claim ==true){System.out.println("Press corresponding number to claim help request");}
	    try{  
		notClaimedList = stub.printNotClaimedList();
		helpList = stub.printHelpList();
	    } catch (Exception e) {
		System.err.println("Client exception: " + e.toString());
		e.printStackTrace();
	    }
	    if (show == true){
		System.out.print(helpList);
	    }
	    if(claim==true){
		System.out.print(notClaimedList);
	    }
	    String input = in.nextLine();

	    if(isInteger(input)){
		int intPut = Integer.parseInt(input);
		String info = "";
		String claimedOrNot="";
		if(show==true){
		    try{
			info = stub.printExtendedInfo(intPut);
		    } catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		    }
		    System.out.print(info);
		}
		if(delete == true){
		    String temp = ""; // change this later if we can use clientaddress as argument or not
		    try{
			stub.deleteHelpObject(intPut, temp);
		    } catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		    }
   
		}
		if(claim ==true){
		    try{
			claimedOrNot=stub.claimHelpObject(input);
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
  
    public void intface(Studyhelper stub) {
	boolean should_quit = false;
	while (!should_quit) {
	    mainMenu();
	    switch(getInput()){
	    case 1: 
		addQuestion(stub);
		break;
	    case 2:
		//  answer_question
		HelpListOptions(stub, false, false, true);
		//System.out.println("You have answered the question! Good job! :)");
		break;
	    case 3:
		// show list
		HelpListOptions(stub, true, false, false);
		break;
	    case 4:
		HelpListOptions(stub, false, true, false);
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

	String host = (args.length < 1) ? null : args[0];
	try {
	    Registry registry = LocateRegistry.getRegistry(host);
	    Studyhelper stub = (Studyhelper) registry.lookup("Studyhelper");
	    Client client  = new Client();
	    client.intface(stub);
	    String response = stub.sayHello();
	    System.out.println("response: " + response);
	    stub.printSizeHelpList();
	    String sizeHelpList = stub.printSizeHelpList();
	    System.out.println("Size of helpList: " + sizeHelpList);
	} catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
    }
  


}
