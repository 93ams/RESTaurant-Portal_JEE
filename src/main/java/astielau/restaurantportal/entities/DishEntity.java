package astielau.restaurantportal.entities;

import astielau.restaurantportal.cpk.DishPK;
import astielau.restaurantportal.dto.DishDTO;
import astielau.restaurantportal.dto.IngredientDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@NamedQueries({
    @NamedQuery( name="findAllDishes", query="SELECT d FROM DishEntity d"),
    @NamedQuery( name="findRestaurantDishes", query="SELECT d FROM RestaurantEntity r, DishEntity d WHERE d.restaurant.name = r.name AND r.name = :restaurantName"),
    @NamedQuery( name="findDishByName", query="SELECT d FROM RestaurantEntity r, DishEntity d WHERE d.restaurant.name = r.name AND r.name = :restaurantName AND d.name = :dishName")
})
@IdClass(DishPK.class)
public class DishEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private Long id;
    
    @ManyToOne(optional=false)
    @JoinColumn(name="RESTAURANT_NAME", 
                nullable=false, 
                referencedColumnName = "name", 
                updatable = false)
    @Id private RestaurantEntity restaurant;
    
    @Size(max = 255)
    private String name;
    
    @Min(value=0)
    private Double price;
    
    @ManyToMany
    private final List<IngredientEntity> ingredients;
    
    @OneToMany( mappedBy="dish",
                cascade={CascadeType.ALL},
                orphanRemoval = true )
    List<RateEntity> rates;
    
    private Double rate;
    
    public DishEntity() { 
        this.ingredients = new ArrayList<>(); 
        this.rates = new ArrayList<>();
    }
    public DishEntity(String name, Double price) {
        this.name = name;
        this.price = price;
        this.ingredients = new ArrayList<>();
        this.rates = new ArrayList<>();
    }
    public DishEntity(String name, Double price, RestaurantEntity restaurant) {
        this.name = name;
        this.price = price;
        this.ingredients = new ArrayList<>();
        this.rates = new ArrayList<>();
        this.restaurant = restaurant;
        this.bindRestaurant(restaurant);
    }
    
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    
    public RestaurantEntity getRestaurant(){ return restaurant; } 
    public void setRestaurant(RestaurantEntity restaurant){ 
        this.restaurant = restaurant;
        if(restaurant != null){
            this.bindRestaurant(restaurant);
        }
    }
    
    private void bindRestaurant(RestaurantEntity restaurant){
        if(!restaurant.getDishes().contains(this)){
            restaurant.addDish(this);
        }
    }
    
    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; } 
    public void setPrice(Double price) { this.price = price; }
    
    public List<IngredientEntity> getIngredients(){ return ingredients; }
    public void addIngredient(IngredientEntity ingredient){
        try {
            if(ingredient != null && !ingredients.contains(ingredient)){
                this.ingredients.add(ingredient);
            }
        } catch ( Exception e ){
            System.out.println("Error @ DishEntity: addIngredient");
            System.out.println( e.getMessage() );
        }
    }
    public void removeIngredient(IngredientEntity ingredient){
        try {
            if(ingredient != null && ingredients.contains(ingredient)){
                ingredients.remove(ingredient);
            }
        } catch ( Exception e ){
            System.out.println("Error @ DishEntity: removeIngredient");
            System.out.println( e.getMessage() );
        }
    }
    
    public List<RateEntity> getRates(){ return rates; }
    public void addRate( RateEntity rate ){
        try { 
            if(!this.rates.contains(rate)){
                this.rates.add(rate);
                if(this.rates.size() == 1){
                    this.rate = rate.getRate().doubleValue();
                }else{
                    this.rate = ((this.rate * (this.rates.size()-1)) + rate.getRate())/this.rates.size();
                }
                this.restaurant.setRate();
            }else{
                throw new Exception("Repeated Rate");
            }
        } catch (Exception e) {
            System.out.println("Error @ DishEntity: addRate");    
            System.out.println( e.getMessage() );
        }
    }
    public void removeRate( RateEntity rate ){
        try {
            if(this.rates.contains(rate)){
                this.rates.remove(rate);
                if(this.rates.isEmpty()){
                    this.rate = 0.0;
                }else{
                    this.rate = ((this.rate * (this.rates.size()+1)) - rate.getRate())/this.rates.size();
                }
                this.restaurant.setRate();
            }else{
                throw new Exception("Unexisting Rate");
            }
        } catch (Exception e) {
            System.out.println("Error @ DishEntity: removeRate");    
            System.out.println( e.getMessage() );
        }
    }
    
    public Double getRate() { return rate; }
    
    public DishDTO toDTO(){
        DishDTO dish = new DishDTO(this.name, this.restaurant.getName(), this.id, this.price, this.rate, new ArrayList<IngredientDTO>());
        for(IngredientEntity ingredient: this.ingredients){
            dish.addIngredient(ingredient.toDTO());
        }
        return dish;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (restaurant != null ? restaurant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals( Object object ) {
        if (!(object instanceof DishEntity)) {
            return false;
        }
        DishEntity other = (DishEntity) object;
        return !((this.id == null && other.id != null) 
              || (this.id != null && !this.id.equals(other.id)) 
              || (this.restaurant == null && other.restaurant != null) 
              || (this.restaurant != null && !this.restaurant.equals(other.restaurant)));
    }

    @Override
    public String toString() { return "Dish: " + name 
            + ( restaurant == null ? "" 
            : (" from Restaurant: " + restaurant.getName())); }
}
