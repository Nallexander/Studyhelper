import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.lang.String;

import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import java.util.LinkedList;
import javax.swing.JComboBox;



public class ClientGUI extends JFrame implements ActionListener{
    final JButton addQuestion;
    final JButton answerQuestion;
    final JButton showQuestions;
    final JButton removeQuestion;
    final JButton quit;
    public Studyhelper stub;
    LinkedList<String> notClaimedLList;
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
	//mainmenu();
    }
    public LinkedList<String> cutString(String bigString){
    	String littleString="";
    	LinkedList<String> linkedL =new LinkedList();
    	
    	int i =0;
    	int j=0;
    	while(i + 1 < bigString.length()){
	    if (bigString.charAt(i)=='\n' && bigString.charAt(i+1)==('\n')){
    	    	littleString=bigString.substring(j,i);
    	    	linkedL.add(littleString);
    	    	j=i+2;
    	    	i=i+2;
	    }
	    else{
		i++;
	    }
    	}
    	return linkedL;
    }
    
    public void viewNotClaimedQuestionsp(){
    	setLayout(new FlowLayout());
    	JButton test = new JButton("Test");
    	JLabel label = new JLabel("hello");
        label.setSize(150,30);
        label.setLocation(0,0);
        JTextField textField = new JTextField();
        add(test);
        add(textField);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }
    public void expandNotClaimedQuestions(){
     
    }
    public void viewNotClaimedQuestions(){
    	
	try {
		String notClaimedList = "";
		JComboBox<String> optionCombo = new JComboBox<String>();
		JComboBox<String> questionCombo = new JComboBox<String>();
		/*
		int textX = 100;
		int textY = 50;
		int textWidth = 200;
		int textHeight = 30;
		int buttonX = 20;
		int buttonY = 50;
		int buttonWidth = 50;
		int buttonHeight = 30;
		*/
    	//JTextArea[] allTextArea;
    	//JButton[] allButtons;
    	notClaimedList= this.stub.printNotClaimedList();
	    LinkedList<String> linkedL2=cutString(notClaimedList);
	    //allTextArea = new JTextArea[linkedL2.size()];
	    //allButtons = new JButton[linkedL2.size()];
	    
	    
		
	  for(int i = 0;i < linkedL2.size();i++)
	    {
		questionCombo.addItem(linkedL2.get(i));
		/*
		allButtons[i]= new JButton("Claim");
		allTextArea[i] = new JTextArea();
		allTextArea[i].setBounds(textX,textY,textWidth,textHeight);
		allButtons[i].setBounds(buttonX,buttonY,buttonWidth,buttonHeight);
		allTextArea[i].append(linkedL2.get(i));
		allTextArea[i].setEditable(false);
		add(allButtons[i]);
		add(allTextArea[i]);
    	textY = textY + textHeight + 5;
    	buttonY = buttonY + buttonHeight + 5;
    	*/
    	
	    }
	    optionCombo.addItem("Claim");
	    optionCombo.addItem("Expand");
	    setVisible(true);
	Object[] message = {
   		   "Woop",questionCombo,optionCombo
   					
   		};
   		int option= JOptionPane.showConfirmDialog(null, message, "Send question", JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE);
   		
	}
    	
    catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	return;
    }
public void viewNotClaimedQuestionsFul(){
    	try{ String notClaimedList = "";
    	notClaimedList= this.stub.printNotClaimedList();
    	JTextArea jClaim = new JTextArea(10,10);
    	Object[] message = {
    		   "Woop",jClaim
    					
    		};
    		int option= JOptionPane.showConfirmDialog(null, message, "Send question", JOptionPane.OK_CANCEL_OPTION);
    	//jClaim.append(notClaimedList);
    	
    	}
    	catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
    }
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
	    client.setSize(800,800);
	    client.setVisible(true);
	    client.mainmenu();
	}
	catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	
	//client.questionForm();
    }
    public void actionPerformed(ActionEvent event){	
	JButton pressed = (JButton)event.getSource();
	if (pressed.equals(addQuestion)){
	    questionForm();
	}
	if(pressed.equals(answerQuestion)){
		System.out.println("Irun master");
	    viewNotClaimedQuestions();
	}
	else{
	    System.out.println("Magicarp");
	}
	//questionForm(stub);
	//JOptionPane.showMessageDialog(null, "yolo");
	//}
    }
}

  
