import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.lang.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.border.*;



public class ClientGUI extends JFrame implements ActionListener{
	private int        numberOfQuestions = 0;
	private int        serverTries       = 3;
	private int        numberOfServers   = 1;
	protected LinkedList<Boolean> claimedList = new LinkedList();
	private boolean access = true;
	public  JButton    addQuestion;
	public  JButton    answerQuestion;
	public  JButton    showQuestions;
	public  JButton    myClaims;
	public  JButton    myQuestions;
	public  JTextArea  aBuffer = new JTextArea(13,37);
	public  LinkedList <Studyhelper> theStubList;
	public  String     operation  = "";


	public ClientGUI(LinkedList<Studyhelper> stubb, int numServers){
		super("Studyhelper");
		this.numberOfServers = numServers;
		this.numberOfQuestions = 0;
		addQuestion    = new JButton("Add Question");
		answerQuestion = new JButton("Answer Question");
		showQuestions  = new JButton("Show All Questions");
		myQuestions    = new JButton("Show my questions");
		myClaims       = new JButton("My Claims");

		answerQuestion.addActionListener(this);
		addQuestion.addActionListener(this);
		showQuestions.addActionListener(this);
		myQuestions.addActionListener(this);
		myClaims.addActionListener(this);
		this.aBuffer.append("Weolcome to StudyHelper, what do you want to do?\n Press the corresponding button above ");
		this.theStubList = stubb;
	}
	public Replication servers = new Replication(this.numberOfServers, this.serverTries);

	public void updateNumberOfQuestions() {
		this.numberOfQuestions = servers.replicatedGetNumberOfUnclaimedQuestions(this.theStubList);
	}




	public void confirmPopUp(String confirmMessage,String confirmOption) {	

		Object paneBG  = UIManager.get("OptionPane.background");
		Object panelBG = UIManager.get("Panel.background");
		Object[] message0 = {confirmMessage};
		String[] options0 = {"OK"};
		String title ="Pop Up";
		if(confirmOption.equals("CONFIRM")){
			UIManager.put("OptionPane.background", new Color(0,0,200));
			UIManager.put("Panel.background", new Color(0,0,200));
			options0[0] = "OK! :)";
			title     = "Notification";
		}

		else if(confirmOption.equals("EMPTY")){
			UIManager.put("OptionPane.minimumSize",new Dimension(400,100));
			UIManager.put("OptionPane.background", new Color(255,128,0));
			UIManager.put("Panel.background", new Color(255,128,0));
			options0[0] ="OK";
			title     = "Empty";
		}

		else if (confirmOption.equals("FAIL")) {
			UIManager.put("OptionPane.background", new Color(200,0,0));
			UIManager.put("Panel.background", new Color(200,0,0));
			options0[0] = "OK :(";
			title     = "Woops";
		}
		else if (confirmOption.equals("SUCCESS")){
			UIManager.put("OptionPane.background", new Color(0,200,0));
			UIManager.put("Panel.background", new Color(0,200,0));
			options0[0] = "Woho :)";
			title     = "Success";
		}
		setVisible(true);
		int option = JOptionPane.showOptionDialog(null,new JLabel("<html><h2><font color='white'>"+confirmMessage+"</font>"), title,JOptionPane.DEFAULT_OPTION,JOptionPane.PLAIN_MESSAGE,null,options0,options0[0]);
		UIManager.put("OptionPane.background", paneBG);
		UIManager.put("Panel.background", panelBG);
	}




	public synchronized void getAccess(){
		if (this.access) {
			this.access = false;
		}
	}

	public synchronized void releaseAccess(){
		this.access = true;

	}

	public synchronized int getNumberOfQuestions(){
		this.getAccess();
		int numQ = this.numberOfQuestions;
		this.releaseAccess();
		return numQ;
	}
	public synchronized void decrementNumberOfQuestions(){
		this.getAccess();
		this.numberOfQuestions = this.numberOfQuestions - 1;
		this.releaseAccess();
	}

	public synchronized void incrementNumberOfQuestions(){
		this.getAccess();
		this.numberOfQuestions = this.numberOfQuestions + 1;
		this.releaseAccess();
	}

	public LinkedList<String> cutString(String bigString){
		String             littleString = "";
		LinkedList<String> linkedL      = new LinkedList<String>();
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
		String ID = "";
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
		int    idBegins     = 4;
		String theSubstring = theString.substring(idBegins,theString.length());
		String foundID      = onlyStringID(theSubstring);
		return foundID;
	}

