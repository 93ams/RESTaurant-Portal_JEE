package astielau.restaurantportal.cpk;

import java.io.Serializable;

public class OrderItemPK implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private PurchasePK purchase;
    private DishPK dish;

    public OrderItemPK() {}
    public OrderItemPK(PurchasePK purchase, DishPK dish) {
        this.purchase = purchase;
        this.dish = dish;
    }
    
    public PurchasePK getPurchase() { return purchase; } 
    public void setPurchase(PurchasePK purchase) { this.purchase = purchase; }

    public DishPK getDish() { return dish; } 
    public void setDish(DishPK dish) { this.dish = dish; }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (purchase != null ? purchase.hashCode() : 0);
        hash += (dish != null ? dish.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof OrderItemPK)) { return false; }
        OrderItemPK other = (OrderItemPK) object;
        return !((this.purchase == null && other.purchase != null) 
              || (this.dish == null && other.dish != null) 
              || (this.purchase != null && !this.purchase.equals(other.purchase) )
              || (this.dish != null && !this.dish.equals(other.dish)));
    }
    
}
