package astielau.restaurantportal.entities;

import astielau.restaurantportal.cpk.RatePK;
import astielau.restaurantportal.dto.RateDTO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@IdClass(value=RatePK.class)
@NamedQueries({
    @NamedQuery( name="findDishRates", query="SELECT r FROM RateEntity r WHERE r.dish.restaurant.name = :restaurantName AND r.dish.id = :dishId"),
    @NamedQuery( name="findClientRates", query="SELECT r FROM RateEntity r WHERE r.client.username = :username")
})
public class RateEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @ManyToOne 
    @JoinColumns({
        @JoinColumn(name= "DISH_ID", referencedColumnName="ID"),
        @JoinColumn(name= "RESTAURANT_NAME", referencedColumnName="RESTAURANT_NAME")
    })
    @Id private DishEntity dish;
    
    @ManyToOne 
    @JoinColumn(name= "CLIENT_USERNAME")
    @Id private ClientEntity client;
    
    @Max(value=5)
    @Min(value=0)
    private Integer rate;
    
    public RateEntity(){} 
    public RateEntity(DishEntity dish, ClientEntity client, Integer rate){
        this.rate = rate;
        this.client = client;
        this.dish = dish;
        this.bindingDish(dish);
        this.bindingClient(client);
    }
    
    public DishEntity getDish() { return dish; }
    public void setDish(DishEntity dish){
        this.dish = dish;
        this.bindingDish(dish);
    }
    private void bindingDish(DishEntity dish) {
        if(dish != null){
            if(!dish.getRates().contains(this)){
                dish.addRate(this);
            }
        }
    }

    public ClientEntity getClient() { return client; }
    public void setClient(ClientEntity client){
        this.client = client;
        this.bindingClient(client);
    }
    
    private void bindingClient(ClientEntity client) {
        if(client != null){
            if(!client.getRates().contains(this)){
                client.addRate(this);
            }
        }
    }
    
    public Integer getRate() { return rate; } 
    public void setRate(Integer rate) { this.rate = rate; }
    
    public RateDTO toDTO() { return new RateDTO(this.client.getUsername(), this.dish.getRestaurant().getName(), this.dish.getName(), this.rate); }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dish != null ? dish.hashCode() : 0);
        hash += (client != null ? client.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RateEntity)) {
            return false;
        }
        RateEntity other = (RateEntity) object;
        return !((this.dish == null && other.dish != null) 
              || (this.dish != null && !this.dish.equals(other.dish))
              || (this.client == null && other.client != null) 
              || (this.client != null && !this.client.equals(other.client)) );
    }

    @Override
    public String toString() {
        return "Rate from client: " + client.getName() 
                + " in dish: " + dish.getName() 
                + " of restaurant: " + dish.getRestaurant().getName()
                + " with value: " + rate;
    }
}
