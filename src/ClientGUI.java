import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.lang.String;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;




public class ClientGUI extends JFrame{
   
    ClientGUI(Studyhelper stub){
	super("Studyhelper");
	mainmenu(stub);
    }
	
    public void mainmenu(Studyhelper stub){
	HandlerClass handler = new HandlerClass(stub);
	JButton addQuestion;
	JButton answerQuestion;
	JButton showQuestions;
	JButton removeQuestion;
	JButton quit;
	setLayout(new FlowLayout());
	addQuestion=new JButton("Add question");
	add(addQuestion);
	addQuestion.addActionListener(handler);
    }
    public void questionForm(Studyhelper stub){
	JTextField jUsername = new JTextField();
	JTextField jCourse = new JTextField();
	JTextField jTitle = new JTextField();
	JTextField jLocation = new JTextField();
	JTextArea jOther  = new JTextArea(3,10);
	JTextArea jQuestion = new JTextArea(5,20);
		
	Object[] message = {
	    "Username", jUsername,
	    "Course",jCourse,
	    "Title",jTitle,
	    "Location",jLocation,
	    "Question",jQuestion,
	    "Other",jOther
				
	};
	int option= JOptionPane.showConfirmDialog(null, message, "Send question", JOptionPane.OK_CANCEL_OPTION);
	if (option == JOptionPane.OK_OPTION) {
	    String courseName=jCourse.getText();
	    String title=jTitle.getText();
	    String question=jQuestion.getText();
	    String location=jLocation.getText();
	    String username=jUsername.getText();
	    String other=jOther.getText();
	    try{
                stub.addHelpObject(courseName, title, question, location, username, other);
	    } catch (Exception e) {
                System.err.println("Client exception: " + e.toString());
                e.printStackTrace();
	    }
			
	    //System.out.println(question.getText());
	}
	else {
	    System.out.println("No question added");
	}
	//String course =JOptionPane.showInputDialog("Enter coursename");
	//String title =JOptionPane.showInputDialog("Enter question title");
		
    }
	
    public static void main(String[] args){
	String host = (args.length < 1) ? null : args[0];
	try {
	    Registry registry = LocateRegistry.getRegistry(host);
	    Studyhelper stub = (Studyhelper) registry.lookup("Studyhelper");
		    
	    ClientGUI client = new ClientGUI(stub);
	    client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client.setSize(300,200);
	    client.setVisible(true);
	}
	catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	//client.mainmenu();
	//client.questionForm();
    }
   public class HandlerClass implements ActionListener{
	   public Studyhelper stubby;
	   public HandlerClass(Studyhelper stub){
	   this.stubby=stub;
	   }
public void actionPerformed(ActionEvent event){	
	   System.out.println("Nothing");
	   questionForm(this.stubby);
}
	    //questionForm(stub);
	    //JOptionPane.showMessageDialog(null, "yolo");
	//}
  }
}

  
