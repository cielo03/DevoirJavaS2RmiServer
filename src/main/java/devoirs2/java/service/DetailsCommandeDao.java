package devoirs2.java.service;

import devoirs2.java.model.Client;
import devoirs2.java.model.DetailsCommande;
import devoirs2.java.model.Entree;
import devoirs2.java.model.Produit;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class DetailsCommandeDao extends UnicastRemoteObject implements IDetailsCommande{
    private Session session;

    public DetailsCommandeDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }
    @Override
    public List<DetailsCommande> allDetails() throws RemoteException {
        return session.createQuery("SELECT dc from DetailsCommande dc", DetailsCommande.class).list();
    }

    @Override
    public List<DetailsCommande> detailsByCmd(int id) throws RemoteException {
        try{
            return session.createQuery("SELECT d FROM DetailsCommande d where d.commande = :id", DetailsCommande.class).list();

        }catch (Exception ex){
            return  null;
        }
    }

    @Override
    public void ajouterDetail(DetailsCommande detailsCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((detailsCommande));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifDetail(DetailsCommande detailsCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(detailsCommande);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteDetailsCommande(DetailsCommande detailsCommande) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            DetailsCommande details = (DetailsCommande) session.get(DetailsCommande.class, detailsCommande.getId());
            session.delete(details);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public DetailsCommande findById(int i) throws RemoteException {
        return session.find(DetailsCommande.class, i);
    }
}
