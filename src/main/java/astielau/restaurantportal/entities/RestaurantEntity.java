package astielau.restaurantportal.entities;

import astielau.restaurantportal.dto.RestaurantDTO;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery( name="findAllRestaurants", query="SELECT r FROM RestaurantEntity r"),
    @NamedQuery( name="findRestaurantBySlug", query="SELECT r FROM RestaurantEntity r WHERE r.slug = :restaurantSlug")
})
public class RestaurantEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id private String name;
    private String address;
    private String slug;
    private Double rate;
    
    @OneToMany( mappedBy="restaurant" )
    private final List<ManagerEntity> managers;
    
    @OneToMany( mappedBy="restaurant",
                cascade = CascadeType.ALL,
                orphanRemoval = true )
    private final List<DishEntity> dishes;
    
    public RestaurantEntity(){ 
        this.dishes = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.rate = 0.0;
    }
    public RestaurantEntity(String name, String address){
        this.name = name;
        this.slug = toSlug(name);
        this.address = address;
        this.dishes = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.rate = 0.0;
    }
    public RestaurantEntity(String name, String address, ManagerEntity firstManager){
        this.name = name;
        this.slug = toSlug(name);
        this.address = address;
        this.dishes = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.managers.add(firstManager);
        this.bindManager(firstManager);
        this.rate = 0.0;
    }
    
    public String getName() { return name; } 
    public void setName(String name) { 
        this.name = name; 
        this.slug = toSlug(name);
    }

    public String getAddress() { return address; } 
    public void setAddress(String address) { this.address = address; }

    public String getSlug() { return slug; }

    public List<ManagerEntity> getManagers() { return managers; }
    public void addManager(ManagerEntity manager){
        if(manager != null && !managers.contains(manager)){
            this.managers.add(manager);
            this.bindManager(manager);
        }
    }
    public void removeManager(ManagerEntity manager){
        if(manager != null && managers.contains(manager)){
            this.managers.remove(manager);
            this.unbindManager(manager);
        }
    }
    private void bindManager(ManagerEntity manager){
        if(manager.getRestaurant() != this){
            manager.setRestaurant(this);
        }
    }
    
    private void unbindManager(ManagerEntity manager){
        if(manager.getRestaurant() == this){
            manager.setRestaurant(null);
        }
    }
    
    public List<DishEntity> getDishes() { return dishes; }
    public void addDish(DishEntity dish){
        if(dish != null && !dishes.contains(dish)){
            this.dishes.add(dish);
            if(dish.getRestaurant() != this){
                dish.setRestaurant(this);
            }
        }
    }
    public void removeDish(DishEntity dish){
        if(dish != null && dishes.contains(dish))
            this.dishes.remove(dish);
    }

    public void setRate(){
        int len = 0;
        Double sum = 0.0;
        
        for(DishEntity dish: this.dishes){
            if(dish.getRates().size() > 0){
                sum += dish.getRate();
                len++;
            }
        }
        
        if(len > 2){
            this.rate = sum / len;
        }
    }
      
    public RestaurantDTO toDTO(){
        RestaurantDTO restaurant = new RestaurantDTO(this.name, this.address, new ArrayList<String>(), new ArrayList<String>());
        for(DishEntity dish: this.dishes){
            restaurant.addDish(dish.getName());
        }
        for(ManagerEntity manager: this.managers){
            restaurant.addManager(manager.getUsername());
        }
        return restaurant;
    }
    
    private String toSlug( String value ){
        String nowhitespace = Pattern.compile("[\\s]").matcher(value).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slugified = Pattern.compile("[^\\w-]").matcher(normalized).replaceAll("");
        return slugified.toLowerCase(Locale.ENGLISH);
    }
    
    @Override
    public int hashCode() { return name != null ? name.hashCode() : 0; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RestaurantEntity)) { return false; }
        RestaurantEntity other = (RestaurantEntity) object;
        return !((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)));
    }

    @Override
    public String toString() { return "Restaurant: " + name; }
}
