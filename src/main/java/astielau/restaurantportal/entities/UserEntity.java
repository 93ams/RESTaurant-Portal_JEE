package astielau.restaurantportal.entities;

import astielau.restaurantportal.enums.UserTypeEnum;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance( strategy = InheritanceType.JOINED )
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id private String username;
    private String password;
    private String name;
    private UserTypeEnum userType;
    
    public UserEntity() {}
    public UserEntity(String userType, String username, String password, String name) {
        this.userType = this.convertUserType(userType);
        this.username = username;
        this.password = password;
        this.name = name;
    }
    
    public String getUsername() { return username; } 
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; } 
    public void setPassword(String password) { this.password = password; }
    
    public String getName() { return name; } 
    public void setName(String name) { this.name = name; }
    
    public String getUserType(){ return this.userType.getType(); }
    public void setUserType(String value){ this.userType = convertUserType(value); }
    
    private UserTypeEnum convertUserType(String value){
        switch(value){
            case "client":
                return UserTypeEnum.CLIENT;
            case "manager":
                return UserTypeEnum.MANAGER;
            default:
                return null;
        }
    }
    
    @Override
    public int hashCode() { return name != null ? name.hashCode() : 0; }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof UserEntity)) { return false; }
        UserEntity other = (UserEntity) object;
        return !((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)));
    }

    @Override
    public String toString() { return "User: " + name; }
 
}
