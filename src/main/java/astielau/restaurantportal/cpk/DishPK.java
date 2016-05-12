package astielau.restaurantportal.cpk;

import java.io.Serializable;
import javax.persistence.Column;

public class DishPK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Column(name="RESTAURANT_ID", nullable=false)
    private String restaurant;
    
    @Column(name="DISH_ID", nullable=false)
    private Long id;
    
    public DishPK(){}
    public DishPK( String restaurant, Long id ){
        this.restaurant = restaurant;
        this.id = id;
    }
    
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }

    public String getRestaurant(){ return restaurant; }
    public void setRestaurant(String restaurant){ this.restaurant = restaurant; }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (restaurant != null ? restaurant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DishPK)) { return false; }
        DishPK other = (DishPK) object;
        return !((this.id == null && other.id != null) 
              || (this.restaurant == null && other.restaurant != null) 
              || (this.id != null && !this.id.equals(other.id) )
              || (this.restaurant != null && !this.restaurant.equals(other.restaurant)));
    }
}
