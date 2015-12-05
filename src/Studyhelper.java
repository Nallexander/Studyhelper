import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Studyhelper extends Remote {
    String sayHello() throws RemoteException;
    String printSizeHelpList() throws RemoteException;
    void claimHelpObject(String questionID) throws RemoteException;
    void deleteHelpObject(int index, String clientAddress) throws RemoteException;
    void addHelpObject(String courseName, String title, String message, String location, String userName, String other) throws RemoteException;
    String printExtendedInfo(int index) throws RemoteException;
    String printHelpList() throws RemoteException;
}
