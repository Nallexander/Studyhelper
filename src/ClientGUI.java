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
    public JButton addQuestion;
    public JButton answerQuestion;
    public JButton showQuestions;
    public JButton removeQuestion;
    public JButton quit;
    public JTextArea aBuffer = new JTextArea(13,37);
    public Studyhelper stub;
    LinkedList<String> notClaimedLList;
    public String operation = "";
    public ClientGUI(Studyhelper stubb){
	super("Studyhelper");
	addQuestion    = new JButton("Add Question");
	answerQuestion = new JButton("Answer Question");
	showQuestions  = new JButton("Show All Questions");
	removeQuestion = new JButton("Remove Question");
	quit           = new JButton("Quit");
	answerQuestion.addActionListener(this);
	addQuestion.addActionListener(this);
	showQuestions.addActionListener(this);
	removeQuestion.addActionListener(this);
	this.stub = stubb;
    }
    public LinkedList<String> cutString(String bigString){
    	String littleString = "";
    	LinkedList<String> linkedL =new LinkedList();
    	
    	int i = 0;
    	int j = 0;
    	while(i + 1 < bigString.length()){
	    if (bigString.charAt(i)=='\n' && bigString.charAt(i+1)==('\n')){
    	    	littleString=bigString.substring(j,i);
    	    	linkedL.add(littleString);
    	    	j = i + 2;
    	    	i = i + 2;
	    }
	    else{
		i++;
	    }
    	}
    	return linkedL;
    }
    
    public String onlyStringID(String str){
	String ID ="";
	String s  = "";
	for (char c : str.toCharArray()) {
	          
	    if (Character.isDigit(c)){
		s  = ""  + c;
		ID = ID  + s;
	    }
	    else{
		return ID;
	    }
	}
	return ID;
    }
    
    public String findIDInString(String theString){
    	int idBegins = 4;
    	String theSubstring = theString.substring(idBegins,theString.length());
    	String foundID = onlyStringID(theSubstring);
    	return foundID;
    }
    
    public void mainmenu(){
    	setLayout(new FlowLayout());
    	add(this.addQuestion);
    	add(this.answerQuestion);
    	add(this.showQuestions);
    	add(this.removeQuestion);
    	add(this.aBuffer);
    }
    
    public void viewTheQuestions(){
    	
	try {
	    String questionList = "";
	    JComboBox<String> questionCombo = new JComboBox<String>();
	    if (this.operation.equals("NOTCLAIMED")){
		questionList = this.stub.printNotClaimedList();
	    }
	    else{
	    	questionList = this.stub.printHelpList();
	    }
	    LinkedList<String> questionsLinkedL=cutString(questionList);
		
	    for(int i = 0;i < questionsLinkedL.size();i++)
		{
		    questionCombo.addItem(questionsLinkedL.get(i));
		}
	    setVisible(true);
	    Object[] message = {
		"Woop",questionCombo
   					
	    };
	    String[] options = new String[] {"Expand", "Claim", "Cancel"};
	    if(this.operation.equals("REMOVE")){
	      options[1] = "Remove";
	    }
	    
	    int option = JOptionPane.showOptionDialog(null, message, "Send question", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[2]);
	    if (option == JOptionPane.YES_OPTION){ 
		int selectedIndex = questionCombo.getSelectedIndex();
		String anotherString =questionsLinkedL.get(selectedIndex);
		String theID = findIDInString(anotherString);
		String expandedQuestion =this.stub.printExtendedInfoID(theID);
		this.aBuffer.setText("");
		this.aBuffer.append(expandedQuestion);
	    }
	    if(option == JOptionPane.NO_OPTION){	
		int selectedIndex = questionCombo.getSelectedIndex();
		String anotherString =questionsLinkedL.get(selectedIndex);
		String theID = findIDInString(anotherString);
		if(this.operation.equals("REMOVE")){
			this.stub.deleteHelpObject(theID);	
    	}
    	else{
		String claimedOrNot = "";
		claimedOrNot = this.stub.claimHelpObject(theID);
		this.aBuffer.setText("");
		this.aBuffer.append(claimedOrNot);  			 
	    }
	    }
	}
    	
	catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	return;
    }
   
    public void questionForm(){
	JTextArea jUsername = new JTextArea();
	JTextArea jCourse   = new JTextArea();
	JTextArea jTitle    = new JTextArea();
	JTextArea jLocation = new JTextArea();
	JTextArea jOther    = new JTextArea(3,10);
	JTextArea jQuestion = new JTextArea(5,20);
		
	Object[] message = {
	    "Username", jUsername,
	    "Course",   jCourse,
	    "Title",    jTitle,
	    "Location", jLocation,
	    "Question", jQuestion,
	    "Other",    jOther
				
	};
	int option = JOptionPane.showConfirmDialog(null, message, "Send question", JOptionPane.OK_CANCEL_OPTION);
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
		
	}
	else {
	    System.out.println("Adding question Canceled");
	}
    }
	
    public static void main(String[] args){
	String host = (args.length < 1) ? null : args[0];
	try {
	    Registry registry = LocateRegistry.getRegistry(host);
	    Studyhelper stubb = (Studyhelper) registry.lookup("Studyhelper");
		    
	    ClientGUI client = new ClientGUI(stubb);
	    client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client.setSize(700,700);
	    client.setVisible(true);
	    client.mainmenu();
	}
	catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	
    }
    
    public void actionPerformed(ActionEvent event){	
	JButton pressed = (JButton)event.getSource();
	if (pressed.equals(addQuestion)){
	    questionForm();
	}
	if(pressed.equals(answerQuestion)){
	    this.operation = "NOTCLAIMED";
	    viewTheQuestions();
	}
	if(pressed.equals(showQuestions)){
	    this.operation = "SHOWALL";
	    viewTheQuestions();
	}
	if(pressed.equals(removeQuestion)){
	    this.operation = "REMOVE";
	    viewTheQuestions();
	}
    }
}

  
