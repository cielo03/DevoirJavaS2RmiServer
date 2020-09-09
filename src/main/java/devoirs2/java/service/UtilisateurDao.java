package devoirs2.java.service;

import devoirs2.java.model.Commande;
import devoirs2.java.model.Produit;
import devoirs2.java.model.Profile;
import devoirs2.java.model.Utilisateur;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class UtilisateurDao extends UnicastRemoteObject implements IUtilisateur {
    private Session session;

    public UtilisateurDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Utilisateur> allUtilisateur() throws RemoteException {
        return session.createQuery("SELECT u from Utilisateur u", Utilisateur.class).list();
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((utilisateur));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifUtilisateur(Utilisateur utilisateur) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(utilisateur);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteUtilisateur(Utilisateur utilisateur) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Utilisateur user = (Utilisateur) session.get(Utilisateur.class, utilisateur.getId());
            user.setEtat(-1);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Utilisateur findById(int i) throws RemoteException {
        return session.find(Utilisateur.class, i);
    }

    @Override
    public List<Utilisateur> allUsersByEtat() throws RemoteException {
        return session.createQuery("SELECT u from Utilisateur u where u.etat != -1", Utilisateur.class).list();
    }

    @Override
    public Utilisateur findByLogin(String libelle) throws RemoteException {
        try{
            return session.createQuery("SELECT u FROM Utilisateur u where u.login = :lib ", Utilisateur.class)
                    .setParameter("lib",libelle).getSingleResult();
        }catch (Exception ex){
            return  null;
        }
    }
}
