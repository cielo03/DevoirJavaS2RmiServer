package devoirs2.java.service;

import devoirs2.java.model.Profile;
import devoirs2.java.model.TypeClient;
import devoirs2.java.service.IProfile;
import devoirs2.java.utils.HibernateUtil;
import org.hibernate.Session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class ProfileDao extends UnicastRemoteObject implements IProfile {
    private Session session;

    public ProfileDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }

    @Override
    public List<Profile> allProfile() throws RemoteException {
        return session.createQuery("SELECT p from Profile p", Profile.class).list();
    }

    @Override
    public Profile findById(int i) throws RemoteException {
        return session.find(Profile.class, i);
    }
}
