package astielau.restaurantportal.dto;

public class OrderItemDTO {
    private String restaurant;
    private String dish;
    private Integer quantity;
    
    public OrderItemDTO() {}
    public OrderItemDTO(String restaurant, String dish, Integer quantity) {
        this.restaurant = restaurant;
        this.dish = dish;
        this.quantity = quantity;
    }

    public String getRestaurant() { return restaurant; } 
    public void setRestaurant(String restaurant) { this.restaurant = restaurant; }
    
    public String getDish() { return dish; } 
    public void setDish(String dish) { this.dish = dish; }

    public Integer getQuantity() { return quantity; } 
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
}