	public void mainmenu(){
        updateNumberOfQuestions();
		setLayout(new FlowLayout());
		add(this.addQuestion);
		add(this.answerQuestion);
		add(this.showQuestions);
		add(this.myQuestions);
		add(this.myClaims);
		add(this.aBuffer);
		this.aBuffer.setEditable(false);
	}

	public void viewTheQuestions(){

		try {
			String questionList = "";
			String title        = "";
			int    expand       = 0;
			int    claim        = 1;
			int    remove       = 2;
			int    cancel       = 3;
			int    option       = 3;
			JComboBox<String> questionCombo = new JComboBox<String>();

			if (this.operation.equals("NOTCLAIMED")){
				questionList = servers.replicatedPrintNotClaimedList(theStubList);
				this.aBuffer.setText("");
				this.aBuffer.append(questionList);
			}
			if (this.operation.equals("MYQUESTIONS")){
				questionList = servers.replicatedPrintOwnQuestionsOnly(theStubList);
				this.aBuffer.setText("");
				this.aBuffer.append(questionList);
			}
			if (this.operation.equals("SHOWALL")){
				questionList = servers.replicatedPrintHelpList(theStubList);
				this.aBuffer.setText("");
				this.aBuffer.append(questionList);
			}


			LinkedList<String> questionsLinkedL = cutString(questionList);

			for(int i = 0; i < questionsLinkedL.size();i++)
			{
				questionCombo.addItem(questionsLinkedL.get(i));
			}
			setVisible(true);
			Object[] message  = {"Choose one question from the list",questionCombo};			
			String[] options  = new String[] {"Expand", "Claim","Remove","Cancel"};
			title     = "Select question to view, claim or remove (if the question is yours)";

			if(this.operation.equals("MYQUESTIONS")){
				title = "My questions";

			}


			if (questionCombo.getItemCount() > 0){
				option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[3]);

			}
			if (questionCombo.getItemCount() == 0){
				confirmPopUp("There are no such questions avialable","EMPTY");
			}
			if (option != cancel && questionCombo.getItemCount() > 0 ){

				int    selectedIndex    = questionCombo.getSelectedIndex();
				String anotherString    = questionsLinkedL.get(selectedIndex);
				String theID            = findIDInString(anotherString);

				if (option == expand){ 
					String expandedQuestion =servers.replicatedPrintExtendedInfoID(theStubList, theID);
					this.aBuffer.setText("");
					this.aBuffer.append(expandedQuestion);
				}
				if(option == claim){
					String claimedOrNot = "";
					claimedOrNot = servers.replicatedClaimHelpObject(theStubList,theID);
					if (claimedOrNot.startsWith("You have claimed")){
						confirmPopUp(claimedOrNot,"SUCCESS");

					}
					else{
						confirmPopUp(claimedOrNot,"FAIL");
					}
				}
				if (option==remove){
					servers.replicatedDeleteHelpObject(theStubList,theID);
					decrementNumberOfQuestions();
				}
			}
		}

		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return;
	}


	public void viewOwnClaims(){
		try{

			String questionList = "";
			String title        = "";
			int expand          = 0;
			int cancel          = 1;
			String[] options = new String[] {"Expand","Cancel"};
			String[] options0 = new String[] {"Cancel"};
			JComboBox<String> questionCombo = new JComboBox<String>();
			questionList = servers.replicatedPrintOwnClaimsOnly(theStubList);
			this.aBuffer.setText("");
			this.aBuffer.append(questionList);



			LinkedList<String> questionsLinkedL = cutString(questionList);

			for(int i = 0; i < questionsLinkedL.size();i++)
			{
				questionCombo.addItem(questionsLinkedL.get(i));
			}
			Object[] message  = {"Choose one question from the list",questionCombo};		
			Object[] message0 = {"No claims by you to show"};
			title     = "Your Claims";
			setVisible(true);
			if (questionCombo.getItemCount() > 0){
				int option = JOptionPane.showOptionDialog(null, message, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
				if (option == expand){

					int    selectedIndex    = questionCombo.getSelectedIndex();
					String anotherString    = questionsLinkedL.get(selectedIndex);
					String theID            = findIDInString(anotherString); 
					String expandedQuestion =servers.replicatedPrintExtendedInfoID(theStubList, theID);
					this.aBuffer.setText("");
					this.aBuffer.append(expandedQuestion);
				}
			}
			else {
				confirmPopUp("You have not claimed any of the existing questions","EMPTY");
			}
		}
		catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
		return;
	}

	public void questionForm(){

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		JTextArea jUsername = new JTextArea(1,1);
		JTextArea jCourse   = new JTextArea(1,1);
		JTextArea jTitle    = new JTextArea(1,1);
		JTextArea jLocation = new JTextArea(1,1);
		JTextArea jOther    = new JTextArea(3,1);
		JTextArea jQuestion = new JTextArea(5,1);
		jUsername.setBorder(border);
		jCourse.setBorder(border);
		jTitle.setBorder(border);
		jLocation.setBorder(border);
		jQuestion.setBorder(border);
		jOther.setBorder(border);
		enableNormalTabbing(jUsername);
		enableNormalTabbing(jCourse);
		enableNormalTabbing(jTitle);
		enableNormalTabbing(jLocation);
		enableNormalTabbing(jOther);
		enableNormalTabbing(jQuestion);

		/*Object paneBG  = UIManager.get("OptionPane.background");
	Object panelBG = UIManager.get("Panel.background");
	UIManager.put("OptionPane.background", new Color(0,200,0));
	UIManager.put("Panel.background", new Color(0,200,0));
		 */
		Object[] message = {
				"Username", jUsername,
				"Course",   jCourse,
				"Title",    jTitle,
				"Location", jLocation,
				"Question", jQuestion,
				"Other",    jOther
		};
		UIManager.put("OptionPane.minimumSize",new Dimension(500,500)); 
		int option = JOptionPane.showConfirmDialog(null, message,"Send question", JOptionPane.OK_CANCEL_OPTION);
		/*UIManager.put("OptionPane.background", paneBG);
	UIManager.put("Panel.background", panelBG);
		 */
		if (option == JOptionPane.OK_OPTION) {
			String courseName = jCourse.getText();
			String title      = jTitle.getText();
			String question   = jQuestion.getText();
			String location   = jLocation.getText();
			String username   = jUsername.getText();
			String other      = jOther.getText();
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
		LinkedList<Studyhelper> stubList = new LinkedList<Studyhelper>();
		ClientGUI client =new ClientGUI(stubList,args.length/2);
		try {
			if  (args.length == 0) { //No argument given
				Registry registry = LocateRegistry.getRegistry();
				stubList.add((Studyhelper) registry.lookup("Studyhelper"));
				client.servers.updateReplicas(client.numberOfServers, client.serverTries);
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.setSize(700,700);
				client.setVisible(true);
				client.mainmenu();
				LinkedList<Thread> threadList = client.servers.replicatedNewGUIThread(client, stubList);
				client.servers.replicatedThreadStart(threadList); 

			}

			if  (args.length == 1){ //One  argument given
				String   host       = args[0];
				Registry registry   = LocateRegistry.getRegistry(host);   
				stubList.add((Studyhelper) registry.lookup("Studyhelper"));
				client.servers.updateReplicas(client.numberOfServers, client.serverTries);
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.setSize(700,700);
				client.setVisible(true);
				client.mainmenu();
				LinkedList<Thread> threadList = client.servers.replicatedNewGUIThread(client,stubList);
				client.servers.replicatedThreadStart(threadList); 
			}
			if (args.length > 1) { //More than one argument given


				for (int i = 0; i < args.length; i = i+2) {
					Registry registry = LocateRegistry.getRegistry(args[i], Integer.parseInt(args[i+1])); 
					stubList.add((Studyhelper) registry.lookup("Studyhelper"));
				}
				client.servers.updateReplicas(client.numberOfServers, client.serverTries);
				client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				client.setSize(700,700);
				client.setVisible(true);
				client.mainmenu();
				LinkedList<Thread> threadList = client.servers.replicatedNewGUIThread(client, stubList);
				client.servers.replicatedThreadStart(threadList);
			}

		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
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
		if(pressed.equals(myQuestions)){
			this.operation="MYQUESTIONS";
			viewTheQuestions();
		}
		if(pressed.equals(myClaims)){
			this.operation = "MYCLAIMS";
			viewOwnClaims();
		}
		if(pressed.equals(showQuestions)){
			this.operation = "SHOWALL";
			viewTheQuestions();
		}
	}
}
