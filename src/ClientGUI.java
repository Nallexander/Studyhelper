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




public class ClientGUI extends JFrame implements ActionListener{
	final JButton addQuestion;
	final JButton answerQuestion;
	final JButton showQuestions;
	final JButton removeQuestion;
	final JButton quit;
	public Studyhelper stub;
   
    public ClientGUI(Studyhelper stubb){
    super("Studyhelper");
    addQuestion    = new JButton("Add Question");
    answerQuestion = new JButton("Answer Question");
    showQuestions  = new JButton ("Show All Questions");
    removeQuestion = new JButton ("Remove Question");
    quit           = new JButton ("Quit");
    answerQuestion.addActionListener(this);
	addQuestion.addActionListener(this);
	this.stub = stubb;
	mainmenu();
    }
    public void viewUnansweredQuestions(){
    	
    }
    
	
    public void mainmenu(){
	setLayout(new FlowLayout());
	add(this.addQuestion);
	add(this.answerQuestion);
	
    }
    public void questionForm(){
	JTextArea jUsername = new JTextArea();
	JTextArea jCourse = new JTextArea();
	JTextArea jTitle = new JTextArea();
	JTextArea jLocation = new JTextArea();
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
                this.stub.addHelpObject(courseName, title, question, location, username, other);
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
	    Studyhelper stubb= (Studyhelper) registry.lookup("Studyhelper");
		    
	    ClientGUI client = new ClientGUI(stubb);
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
public void actionPerformed(ActionEvent event){	
	  JButton pressed = (JButton)event.getSource();
	  if (pressed.equals(addQuestion)){
	   questionForm();
}
	  if(pressed.equals(answerQuestion)){
		  System.out.println("Answer question button pressed");
	  }
	  else{
		  System.out.println("Magicarp");
	  }
	    //questionForm(stub);
	    //JOptionPane.showMessageDialog(null, "yolo");
	//}
  }
}

  
