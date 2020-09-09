package devoirs2.java.service;

import devoirs2.java.model.Produit;
import devoirs2.java.model.Utilisateur;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProduitDao extends UnicastRemoteObject implements IProduit{
    private Session session;

    public ProduitDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Produit> allProduits() throws RemoteException {
        return session.createQuery("SELECT p from Produit  p", Produit.class).list();
    }

    @Override
    public List<Produit> allProduitsByEtat() throws RemoteException {
        return session.createQuery("SELECT p from Produit p where p.etat != 1", Produit.class).list();
    }

    @Override
    public void ajouterProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((produit));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(produit);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteProduit(Produit produit) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            Produit pro = (Produit) session.get(Produit.class, produit.getId());
            pro.setEtat(1);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }

    @Override
    public Produit findById(int i) throws RemoteException {
        return session.find(Produit.class, i);
    }

    @Override
    public Produit findByDesc(String libelle) throws RemoteException {
        try{
            return session.createQuery("SELECT p FROM Produit p where p.description = :lib",Produit.class)
                    .setParameter("lib", libelle)
                    .getSingleResult();
        }catch (Exception ex){
            return  null;
        }
    }
}
