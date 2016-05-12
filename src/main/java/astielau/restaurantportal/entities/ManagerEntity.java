package astielau.restaurantportal.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="username")
public class ManagerEntity extends UserEntity {
    
    @ManyToOne
    @JoinColumn(name="RESTAURANT_ID",
                referencedColumnName = "name")
    private RestaurantEntity restaurant;
    
    public ManagerEntity(){}
    public ManagerEntity(String username, String password, String name){
        super("manager", username, password, name);
    }
    
    public RestaurantEntity getRestaurant() { return restaurant; } 
    public void setRestaurant(RestaurantEntity restaurant) {
        if(restaurant != null){
            this.restaurant = restaurant;
            if(!restaurant.getManagers().contains(this)){
                restaurant.addManager(this);
            } 
        } else {
            if(this.restaurant.getManagers().contains(this)){
                this.restaurant.removeManager(this);
            } 
            this.restaurant = restaurant;
        }
    }
    
    @Override
    public String toString() { return "Manager: " + this.getUsername(); }
}
