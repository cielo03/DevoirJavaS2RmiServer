package devoirs2.java.service;

import devoirs2.java.model.Client;
import devoirs2.java.model.TypeClient;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TypeClientDao extends UnicastRemoteObject implements ITypeClient{
    private Session session;

    public TypeClientDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<TypeClient> allTypeClients() throws RemoteException {
        return session.createQuery("SELECT tc from TypeClient tc", TypeClient.class).list();
    }

    @Override
    public TypeClient findById(int i) throws RemoteException {
        return session.find(TypeClient.class, i);
    }
}
