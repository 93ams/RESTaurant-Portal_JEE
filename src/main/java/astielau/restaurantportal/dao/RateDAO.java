package astielau.restaurantportal.dao;

import astielau.restaurantportal.cpk.DishPK;
import astielau.restaurantportal.cpk.RatePK;
import astielau.restaurantportal.entities.ClientEntity;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.RateEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class RateDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public RateEntity getRate(String username, String restaurantName, Long dishId){
        try {
            RatePK ratePK = new RatePK(username, new DishPK(restaurantName, dishId));
            RateEntity rate = em.find(RateEntity.class, ratePK);
            return rate;
        } catch ( Exception e) {
            System.out.println("Error @ RateDAO: getRate");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public List<RateEntity> getDishRates(String restaurantName, Long dishId){
        try {
            return (List<RateEntity>) em.createNamedQuery("findDishRates")
                                        .setParameter("restaurantName", restaurantName)
                                        .setParameter("dishId", dishId)
                                        .getResultList();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ RateDAO: getDishRates");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public List<RateEntity> getClientRates(String username){
        try {
            return (List<RateEntity>) em.createNamedQuery("findClientRates")
                                        .setParameter("username", username)
                                        .getResultList();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ RateDAO: getClientRates");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public RateEntity createRate(ClientEntity client, DishEntity dish, Integer value){
        try {
            if(getRate(client.getUsername(), dish.getRestaurant().getName(), dish.getId()) == null){   
                RateEntity rate = new RateEntity(dish, client, value);
                em.persist(rate);
                return rate;
            }else{
                throw new Exception("You can't rate the same dish twice!");
            }
        } catch ( Exception e) {
            System.out.println("Error @ RateDAO: createRate");
            System.out.println( e.getMessage() );
        }
        return null;
    }

    public RateEntity updateRate(String username, String restaurantName, Long dishId, Integer value){
        try {
            RateEntity rate = getRate(username, restaurantName, dishId);
            if(rate != null){
                rate.setRate(value);
                return rate;
            }else{
                throw new Exception("Unable to find required rate.");
            }
        } catch ( Exception e) {
            System.out.println("Error @ RateDAO: deleteRate");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void deleteRate(String username, String restaurantName, Long dishId){
        try {
            RateEntity rate = getRate(username, restaurantName, dishId);
            if(rate != null){
                rate.getClient().removeRate(rate);
                rate.getDish().removeRate(rate);
                em.remove(rate);
            } else {
                throw new Exception("Unable to find required rate.");
            }
        } catch ( Exception e) {
            System.out.println("Error @ RateDAO: deleteRate");
            System.out.println( e.getMessage() );
        }
    }   

}
