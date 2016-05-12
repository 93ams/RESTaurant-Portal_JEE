package astielau.restaurantportal.dao;

import astielau.restaurantportal.entities.UserEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class LoginDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    ManagerDAO managerDAO;
    
    @EJB
    ClientDAO clientDAO;
    
    public LoginDAO() {}
    
    private UserEntity getUser( String username ){
        try {
            return (UserEntity) em.find(UserEntity.class, username);
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public UserEntity registerManager ( String username, String password, String name ) {
        return managerDAO.registerManager(username, password, name);
    }
    
    public UserEntity registerClient ( String username, String password, String name, String email, String address, String taxId ) {
        return clientDAO.registerClient(username, password, name, email, address, taxId);
    }
    
    public UserEntity validate( String username, String password ){
        try {
            UserEntity user = getUser( username );
            if( user != null && user.getPassword().equals(password)){
                return user;
            }
        } catch ( Exception e ) {
            System.out.println( e.getMessage() );
        }
        return null;
    }
}
