package devoirs2.java;

import devoirs2.java.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {

        try {
            System.setSecurityManager(new SecurityManager());
            Registry registry = LocateRegistry.createRegistry(5003);
            IProduit iPro = new ProduitDao();
            IEntree iEntree = new EntreeDao();
            ISortie iSortie = new SortieDao();
            IClient iClient = new ClientDao();
            IDetailsCommande iDetailsCommande = new DetailsCommandeDao();
            ICommande iCommande = new CommandeDao();
            ITypeClient iTypeClient = new TypeClientDao();
            IProfile iProfile = new ProfileDao();
            IUtilisateur iUtilisateur = new UtilisateurDao();
            IFacture iFacture = new FactureDao();

            registry.bind("produitRemote", iPro);
            registry.bind("entreeRemote", iEntree);
            registry.bind("sortieRemote", iSortie);
            registry.bind("clientRemote", iClient);
            registry.bind("typeClientRemote", iTypeClient);
            registry.bind("detailsRemote", iDetailsCommande);
            registry.bind("commandeRemote", iCommande);
            registry.bind("profileRemote", iProfile);
            registry.bind("utilisateurRemote", iUtilisateur);
            registry.bind("factureRemote", iFacture);
            System.out.println("Serveur est Lancé sur le port 5003");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
