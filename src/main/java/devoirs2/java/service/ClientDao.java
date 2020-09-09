package devoirs2.java.service;

import devoirs2.java.model.Client;
import devoirs2.java.model.Entree;
import devoirs2.java.model.Produit;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ClientDao extends UnicastRemoteObject implements IClient{
    private Session session;

    public ClientDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Client> allClients() throws RemoteException {
        return session.createQuery("SELECT c from Client c", Client.class).list();
    }

    @Override
    public void ajouterClient(Client client) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.save((client));
            t.commit();
        }catch (Exception e){
            t.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void modifClient(Client client) throws RemoteException {
        Transaction t = session.getTransaction();
        try {
            t.begin();
            session.merge(client);
            t.commit();
        }catch (Exception ex){
            t.rollback();
            ex.printStackTrace();
        }
    }


    @Override
    public Client findById(int i) throws RemoteException {
        return session.find(Client.class, i);
    }

    @Override
    public Client findByCni(String cnii) throws RemoteException {
        try{
            return session.createQuery("SELECT c FROM Client c where c.cni = :lib",Client.class)
                    .setParameter("lib", cnii)
                    .getSingleResult();
        }catch (Exception ex){
            return  null;
        }
    }
}
