package astielau.restaurantportal.dao;

import astielau.restaurantportal.entities.ClientEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ClientDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    PurchaseDAO purchaseDAO;
    
    @EJB
    RateDAO rateDAO;
    
    public ClientEntity getClient( String username ){
        try {
            System.out.println("Looking for Client " + username);
            ClientEntity client = em.find(ClientEntity.class, username);
            return client;
        } catch ( Exception e ){
            System.out.println("Error @ ClientDAO: getClient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public ClientEntity registerClient( String username, String password, String name, String email, String address, String taxId ){
        try {
            if(getClient(username) == null){
                System.out.println("Registrering Client " + username);
                ClientEntity new_client = new ClientEntity(username, password, name, email, address, taxId);
                em.persist(new_client);
                return new_client;
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientDAO: registerClient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
}
