package devoirs2.java.service;

import devoirs2.java.model.Commande;
import devoirs2.java.model.Facture;
import devoirs2.java.model.Sortie;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FactureDao extends UnicastRemoteObject implements IFacture{
    private Session session;

    public FactureDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Facture> allFactures() throws RemoteException {
        return session.createQuery("SELECT f from Facture f", Facture.class).list();
    }

    @Override
    public void ajouterFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((facture));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(facture);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteFacture(Facture facture) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Sortie s = (Sortie) session.get(Sortie.class, facture.getId());
            session.delete(s);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Facture findById(int i) throws RemoteException {
        return session.find(Facture.class, i);
    }

    @Override
   public Facture findByNum(String s) throws RemoteException {
        try{
            return session.createQuery("SELECT f FROM Facture f where f.numero = :lib",Facture.class)
                    .setParameter("lib", s)
                    .getSingleResult();
        }catch (Exception ex){
            return  null;
        }
    }

    @Override
    public int maxiNum() throws RemoteException {
        try {
            int mxid = (int) (session.createQuery("SELECT max(f.id) FROM Facture f").uniqueResult());
            return mxid;
        } catch (Exception ex) {
            return 0;
        }
    }
}
