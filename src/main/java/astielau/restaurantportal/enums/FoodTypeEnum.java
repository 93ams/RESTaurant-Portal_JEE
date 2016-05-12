package astielau.restaurantportal.enums;

public enum FoodTypeEnum {
    MEAT("meat"),
    FISH("fish"),
    VEGETARIAN("vegetarian");
    
    private final String type;
    
    private FoodTypeEnum(String type){ this.type = type; }
    
    public String getType(){ return type; }
}
