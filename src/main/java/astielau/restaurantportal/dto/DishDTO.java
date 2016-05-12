package astielau.restaurantportal.dto;

import java.util.List;

public class DishDTO {
    
    private String name;
    private String restaurant;
    private Long id;
    private Double price;
    private Double rate;
    private List<IngredientDTO> ingredients;
    
    public DishDTO() {}
    public DishDTO(String name, String restaurant, Long id, Double price, Double rate, List<IngredientDTO> ingredients) {
        this.name = name;
        this.restaurant = restaurant;
        this.id = id;
        this.price = price;
        this.rate = rate;
        this.ingredients = ingredients;
    }

    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }

    public String getRestaurant() { return restaurant; } 
    public void setRestaurant(String restaurant) { this.restaurant = restaurant; }

    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }

    public Double getPrice() { return price; } 
    public void setPrice(Double price) { this.price = price; }

    public Double getRate() { return rate; } 
    public void setRate(Double rate) { this.rate = rate; }

    public List<IngredientDTO> getIngredients() { return ingredients; } 
    public void setIngredients(List<IngredientDTO> ingredients) { this.ingredients = ingredients; }
    public void addIngredient(IngredientDTO ingredient) { this.ingredients.add(ingredient); }

}
