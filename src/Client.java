import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.lang.String;
public class Client {

    private Client() {}
    
    public int getInput() {
    	Scanner scan = new Scanner(System.in);
    	int input = scan.nextInt();
    	return input;
    }
    public void mainMenu(){
    	System.out.print("What do you want to do?\n\n");
    	System.out.println("To choose an option enter the corresponding number");
    	System.out.println("[1] Ask for help");
    	System.out.println("[2] Help someone with a question");
    	System.out.println("[3] Show all unanswered questions ");
    	System.out.println("[4] Remove your Question");
    	System.out.println("[5] Quit");
    }
    	
    	private boolean compareString(String reply, String compareto){
    		return reply.toLowerCase().equals(compareto);
    		
    	}
    
    public void addQuestion(Studyhelper stub){
    	int i = 1;
    	Scanner in = new Scanner(System.in);    	
    	System.out.println("Press q to quit, press u if you want to redo last category")
    	String input;
    	
    	while(i < 8){
    	
    	input = input.toLowerCase();
    	if(!(input.equals("u"))){
    		System.out.println("Question editor, you have entered" + i-1 " of 6 categories");
    	}
    	 if (input.equals("q")){
    		 return  null;
    	 }
    		if ( input.equals("u")){
    		  if (i == 1){ //special fall vi kan inte undoa från första caset
    	      System.out.println("Nothing to undo");
    		  System.out.println("Question editor, you have entered" + i-1 " of 6 categories");
    		  }
    		  else {
    			  i--;
    			  System.out.println("Question editor, you have entered" + i-1 " of 6 categories");
    		 }
    	 }
    		
    switch(i){
    Case 1:
    System.out.println("Enter coursename");	
    String courseName = in.nextLine();
    input = courseName;
    i++;
    break;
    
    Case 2:
    System.out.println("Enter title");	
    String title = in.nextLine();
    input =title;
    i++;
    break;
    Case 3:
    System.out.println("Enter  your question");	
    String question = in.nextLine();
    input = question;
    i++;

    break;
    Case 4:
    System.out.println("Enter location");	
    String location = in.nextLine();
    input =location
    i++;
    break;
    Case 5:
    System.out.println("Enter name (optional), leave empty if you want to be anonymous");	
    String userName = in.nextLine();
    input = userName;
    break:
    Case 6:
    System.out.println("Enter other info you want to share, leave blank if not");	
    String other = in.nextLine();
    input = other;
    i++;
    break;	
    case 7:
    	boolean loop = true;
    	System.out.prinln("you have now entered \n Coursename:" + coursename 
		         "\n Title:" + title 
				"\n Question:" + question 
				"\n Location:" + location 
				"\n Name:" + username 
				"\n Other:" + other "\n");
while(loop == true){
System.out.prinln("Enter 'ok' if you want to send this question in, press q to quit or u to undo");
		String confirmation = in.nextline();
		confirmation = confirmation.toLowerCase();
		input = confirmation;
		if (confirmation.equals("ok")){
			stub.addHelpObject(courseName, title, question, location, username, other);
           System.out.println("Question added");
           return null;
           
		}
		if (confirmation.equals("q")){
			return null;
		}
		if (confirmation.equals("u")){
		loop = false;
		}
				
}
break;
    }
    	 }
    
    }
    public void intface(Studyhelper stub) {
    	boolean should_quit = false;
    	while (!should_quit) {
    		mainMenu();
    		switch(getInput()){
    		case 1: 
    			// TODO: add_question();
    			addQuestion(stub);
    			break;
    		case 2:
    			// TODO: answer_question();
    			System.out.println("You have answered the question! Good job! :)");
    			break;
    		case 3: 
    			// TODO: show_questions();
    			break;
    		case 4:
    			// TODO: remove_question();
    			System.out.println("Your question has now been removed from the system!");
    			break;
    		case 5:
    			// TODO: should_quit = exit();
    			System.out.println("Bye bye! :)");
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
            Hello stub = (Hello) registry.lookup("Hello");
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
