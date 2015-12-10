JCC = javac

compile:
	$(JCC) Client.java Server.java Studyhelper.java HelpObject.java ClientGUI.java Replication.java ClientThread.java

server: compile
	rmiregistry &
	java -Djava.rmi.server.codebase=file: Server &
