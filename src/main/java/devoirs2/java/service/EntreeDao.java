package devoirs2.java.service;

import devoirs2.java.model.Entree;
import devoirs2.java.model.Produit;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class EntreeDao extends UnicastRemoteObject implements IEntree{
    private Session session;

    public EntreeDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Entree> allEntrees() throws RemoteException {
        return session.createQuery("SELECT e from Entree e",Entree.class).list();
    }

    @Override
    public List<Entree> allEntreesByDesc(String s) throws RemoteException {
        try{
            return session.createQuery("SELECT e FROM Entree e,Produit p where e.produit.id = p.id and p.description =:lib", Entree.class)
                    .setParameter("lib",s).list();

        }catch (Exception ex){
            return  null;
        }
    }

    @Override
    public void ajouterEntree(Entree entree) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((entree));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public Entree findById(int i) throws RemoteException {
        return session.find(Entree.class, i);
    }

    @Override
    public List<Entree> findAllById(int id) throws RemoteException {
        try{
            return session.createQuery("SELECT e FROM Entree e where e.produit = :id", Entree.class).list();

        }catch (Exception ex){
            return  null;
        }
    }
}
