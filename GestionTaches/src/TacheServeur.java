import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class TacheServeur extends UnicastRemoteObject implements TacheInterface {
    private List<Tache> tacheListe;

    protected TacheServeur() throws RemoteException {
        super();
        tacheListe = new ArrayList<>();
    }

    public static void main(String[] args) {
        try {
        	TacheServeur server = new TacheServeur();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("TacheService", server);
            System.out.println("Serveur en marche...");
        } catch (Exception e) {
            System.err.println("Exception serveur : " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void ajouterTache(Tache tache) throws RemoteException {
        tacheListe.add(tache);
        System.out.println("Tache ajoutée: " + tache.getDescription());
    }

    @Override
    public void suppTache(int index) throws RemoteException {
        tacheListe.remove(index);
        System.out.println("Tache supprimée: ");

        for (int i = 0; i < tacheListe.size(); i++) {
            System.out.println(i + ". " + tacheListe.get(i).getDescription());
        }
    }

    @Override
    public List<Tache> getTaches() throws RemoteException {
        return tacheListe;
    }
}
