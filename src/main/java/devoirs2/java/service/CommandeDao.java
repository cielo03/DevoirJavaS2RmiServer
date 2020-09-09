package devoirs2.java.service;

import devoirs2.java.model.*;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class CommandeDao extends UnicastRemoteObject implements ICommande{
    private Session session;

    public CommandeDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Commande> allCommandes() throws RemoteException {
        return session.createQuery("SELECT c from Commande c", Commande.class).list();
    }

    @Override
    public List<Commande> allCommandesByEtat() throws RemoteException {
        return session.createQuery("SELECT c from Commande c where c.etat != -1", Commande.class).list();
    }

    @Override
    public void ajouterCommande(Commande commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((commande));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifCommande(Commande commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(commande);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteCommande(Commande commande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Commande cmd = (Commande) session.get(Commande.class, commande.getId());
            cmd.setEtat(-1);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Commande findById(int i) throws RemoteException {
        return session.find(Commande.class, i);
    }

    @Override
    public Commande findByNum(String num) throws RemoteException {
        try{
            return session.createQuery("SELECT c FROM Commande c where c.numero = :lib",Commande.class)
                    .setParameter("lib", num)
                    .getSingleResult();
        }catch (Exception ex){
            return  null;
        }
    }

    @Override
    public int generNum() throws RemoteException {
        try {
            int mxid = (int) (session.createQuery("SELECT max(c.id) FROM Commande c").uniqueResult());
            return mxid;
        } catch (Exception ex) {
            return 0;
        }
    }

}
