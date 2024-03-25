import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

public class TacheClient {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            TacheInterface tacheService = (TacheInterface) registry.lookup("TacheService");

            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Choisissez une action :");
                System.out.println("1. Ajouter une tâche");
                System.out.println("2. Supprimer une tâche");
                System.out.println("3. Récupérer toutes les tâches");
                System.out.println("4. Quitter");

                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Entrez la description de la tâche à ajouter :");
                        scanner.nextLine(); 
                        String tacheDescription = scanner.nextLine();
                        Tache nouvelleTache = new Tache(tacheDescription);
                        tacheService.ajouterTache(nouvelleTache);
                        System.out.println("Tâche ajoutée avec succès.");
                        break;
                    case 2:
                        System.out.println("Entrez l'index de la tâche à supprimer :");
                        int index = scanner.nextInt();

                        List<Tache> taches = tacheService.getTaches();
                        if (index >= 0 && index < taches.size()) {

                            tacheService.suppTache(index);
                            System.out.println("Tâche supprimée avec succès.");
                        } else {
                            System.out.println("Index invalide.");
                        }
                        break;
                    case 3:
                        List<Tache> toutesTaches = tacheService.getTaches();
                        System.out.println("Toutes les tâches :");
                        for (int i = 0; i < toutesTaches.size(); i++) {
                            System.out.println(i + ". " + toutesTaches.get(i).getDescription());
                        }
                        System.out.println("\n");
                        break;
                    case 4:
                        System.out.println("Programme terminé.");
                        System.exit(0);
                    default:
                        System.out.println("Choix invalide. Veuillez entrer un nombre entre 1 et 4.");
                }
            }
        } catch (Exception e) {
            System.err.println("Exception Client: " + e.toString());
            e.printStackTrace();
        }
    }
}

