package astielau.restaurantportal.cpk;

import java.io.Serializable;

public class PurchasePK implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String client;
    private Long id; 
    
    public PurchasePK(){}
    public PurchasePK( String client, Long id ){
        this.client = client;
        this.id = id;
    }
    
    public Long getId(){ return id; }
    public void setId(Long id){ this.id = id; }
    
    public String getClient(){ return client; }
    public void setClient(String client){ this.client = client; }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        hash += (client != null ? client.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof PurchasePK)) { return false; }
        PurchasePK other = (PurchasePK) object;
        return !((this.id == null && other.id != null) 
              || (this.client == null && other.client != null) 
              || (this.id != null && !this.id.equals(other.id) )
              || (this.client != null && !this.client.equals(other.client)));
    }
}
