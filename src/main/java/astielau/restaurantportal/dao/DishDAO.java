package astielau.restaurantportal.dao;

import astielau.restaurantportal.cpk.DishPK;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.IngredientEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class DishDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    @EJB
    private IngredientDAO ingredientDAO;
    
    public DishDAO (){}
    
    public List<DishEntity> getAllDishes(){
        System.out.println("Getting All Dishes");
        try {
            return (List<DishEntity>) em.createNamedQuery("findAllDishes")
                                        .getResultList();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: getAllDish");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public List<DishEntity> getRestaurantDishes(String restaurantName){
        System.out.println("Getting All Dishes from Restaurant " + restaurantName);
        try {
            return (List<DishEntity>) em.createNamedQuery("findRestaurantDishes")
                                        .setParameter("restaurantName", restaurantName)
                                        .getResultList();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: getRestaurantDishes");
            System.out.println( e.getMessage() );
        }
        return null;
    }

    public DishEntity getDishById ( String restaurantName, Long dishId ){
        try {
            System.out.println("Getting Dish #" + dishId + " from Restaurant " + restaurantName);
            return (DishEntity) em.find(DishEntity.class, new DishPK(restaurantName, dishId));
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: getDishById");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public DishEntity getDishByName (String restaurantName, String dishName){
        try {
            System.out.println("Getting Dish " + dishName + " from Restaurant " + restaurantName);
            return (DishEntity) em.createNamedQuery("findDishByName")
                                  .setParameter("restaurantName", restaurantName)
                                  .setParameter("dishName", dishName)
                                  .getSingleResult();
        } catch ( NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: getDishByName");
            System.out.println( e.getMessage() );
        }
        return null;
    }

    //create
    
    public DishEntity createDish (String name, Double price){
        System.out.println("Creating Dish " + name + " that costs " + price + "$.");
        try {
            DishEntity new_dish = new DishEntity( name, price );
            return new_dish;
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: createDish");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    //deletes
    
    public void deleteAllDishes(){
        try{
            List<DishEntity> dishes = getAllDishes();
            if(dishes != null){
                for(DishEntity dish: dishes){
                    dish.getRestaurant().removeDish(dish);
                    em.remove(dish);
                }
            }
        } catch( Exception e ){
            System.out.println("Error @ dishDAO: deleteRestaurantDishes");
            System.out.println( e.getMessage() );
        }
    }
    
    public void deleteRestaurantDishes(String restaurantName){
        try{
            List<DishEntity> dishes = getRestaurantDishes(restaurantName);
            if(dishes != null){
                for(DishEntity dish: dishes){
                    dish.getRestaurant().removeDish(dish);
                    em.remove(dish);
                }
            }
        } catch( Exception e ){
            System.out.println("Error @ dishDAO: deleteRestaurantDishes");
            System.out.println( e.getMessage() );
        }
        
    }
    
    public void deleteDishByName(String restaurantName, String dishName){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            System.out.println("Deleting Dish " + dishName + " of Restaurant " + restaurantName + ".");
            em.remove( dish );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: deleteDishByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void deleteDishById(String restaurantName, Long dishId){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            System.out.println("Deleting Dish #" + dishId + " of Restaurant " + restaurantName + ".");
            em.remove( dish );
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: deleteDishById");
            System.out.println( e.getMessage() );
        }
    }

    //updates
    
    public void updateDishPriceByName(String restaurantName, String dishName, Double dishPrice){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            dish.setPrice(dishPrice);
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: updateDishPriceByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void updateDishPriceById(String restaurantName, Long dishId, Double dishPrice){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            dish.setPrice(dishPrice);
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: updateDishPriceById");
            System.out.println( e.getMessage() );
        }
    }
    
    public void updateDishNameByName(String restaurantName, String dishName, String newName){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            dish.setName(newName);
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: updateDishNameByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void updateDishNameById(String restaurantName, Long dishId, String newName){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            dish.setName(newName);
        } catch ( Exception e ) {
            System.out.println("Error @ DishDAO: updateDishNameById");
            System.out.println( e.getMessage() );
        }
    }

    //Ingredients
    
    public IngredientEntity addIngredientToDishByName( String restaurantName, String dishName, String ingredientName ){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.getIngredient(ingredientName);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.addIngredient(ingredient);
            return ingredient;
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: addIngredientToDishByName");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity addIngredientToDishById( String restaurantName, Long dishId, String ingredientName ){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.getIngredient(ingredientName);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.addIngredient(ingredient);
            return ingredient;
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: addIngredientToDishById");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity createIngredientAndAddToDishByName( String restaurantName, String dishName, String ingredientName, String ingredientType ){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.createIngredient(ingredientName, ingredientType);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.addIngredient(ingredient);
            return ingredient;
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: createAndAddIngredientToDishByName");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity createIngredientAndAddToDishById( String restaurantName, Long dishId, String ingredientName, String ingredientType ){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.createIngredient(ingredientName, ingredientType);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.addIngredient(ingredient);
            return ingredient;
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: createAndAddIngredientToDishById");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void removeIngredientFromDishByName( String restaurantName, String dishName, String ingredientName ){
        try {
            DishEntity dish = getDishByName(restaurantName, dishName);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.getIngredient(ingredientName);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.removeIngredient(ingredient);
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: removeIngredientFromDishByName");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeIngredientFromDishById( String restaurantName, Long dishId, String ingredientName ){
        try {
            DishEntity dish = getDishById(restaurantName, dishId);
            if(dish == null){
                throw new Exception("Unexisting Dish");
            }
            IngredientEntity ingredient = ingredientDAO.getIngredient(ingredientName);
            if(ingredient == null){
                throw new Exception("Unexisting Ingredient");
            }
            dish.removeIngredient(ingredient);
        } catch ( Exception e ){
            System.out.println("Error @ DishDAO: removeIngredientFromDishById");
            System.out.println( e.getMessage() );
        }
    }
    
}
