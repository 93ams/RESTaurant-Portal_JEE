package astielau.restaurantportal.dto;

import java.util.List;

public class RestaurantDTO {
    private String name;
    private String address;
    private List<String> dishes;
    private List<String> managers;
    
    public RestaurantDTO(){}
    public RestaurantDTO(String name, String address, List<String> dishes, List<String> managers){
        this.name = name;
        this.address = address;
        this.dishes = dishes;
        this.managers = managers;
    }
    
    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; } 
    public void setAddress(String address) { this.address = address; } 
    
    public List<String> getDishes() { return dishes; }
    public void addDish(List<String> dishes) { this.dishes = dishes; }
    public void addDish(String dish) { this.dishes.add(dish); }
    
    public List<String> getManagers() { return managers; }
    public void addManagerh(List<String> managers) { this.managers = managers; }
    public void addManager(String manager) { this.dishes.add(manager); }
}
