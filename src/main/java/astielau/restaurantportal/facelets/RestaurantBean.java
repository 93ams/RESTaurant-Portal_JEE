package astielau.restaurantportal.facelets;

import astielau.restaurantportal.dao.ManagerDAO;
import astielau.restaurantportal.dao.RestaurantDAO;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.ManagerEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import astielau.restaurantportal.entities.UserEntity;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class RestaurantBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    ManagerDAO managerDAO;
    
    @EJB
    RestaurantDAO restaurantDAO;

    private String restaurantName;
    private String restaurantAddress;
    
    private String dishName;
    private Double dishPrice;
    
    public RestaurantBean(){}
    
    //Getters / Setters
    
    public String getRestaurantName() { return restaurantName; } 
    public void setRestaurantName(String restaurantName) { this.restaurantName = restaurantName; }

    public String getRestaurantAddress() { return restaurantAddress; } 
    public void setRestaurantAddress(String restaurantAddress) { this.restaurantAddress = restaurantAddress; }
    
    public String getDishName(){ return dishName; }
    public void setDishName( String dishName ) { this.dishName = dishName; }
    
    public Double getDishPrice(){ return dishPrice; }
    public void setDishPrice( Double dishPrice ) { this.dishPrice = dishPrice; }
    
    //Functional Methods
    
    public List<RestaurantEntity> getRestaurants(){
        try {
            return restaurantDAO.getAllRestaurants();
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: getRestaurants");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    private UserEntity getSessionUser() {
        try {
            HttpSession session = SessionBean.getSession();
            return (UserEntity) session.getAttribute("user");
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: getSessionUser");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    private RestaurantEntity getSessionRestaurant() {
        try {
            HttpSession session = SessionBean.getSession();
            return (RestaurantEntity) session.getAttribute("restaurant");
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: getSessionRestaurant");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    private void setSessionRestaurant(RestaurantEntity restaurant) {
        try {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("restaurant", restaurant);
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: setSessionRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    private void setSessionDish(RestaurantEntity restaurant, DishEntity dish) {
        try {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("restaurant", restaurant);
            session.setAttribute("dish", dish);
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: setSessionRestaurant");
            System.out.println( e.getMessage() );
        }
    }
    
    public String createRestaurant (){
        try{
            ManagerEntity user = (ManagerEntity) getSessionUser();
            if(user != null){
                managerDAO.createRestaurant(user.getUsername(), restaurantName, restaurantAddress);
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: createRestaurant");
            System.out.println( e.getMessage() );
        }
        return "index.html?faces-redirect=true";
    }

    public String addDish(){
        try {
            ManagerEntity user = (ManagerEntity) getSessionUser();
            RestaurantEntity restaurant = getSessionRestaurant();
            if(user != null && restaurant != null){
                managerDAO.addDishToRestaurant(user.getUsername(), restaurant.getName(), dishName, dishPrice);
                DishEntity new_dish = new DishEntity(dishName, dishPrice);
                restaurant.addDish(new_dish);
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: addDish");
            System.out.println( e.getMessage() );
        }
        return "restaurant.xhtml?faces-redirect=true";
    }
    
    public String goToRestaurant( RestaurantEntity restaurant ){
        try {
            System.out.println("Going to restaurant");
            if(restaurant != null){
                this.setSessionRestaurant(restaurant);
                return "restaurant.xhtml?faces-redirect=true";
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: goToRestaurant");
            System.out.println( e.getMessage() );
        }
        System.out.println("SHIET");
        return "index.xhtml?faces-redirect=true";
    }
    
    public String goToNewRestaurant(){
        try {
            UserEntity user = getSessionUser();
            if(user == null){
                errorMessage("Meh", "I don't know how you did this, but don't");
            } else if(!"manager".equals(user.getUserType())){
                errorMessage("What are you doing, you are no manager", "You cannot do this dumbdumb");
            } else if( ((ManagerEntity) user).getRestaurant() != null ){
                errorMessage("Sorry but you can't manage more than one restaurant, yet", "I didn't implement that on purpouse, hehe");
            } else {
                return "newrestaurant.xhtml?faces-redirect=true";
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: goToNewRestaurant");
            System.out.println( e.getMessage() );
        }
        return "restaurant.xhtml?faces-redirect=true";
    }
    
    public String goToDish( RestaurantEntity restaurant, DishEntity dish ){
        try {
            System.out.println("Go to dish");
            if(dish != null){
                this.setSessionDish(restaurant, dish);
                return "dish.xhtml?faces-redirect=true";
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: goToDish");
            System.out.println( e.getMessage() );
        }
        return "restaurant.xhtml?faces-redirect=true";
    }
                    
    public String goToNewDish(RestaurantEntity restaurant){
        try {
            UserEntity user = getSessionUser();
            if(user == null || restaurant == null){
                errorMessage("Meh", "I don't know how you did this, but don't");
            } else if(!"manager".equals(user.getUserType())){
                errorMessage("What are you doing, you are no manager", "You cannot do this dumbdumb");
            } else if(!restaurant.equals(((ManagerEntity) user).getRestaurant())){
                errorMessage("Don't get me wrong but, this is not your restaurant", "Do not missbehave silly");
            } else {
                return "newdish.xhtml?faces-redirect=true";
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: goToNewRestaurant");
            System.out.println( e.getMessage() );
        } 
        return "index.xhtml?faces-redirect=true";
    }
    
    public String removeDish( RestaurantEntity restaurant, DishEntity dish ){
        try {
            UserEntity user = getSessionUser();
            if(user != null){
                if(dish != null){
                    managerDAO.removeDishFromRestaurantByName(user.getUsername(), restaurantName, dishName);
                    restaurant.removeDish(dish);
                }else {
                    errorMessage("Some error whatever @ remove dish", "Something something darkside");
                }
            }
        } catch( Exception e ) {
            System.out.println("Error @ RestaurantBean: removeDish");
            System.out.println( e.getMessage() );
        }
        return "restaurant.xhtml?faces-redirect=true";
    }
    
    private void errorMessage( String message, String details ){
        FacesContext.getCurrentInstance().addMessage( null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, message, details));
    }

}
