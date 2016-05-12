package astielau.restaurantportal.enums;

public enum UserTypeEnum {
    CLIENT("client"),
    MANAGER("manager");
    
    private final String type;
    
    private UserTypeEnum(String value){ this.type = value; }
    
    public String getType(){ return type; }
}
