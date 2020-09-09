package devoirs2.java.service;

import devoirs2.java.model.Sortie;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class SortieDao extends UnicastRemoteObject implements ISortie{
    private Session session;

    public SortieDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }
    @Override
    public List<Sortie> allSorties() throws RemoteException {
        return session.createQuery("SELECT s from Sortie s",Sortie.class).list();
    }

    @Override
    public void ajouterSOrtie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((sortie));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifSortie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(sortie);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteSortie(Sortie sortie) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Sortie s = (Sortie) session.get(Sortie.class, sortie.getId());
            session.delete(s);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Sortie findById(int i) throws RemoteException {
        return session.find(Sortie.class, i);
    }
}
