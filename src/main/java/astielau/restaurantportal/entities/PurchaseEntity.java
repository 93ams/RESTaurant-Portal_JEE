package astielau.restaurantportal.entities;

import astielau.restaurantportal.cpk.PurchasePK;
import astielau.restaurantportal.dto.OrderItemDTO;
import astielau.restaurantportal.dto.PurchaseDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
    @NamedQuery( name="findAllPurchases", query="SELECT p FROM PurchaseEntity p"),
    @NamedQuery( name="findClientPurchases", query="SELECT p FROM PurchaseEntity p WHERE p.client.name = :username"),
    @NamedQuery( name="findClientShoppingCart", query="SELECT p FROM PurchaseEntity p WHERE p.client.name = :username AND p.payed = false")
})
@IdClass(PurchasePK.class)
public class PurchaseEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id private Long id;
    
    @ManyToOne 
    @JoinColumn(name= "CLIENT_USERNAME")
    @Id private ClientEntity client;
    
    @OneToMany( mappedBy="purchase",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    private final List<OrderItemEntity> items;
    
    private boolean payed;
    private Double total;
    
    public PurchaseEntity(){
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.payed = false; 
    }
    public PurchaseEntity(ClientEntity client){
        this.client = client;
        this.items = new ArrayList<>();
        this.total = 0.0;
        this.payed = false;
    }
    
    public Long getId() { return id; } 
    public void setId(Long id) { this.id = id; }

    public ClientEntity getClient() { return client; } 
    public void setClient(ClientEntity client) { this.client = client; }

    public List<OrderItemEntity> getItems() { return items; } 
    public void addItem(OrderItemEntity item){
        if(item != null && !this.items.contains(item)){
            this.items.add(item);
            if(item.getPurchase() != this){
                item.setPurchase(this);
            }
        }
    }
    public void removeItem(OrderItemEntity item){
        if(item != null && this.items.contains(item)){
            this.items.remove(item);
        }
    }
    
    public boolean isPayed() { return payed; } 
    public void pay() throws Exception { //Mudar para InsuficientCreditException depois de a implementar
        Double value = this.getTotal();
        Double credit = this.client.getCredit();
        if(total > credit){
            throw new Exception("Not enough credit!!");
        } else {
            this.client.setCredit(credit - value);
            this.total = value;
            this.payed = true; 
        }
    }
    
    public Double getTotal(){ 
        if(this.payed){
            return total;
        } else {
            Double ctr = 0.0;
            for(OrderItemEntity item: this.items){
                ctr += (item.getDish().getPrice() * item.getQuantity());
            }
            return ctr;
        }
    }
    
    public PurchaseDTO toDTO(){
        PurchaseDTO purchase = new PurchaseDTO(this.client.getUsername(), this.id, this.total, new ArrayList<OrderItemDTO>());
        for(OrderItemEntity item: this.items){
            purchase.addItem(item.toDTO());
        }
        return purchase;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (client != null ? client.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals( Object object ) {
        if (!(object instanceof PurchaseEntity)) {
            return false;
        }
        PurchaseEntity other = (PurchaseEntity) object;
        return !((this.id == null && other.id != null) 
              || (this.id != null && !this.id.equals(other.id)) 
              || (this.client == null && other.client != null) 
              || (this.client != null && !this.client.equals(other.client)));
    }
    
    @Override
    public String toString() { return "Purchase #" + id 
                                    + " from client: " + client.getName() 
                                    + " cost: " + getTotal(); }
}
