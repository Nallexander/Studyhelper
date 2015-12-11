import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.lang.String;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteServer;
import java.util.*;


public class Replication  {
    protected int numberOfServers = 1;
    private int serverTimeout = 3;
    protected LinkedList<Boolean> serverUpList = new LinkedList();
    private LinkedList<Integer> serverTriesList = new LinkedList();
    protected Replication(int numServers, int serverTries) {
	this.numberOfServers = numServers;
	this.serverTimeout = serverTries;

    }

    protected void updateReplicas(int numServers, int serverTries) {
	this.numberOfServers = numServers;
	this.serverTimeout = serverTries;
	for (int i = 0; i < this.numberOfServers; i++) {
	    serverUpList.add(true);
	    
	}

	for (int i = 0; i < numberOfServers; i++) {
	    serverTriesList.add(0);
	    
	}
    }
    
    protected void setTriesToZero() {
	for (int i = 0; i < serverTriesList.size(); i++) {
	    serverTriesList.set(i, 0);
	}
    }

    protected void replicatedAddHelpObject(List<Studyhelper> stubList, int method, String courseName, String title, String question, String location, String userName, String other) {
	setTriesToZero();
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			stubList.get(i).addHelpObject(courseName, title, question, location, userName, other);
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
    }

    protected boolean replicatedDeleteHelpObject(List<Studyhelper> stubList, String questionID) {
	setTriesToZero();
	boolean return_bool = false;
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_bool = stubList.get(i).deleteHelpObject(questionID);
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_bool;
    }

    protected String replicatedClaimHelpObject(List<Studyhelper> stubList, String questionID) {
	setTriesToZero();
	String return_stri = "";
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_stri = stubList.get(i).claimHelpObject(questionID);
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_stri;
    }

    protected String replicatedPrintHelpList(List<Studyhelper> stubList) {
	setTriesToZero();
	String return_stri = "Error";
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_stri = stubList.get(i).printHelpList();
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_stri;
    }

    protected String replicatedPrintNotClaimedList(List<Studyhelper> stubList) {
	setTriesToZero();	
	String return_stri = "";
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_stri = stubList.get(i).printNotClaimedList();
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_stri;
    }

    protected String replicatedPrintExtendedInfoID(List<Studyhelper> stubList, String ID) {
	setTriesToZero();
	String return_stri = "";
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_stri = stubList.get(i).printExtendedInfoID(ID);
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_stri;
    }

    protected String replicatedPrintOwnQuestionsOnly(List<Studyhelper> stubList) {
	setTriesToZero();	
	String return_stri = "";
	for (int i = 0; i < this.serverUpList.size(); i++) {
	    if (this.serverUpList.get(i) == true) {
		while (this.serverTriesList.get(i) < serverTimeout) {
		    try{
			return_stri = stubList.get(i).printOwnQuestionsOnly();
			this.serverTriesList.set(i, serverTimeout + 1);
		    }
		    catch (Exception e) {
			this.serverTriesList.set(i, (this.serverTriesList.get(i) + 1));
		    }
		}
		if (this.serverTriesList.get(i) == serverTimeout) {
		    this.serverUpList.set(i, false);
		}
	    }
	}
	return return_stri;
    }

    protected LinkedList<Thread> replicatedNewThread(Client client, LinkedList<Studyhelper> stubList) {
	LinkedList<Thread> threadList = new LinkedList();
	for (int i = 0; i < stubList.size(); i++) {
	    threadList.add(new Thread(new ClientThread(client, (Studyhelper) stubList.get(i))));     
	}

	return threadList;
    }


    protected void replicatedThreadStart(LinkedList<Thread> threadList) {
	for (int i = 0; i < threadList.size(); i++) {
	    threadList.get(i).start();
	}
    }


    
}
  
