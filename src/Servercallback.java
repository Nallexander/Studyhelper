import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servercallback extends Remote {
  void claimCallback() throws RemoteException;
}
