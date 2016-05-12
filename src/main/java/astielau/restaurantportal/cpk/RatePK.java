package astielau.restaurantportal.cpk;

import java.io.Serializable;
import javax.persistence.Column;

public class RatePK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="CLIENT_USERNAME", nullable=false)
    private String client;
    @Column(name="DISH_ID", nullable=false)
    private DishPK dish; 
    
    public RatePK(){}
    public RatePK(String client, DishPK dish){
        this.client = client;
        this.dish = dish;
    }

    public String getClient() { return client; } 
    public void setClient(String client) { this.client = client; }

    public DishPK getDishId() { return dish; } 
    public void setDishId(DishPK dish) { this.dish = dish; }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (client != null ? client.hashCode() : 0);
        hash += (dish != null ? dish.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RatePK)) { return false; }
        RatePK other = (RatePK) object;
        return !((this.client == null && other.client != null) 
              || (this.client != null && !this.client.equals(other.client))
              || (this.dish == null && other.dish != null) 
              || (this.dish != null && !this.dish.equals(other.dish)));
    }
    
}
