import java.rmi.registry.LocateRegistry;
// private String host = "serverns ip";
import java.rmi.registry.Registry;
import java.lang.String;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.server.RemoteServer;
import java.util.*;


public class Replication  {
    private int numberOfServers = 1;
    private int serverTimeout = 3;
    private LinkedList<Boolean> serverUpList = new LinkedList();
    private LinkedList<Integer> serverTriesList = new LinkedList();
    protected Replication(int numServers, int serverTries) {
	numberOfServers = numServers;
	serverTimeout = serverTries;
	for (int i = 0; i < numberOfServers; i++) {
	    serverUpList.add(true);
	    
	}

	for (int i = 0; i < numberOfServers; i++) {
	    serverTriesList.add(0);
	    
	}
    }
    

    protected void replicatedAddHelpObject(List<Studyhelper> stubList, int method, String courseName, String title, String question, String location, String userName, String other) {
	
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







}
  
