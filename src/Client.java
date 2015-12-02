import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {

    private Client() {}
    
    public int get_input() {
    	Scanner scan = new Scanner(System.in);
    	int input = scan.nextInt();
    	return input
    }
    
    public void intface() {
    	boolean should_quit = false;
    	while (!should_quit) {
    		switch(get_input()){
    		case 1: 
    			// TODO: add_question();
    			System.out.prinln("Your question is now added to the system! Yaaay! :)");
    			break;
    		case 2:
    			// TODO: answer_question();
    			System.out.prinln("You have answered the question! Good job! :)");
    			break;
    		case 3: 
    			// TODO: show_questions();
    			break;
    		case 4:
    			// TODO: remove_question();
    			System.out.prinln("Your question has now been removed from the system!");
    			break;
    		case 5:
    			// TODO: should_quit = exit();
    			System.out.prinln("Bye bye! :)");
    			break;
    		default:
    			System.out.prinln("Your choice could not be parsed, please try again! :)");
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
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }	
    }
}