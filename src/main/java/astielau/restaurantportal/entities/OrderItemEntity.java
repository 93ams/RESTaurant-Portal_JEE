package astielau.restaurantportal.entities;

import astielau.restaurantportal.cpk.OrderItemPK;
import astielau.restaurantportal.dto.OrderItemDTO;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Entity
@IdClass(OrderItemPK.class)
public class OrderItemEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ManyToOne(optional=false)
    @JoinColumns({
        @JoinColumn(name= "PURCHASE_ID", referencedColumnName="ID"),
        @JoinColumn(name= "CLIENT_USERNAME", referencedColumnName="CLIENT_USERNAME")
    })
    @Id private PurchaseEntity purchase;
    
    @ManyToOne(optional=false)
    @JoinColumns({
        @JoinColumn(name= "DISH_ID", referencedColumnName="ID"),
        @JoinColumn(name= "RESTAURANT_ID", referencedColumnName="RESTAURANT_ID")
    })
    @Id private DishEntity dish;
    
    private Integer quantity;
    
    public OrderItemEntity() {}
    public OrderItemEntity(PurchaseEntity purchase, DishEntity dish, Integer quantity) {
        this.purchase = purchase;
        this.dish = dish;
        this.quantity = quantity;
    }
    
    public PurchaseEntity getPurchase() { return purchase; } 
    public void setPurchase(PurchaseEntity purchase) { 
        if(purchase != null && !purchase.getItems().contains(this)){
            purchase.addItem(this);
        }
        this.purchase = purchase; 
    }

    public DishEntity getDish() { return dish; } 
    public void setDish(DishEntity dish) { this.dish = dish; }

    public Integer getQuantity() { return quantity; } 
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public OrderItemDTO toDTO(){ return new OrderItemDTO(dish.getRestaurant().getName(), dish.getName(), this.quantity); }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchase != null ? purchase.hashCode() : 0);
        hash += (dish != null ? dish.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals( Object object ) {
        if (!(object instanceof OrderItemEntity)) {
            return false;
        }
        OrderItemEntity other = (OrderItemEntity) object;
        return !((this.purchase == null && other.purchase != null) 
              || (this.purchase != null && !this.purchase.equals(other.purchase)) 
              || (this.dish == null && other.dish != null) 
              || (this.dish != null && !this.dish.equals(other.dish)));
    }
    
    @Override
    public String toString() { 
        return "Order for " + quantity 
             + " " + dish.getName() 
             + " by client " + purchase.getClient().getName(); 
    }
}
