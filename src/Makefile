JCC = javac

Compile:
	$(JCC) Client.java Server.java Studyhelper.java HelpObject.java ClientGUI.java Replication.java ClientThread.java

Server:
	make Compile
	rmiregistry &
	java -Djava.rmi.server.codebase=file: Server &
