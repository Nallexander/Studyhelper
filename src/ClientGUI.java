import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.border.*;



public class ClientGUI extends JFrame implements ActionListener{
	private int numberOfQuestions =0;
	private int serverTries = 3;
    private int numberOfServers = 1;
    public JButton addQuestion;
    public JButton answerQuestion;
    public JButton showQuestions;
    public JButton removeQuestion;
    public JButton quit;
    public Replication servers = new Replication(numberOfServers, serverTries);
    public JTextArea aBuffer = new JTextArea(13,37);
    public LinkedList<Studyhelper> theStubList;
    LinkedList<String> notClaimedLList;
    public String operation = "";
    
    
    public ClientGUI(LinkedList<Studyhelper> stubb){
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
	this.aBuffer.append("Weolcome to StudyHelper, what do you want to do?\n Press the corresponding button above ");
	this.theStubList = stubb;
    }
    public synchronized int getNumberOfQuestions(){
    	return this.numberOfQuestions;
        }
        public synchronized void decrementNumberOfQuestions(){
    	this.numberOfQuestions = this.numberOfQuestions - 1;
        }

        public synchronized void incrementNumberOfQuestions(){
    	this.numberOfQuestions = this.numberOfQuestions + 1;
        }
        
     public void getClaimedIDFromThread(String clientID){
    	 this.aBuffer.append(clientID);
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
	    String title = "";
	    JComboBox<String> questionCombo = new JComboBox<String>();
	    if (this.operation.equals("NOTCLAIMED")){
		questionList = servers.replicatedPrintNotClaimedList(theStubList);
	    }
	    else{
	    	questionList = servers.replicatedPrintHelpList(theStubList);
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
	    title = "Select question to view or claim";
	    if(this.operation.equals("REMOVE")){
	    	title = "Select question to view or remove";
	      options[1] = "Remove";
	    }
	    
	    int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[2]);
	    if (option == JOptionPane.YES_OPTION){ 
		int selectedIndex = questionCombo.getSelectedIndex();
		String anotherString =questionsLinkedL.get(selectedIndex);
		String theID = findIDInString(anotherString);
		String expandedQuestion =servers.replicatedPrintExtendedInfoID(theStubList, theID);
		this.aBuffer.setText("");
		this.aBuffer.append(expandedQuestion);
	    }
	    if(option == JOptionPane.NO_OPTION){	
		int selectedIndex = questionCombo.getSelectedIndex();
		String anotherString =questionsLinkedL.get(selectedIndex);
		String theID = findIDInString(anotherString);
		if(this.operation.equals("REMOVE")){
			servers.replicatedDeleteHelpObject(theStubList,theID);	
    	}
    	else{
		String claimedOrNot = "";
		claimedOrNot = servers.replicatedClaimHelpObject(theStubList,theID);
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
	JTextArea jUsername = new JTextArea(1,1);
	enableNormalTabbing(jUsername);
	JTextArea jCourse   = new JTextArea(1,1);
	enableNormalTabbing(jCourse);
	JTextArea jTitle    = new JTextArea(1,1);
	enableNormalTabbing(jTitle);
	JTextArea jLocation = new JTextArea(1,1);
	enableNormalTabbing(jLocation);
	JTextArea jOther    = new JTextArea(3,1);
	enableNormalTabbing(jOther);
	JTextArea jQuestion = new JTextArea(5,1);
	enableNormalTabbing(jQuestion);
	 Object paneBG = UIManager.get("OptionPane.background");
	    Object panelBG = UIManager.get("Panel.background");
	    UIManager.put("OptionPane.background", new Color(0,200,0));
	    UIManager.put("Panel.background", new Color(0,200,0));
		
	Object[] message = {
	    "Username", jUsername,
	    "Course",   jCourse,
	    "Title",    jTitle,
	    "Location", jLocation,
	    "Question", jQuestion,
	    "Other",    jOther
				
	};
	int option = JOptionPane.showConfirmDialog(null, message,"Send question", JOptionPane.OK_CANCEL_OPTION);
	UIManager.put("OptionPane.background", paneBG);
    UIManager.put("Panel.background", panelBG);
	if (option == JOptionPane.OK_OPTION) {
	    String courseName=jCourse.getText();
	    String title=jTitle.getText();
	    String question=jQuestion.getText();
	    String location=jLocation.getText();
	    String username=jUsername.getText();
	    String other=jOther.getText();
	    try{
                servers.replicatedAddHelpObject(theStubList,1,courseName, title, question, location, username, other);
                incrementNumberOfQuestions();
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
    	LinkedList<Studyhelper> stubList = new LinkedList();
    	try {
    	    if (args.length == 0) { //No argument given
    		Registry registry = LocateRegistry.getRegistry();
    		stubList.add((Studyhelper) registry.lookup("Studyhelper"));
    		//Thread thread = new Thread(new ClientThread(client, (Studyhelper) stubList.get(0)));
    		//thread.start();	 
    		ClientGUI client =new ClientGUI(stubList);
    		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    client.setSize(700,700);
    	    client.setVisible(true);
    	    client.mainmenu();
    	    Thread thread = new Thread(new ClientGUIThread(client, (Studyhelper) stubList.get(0)));
    		thread.start();	 

    	    }
    	
    	    else if (args.length == 1){ //One  argument given
    		String host = args[0];
    		Registry registry = LocateRegistry.getRegistry(host);   
    		stubList.add((Studyhelper) registry.lookup("Studyhelper"));
    		ClientGUI client =new ClientGUI(stubList);
    		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    client.setSize(700,700);
    	    client.setVisible(true);
    	    client.mainmenu();
    	    Thread thread = new Thread(new ClientGUIThread(client, (Studyhelper) stubList.get(0)));
    		thread.start();	 


    	    }
    	    System.out.println("Before args.length > 1");
    	    if (args.length > 1) { //More than one argument given
    	    

    		for (int i = 0; i < args.length; i = i+2) {
    		    System.out.println("for");
    		    Registry registry = LocateRegistry.getRegistry(args[i], Integer.parseInt(args[i+1])); 
    		    stubList.add((Studyhelper) registry.lookup("Studyhelper"));
    		}
    		ClientGUI client =new ClientGUI(stubList);
    		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	    client.setSize(700,700);
    	    client.setVisible(true);
    	    client.mainmenu();
    	    Thread thread = new Thread(new ClientGUIThread(client, (Studyhelper) stubList.get(0)));
    		thread.start();	 


    		
    		
    	    }

    	} catch (Exception e) {
    	    System.err.println("Client exception: " + e.toString());
    	    e.printStackTrace();
    	}
/*try {
		Stublist leStub = new Stublist(args);
	    ClientGUI client = new ClientGUI(leStub.list);
	    client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    client.setSize(700,700);
	    client.setVisible(true);
	    client.mainmenu();
	}
	catch (Exception e) {
	    System.err.println("Client exception: " + e.toString());
	    e.printStackTrace();
	}
	*/
    }
    public void enableNormalTabbing(Component component){
    	component.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS,null);
    	component.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS,null);
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

  
