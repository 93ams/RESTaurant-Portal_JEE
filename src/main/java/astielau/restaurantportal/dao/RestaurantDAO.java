package astielau.restaurantportal.dao;

import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.ManagerEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class RestaurantDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private DishDAO dishDAO;
     
    public RestaurantDAO (){}
    
    public List<RestaurantEntity> getAllRestaurants () {
        System.out.println("Getting All Restaurants");
        try {
            return (List<RestaurantEntity>) em.createNamedQuery("findAllRestaurants")
                                              .getResultList();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: getAllRestaurants");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public RestaurantEntity getRestaurant ( String restaurantName ) {
        System.out.println("Getting Restaurant " + restaurantName);
        try {
            RestaurantEntity restaurant = (RestaurantEntity) em.find(RestaurantEntity.class, restaurantName);
            return restaurant;
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: getRestaurant");
            System.out.println( e.getMessage() );
        }
        return null;
    }

    public RestaurantEntity getRestaurantBySlug ( String restaurantSlug ){
        System.out.println("Getting Restaurant by slug " + restaurantSlug);
        try {
            RestaurantEntity restaurant = (RestaurantEntity) em.createNamedQuery("findRestaurantBySlug")
                                                               .setParameter("restaurantSlug", restaurantSlug)
                                                               .getSingleResult();
            return restaurant;
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: getRestaurantBySlug");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    //Create
    
    public RestaurantEntity createRestaurant ( String restaurantName, String address, ManagerEntity firstManager ) {
        try {
            if(getRestaurant(restaurantName) == null){ 
                System.out.println("Creating Restaurant " + restaurantName + " @ " + address);
                RestaurantEntity new_restaurant = new RestaurantEntity( restaurantName, address );
                em.persist( new_restaurant );
                new_restaurant.addManager( firstManager );
                return new_restaurant;
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: createRestaurant");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    //Updates
    
    public void updateRestaurantAddress ( String restaurantName, String address ) {
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Updating Restaurant " + restaurantName + "'s address to " + address);
                restaurant.setAddress(address);
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: updateRestaurantAddress");
            System.out.println( e.getMessage() );
        }
    }
    
    //Deletes
    
    public void deleteAllRestaurants(){
        try {
            List<RestaurantEntity> restaurants = getAllRestaurants();
            if( restaurants != null ){
                System.out.println("Deleting All Restaurants");
                for(RestaurantEntity restaurant: restaurants){
                    em.remove( restaurant );
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: deleteAllRestaurants");
            System.out.println( e.getMessage() );
        }
    }
    
    public void deleteRestaurant(String restaurantName){
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Deleting Restaurant " + restaurantName);
                em.remove( restaurant );
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: deleteRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    //Managers
    
    public List<ManagerEntity> getRestaurantManagers(String restaurantName){
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Getting restaurant " + restaurantName + "'s manager name list");
                return restaurant.getManagers();
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: getRestaurantManagers");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void addManagerToRestaurant(String restaurantName, ManagerEntity manager){
        try {
            System.out.println("Adding manager " + manager.getName() + " to restaurant " + restaurantName);
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Adding manager " + manager.getName() + " to Restaurant " + restaurantName);
                restaurant.addManager(manager);
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: addManagerToRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeManagerFromRestaurant(String restaurantName, ManagerEntity manager){
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Removing manager " + manager.getName() + " to Restaurant " + restaurantName);
                restaurant.removeManager(manager);
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: addManagerToRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    //Dishes
    
    public List<DishEntity> getRestaurantDishes(String restaurantName){
        System.out.println("Getting All Dishes from Restaurant " + restaurantName);
        try {
            RestaurantEntity restaurant = getRestaurant(restaurantName);
            if(restaurant != null){
                return restaurant.getDishes();
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: getRestaurantDishes");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public DishEntity addDishToRestaurant ( String restaurantName, String dishName, Double dishPrice ) {
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                if( dishDAO.getDishByName(restaurantName, dishName) == null ){
                    DishEntity new_dish = dishDAO.createDish(dishName, dishPrice);
                    new_dish.setRestaurant(restaurant);
                    em.persist(new_dish);
                    return new_dish;
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: addDishToRestaurant");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void removeAllRestaurantDishes(String restaurantName){
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Removing all of restaurant " + restaurantName + "'s dishes");
                for(DishEntity dish: restaurant.getDishes()){
                    System.out.println("Removing :" + dish);
                    restaurant.removeDish(dish);
                    em.remove(dish);
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: removeAllRestaurantDishes");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeDishFromRestaurantByName ( String restaurantName, String dishName ) {
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                System.out.println("Removing dish " + dishName + " from restaurant " + restaurantName);
                dishDAO.deleteDishByName( restaurantName, dishName );
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: removeDishByName");
            System.out.println( e.getMessage() );
        }
    }

    public void removeDishFromRestaurantById ( String restaurantName, Long dishId ) {
        try {
            RestaurantEntity restaurant = getRestaurant( restaurantName );
            if( restaurant != null ){
                dishDAO.deleteDishById( restaurantName, dishId );
            }
        } catch ( Exception e ) {
            System.out.println("Error @ RestaurantDAO: removeDishById");
            System.out.println( e.getMessage() );
        }
    }
}
