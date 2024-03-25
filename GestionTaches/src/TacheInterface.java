import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface TacheInterface extends Remote {
    void ajouterTache(Tache tache) throws RemoteException;
    void suppTache(int index) throws RemoteException;
    List<Tache> getTaches() throws RemoteException;
}
