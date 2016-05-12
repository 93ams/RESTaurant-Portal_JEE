package astielau.restaurantportal.dto;

public class RateDTO {
    private String client;
    private String restaurant;
    private String dish;
    private Integer rate;

    public RateDTO() {}
    public RateDTO(String client, String restaurant, String dish, Integer rate) {
        this.client = client;
        this.restaurant = restaurant;
        this.dish = dish;
        this.rate = rate;
    }
    
    public String getClient() { return client; } 
    public void setClient(String client) { this.client = client; }

    public String getRestaurant() { return restaurant; } 
    public void setRestaurant(String restaurant) { this.restaurant = restaurant; }

    public String getDish() { return dish; } 
    public void setDish(String dish) { this.dish = dish; }

    public Integer getRate() { return rate; } 
    public void setRate(Integer rate) { this.rate = rate; } 
    
}
