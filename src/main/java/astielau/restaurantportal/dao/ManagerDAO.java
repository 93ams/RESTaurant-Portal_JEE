package astielau.restaurantportal.dao;

import astielau.restaurantportal.entities.ManagerEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ManagerDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    RestaurantDAO restaurantDAO;
    
    @EJB
    DishDAO dishDAO;
    
    public ManagerDAO(){}
    
    public ManagerEntity getManager( String username ){
        try {
            System.out.println("Looking for manager " + username);
            ManagerEntity manager = em.find(ManagerEntity.class, username);
            return manager;
        } catch ( Exception e ){
            System.out.println("Error @ ManagerDAO: getManager");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public ManagerEntity registerManager( String username, String password, String name ){
        try {
            if(getManager(username) == null){
                System.out.println("Registrering Manager " + username);
                ManagerEntity new_manager = new ManagerEntity(username, password, name);
                em.persist(new_manager);
                return new_manager;
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: registerManager");
            System.out.println( e.getMessage() );
        }
        
        return null;
    }
    
    public boolean checkManager( String username, String restaurantName ){
        try {
            ManagerEntity manager = getManager( username );
            if(manager != null){
                RestaurantEntity restaurant = manager.getRestaurant();
                if(restaurant != null) {
                    return restaurant.getName().equals(restaurantName) 
                        && restaurant.getManagers().contains(manager);
                } else if (restaurant == null){
                    return true;
                }
            }
        } catch(Exception e) {
            System.out.println("Error @ ManagerDAO: checkManager");
            System.out.println( e.getMessage() );
        }
        return false;
    }
    
    //Restaurant Methods
    
    public void createRestaurant ( String username, String restaurantName, String restaurantAddress ){
        try {
            ManagerEntity manager = this.getManager( username );
            if(manager.getRestaurant() == null){
                System.out.println(username + " is trying to create a restaurant");
                restaurantDAO.createRestaurant(restaurantName, restaurantAddress, manager);
            } else {
                throw new Exception("You can't manage two restaurants at once, yet");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: createRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public void addManagerToRestaurant(String username, String restaurantName){
        try {
            ManagerEntity manager = this.getManager(username);
            if(manager != null){
                RestaurantEntity restaurant = manager.getRestaurant();
                if(restaurant != null && restaurant.getName().equals(restaurantName)){
                    restaurantDAO.addManagerToRestaurant(restaurantName, manager);
                } else {
                    throw new Exception("You're not this restaurant's manager.");
                }
            } else {
                throw new Exception("Manager " + username + " does not exist!");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: addManagerToRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public void updateRestaurantAddress ( String username, String restaurantName, String restaurantAddress ){
        try {
            if(checkManager(username, restaurantName)){
                restaurantDAO.updateRestaurantAddress(restaurantName, restaurantAddress);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: updateRestaurantAddress");
            System.out.println( e.getMessage() );
        }
    }
    
    public void deleteRestaurant ( String username, String restaurantName ){
        try {
            if(checkManager(username, restaurantName)){
                restaurantDAO.deleteRestaurant(restaurantName);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: deleteRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public void addDishToRestaurant ( String username, String restaurantName, String dishName, Double dishPrice ){
        try {
            if(checkManager(username, restaurantName)){
                restaurantDAO.addDishToRestaurant(restaurantName, dishName, dishPrice);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: addDishToRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeDishFromRestaurantByName ( String username, String restaurantName, String dishName ){
        try {
            if(checkManager(username, restaurantName)){
                restaurantDAO.removeDishFromRestaurantByName(restaurantName, dishName);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: removeDishFromRestaurantByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeDishFromRestaurantById ( String username, String restaurantName, Long dishId ){
        try {
            if(checkManager(username, restaurantName)){
                restaurantDAO.removeDishFromRestaurantById(restaurantName, dishId);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: removeDishFromRestaurantById");
            System.out.println( e.getMessage() );
        }
    }

    //Dish Methods
    
    public void updateDishPrice ( String username, String restaurantName, String dishName, Double dishPrice ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.updateDishPriceByName(restaurantName, dishName, dishPrice);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: updateDishPrice");
            System.out.println( e.getMessage() );
        }
    }
        
    public void createIngredientAndAddToDishByName ( String username, String restaurantName, String dishName, String ingredientName, String ingredientType ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.createIngredientAndAddToDishByName(restaurantName, dishName, ingredientName, ingredientType);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: createIngredientAndAddToDishByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void createIngredientAndAddToDishById ( String username, String restaurantName, Long dishId, String ingredientName, String ingredientType ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.createIngredientAndAddToDishById(restaurantName, dishId, ingredientName, ingredientType);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: createIngredientAndAddToDishById");
            System.out.println( e.getMessage() );
        }
    }
    
    //Ingredient Methods

    public void addIngredientToDishByName ( String username, String restaurantName, String dishName, String ingredientName ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.addIngredientToDishByName(restaurantName, dishName, ingredientName);
            } else {
                //Levantar excepção
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: addIngredientToDishByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void addIngredientToDishById ( String username, String restaurantName, Long dishId, String ingredientName ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.addIngredientToDishById(restaurantName, dishId, ingredientName);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: addIngredientToDishById");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeIngredientFromDishByName ( String username, String restaurantName, String dishName, String ingredientName ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.removeIngredientFromDishByName(restaurantName, dishName, ingredientName);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: removeIngredientFromDishByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeIngredientFromDishById ( String username, String restaurantName, Long dishId, String ingredientName ){
        try {
            if(checkManager(username, restaurantName)){
                dishDAO.removeIngredientFromDishById(restaurantName, dishId, ingredientName);
            } else {
                throw new Exception("You're not this restaurant's manager.");
            }
        } catch ( Exception e ) {
            System.out.println("Error @ ManagerDAO: removeIngredientFromDishById");
            System.out.println( e.getMessage() );
        }
    }
}
