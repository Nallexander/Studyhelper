import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
  String sayHello() throws RemoteException;
  void printSizeHelpList() throws RemoteException;
  void claimHelpObject(int index) throws RemoteException;
  void printHelpList() throws RemoteException;
  void deleteHelpObject(int index, String clientAddress) throws RemoteException;
  void addHelpObject(String courseName, String title, String message, String location, String userName, String other, String clientAddress) throws RemoteException;
      }
