package astielau.restaurantportal.entities;

import astielau.restaurantportal.dto.ClientDTO;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(referencedColumnName="username")
public class ClientEntity extends UserEntity {
    
    private String email;
    private String address;
    private String taxId;
    private Double credit;
    
    @OneToMany( mappedBy="client", 
                cascade={CascadeType.ALL},
                orphanRemoval = true )
    private final List<RateEntity> rates;
    
    public ClientEntity(){
        this.credit = 0.0;
        this.rates = new ArrayList<>();
    }
    public ClientEntity(String username, String password, String name, String email, String address, String taxId){
        super("client", username, password, name);
        this.credit = 0.0;
        this.email = email;
        this.address = address;
        this.taxId = taxId;
        this.rates = new ArrayList<>();
    }
    
    public String getEmail() { return email; } 
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; } 
    public void setAddress(String address) { this.address = address; }

    public String getTaxId() { return taxId; } 
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public Double getCredit() { return credit; } 
    public void setCredit(Double credit) { this.credit = credit; }
    
    public List<RateEntity> getRates(){ return rates; }
    public void addRate( RateEntity rate ){
        if(rate != null){
            this.rates.add(rate);
            if(rate.getClient() != this){
                rate.setClient(this);
            }
        }
    }
    public void removeRate( RateEntity rate ){
        if(rate != null){
            this.rates.remove(rate);
        }
    }
    
    public ClientDTO toDTO(){ return new ClientDTO(this.getUsername(), this.getPassword(), this.getName(), this.email, this.address, this.taxId); }
    
    @Override
    public String toString() { return "Client: " + this.getUsername(); }
}
