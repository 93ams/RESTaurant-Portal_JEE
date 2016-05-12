package astielau.restaurantportal.entities;

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
    
    public UserEntity() {}
    public UserEntity(String username, String password, String name) {
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

    public Object getUserType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
