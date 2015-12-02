import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
  String sayHello() throws RemoteException;
  void printSizeHelpList() throws RemoteException;
  void claimHelpObject() throws RemoteException;
  void printHelpList() throws RemoteException;
  void deleteHelpObject() throws RemoteException;
  void addHelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress) throws RemoteException;
      }
