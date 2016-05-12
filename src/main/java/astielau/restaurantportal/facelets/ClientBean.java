package astielau.restaurantportal.facelets;

import astielau.restaurantportal.dao.PurchaseDAO;
import astielau.restaurantportal.dao.RateDAO;
import astielau.restaurantportal.entities.ClientEntity;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.PurchaseEntity;
import astielau.restaurantportal.entities.RateEntity;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

@SessionScoped
@Named
public class ClientBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @EJB
    RateDAO rateDAO;
    
    @EJB
    PurchaseDAO purchaseDAO;
    
    private Integer rate;
    private Integer quantity;
    
    public ClientBean() { rate = -1; }

    public Integer getRate() { return rate; } 
    public void setRate(Integer rate) { this.rate = rate; }

    public Integer getQuantity() { return quantity; } 
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    private ClientEntity getSessionClient(){
        try {
            HttpSession session = SessionBean.getSession();
            return (ClientEntity) session.getAttribute("user");
        } catch( Exception e ) {
            System.out.println("Error @ ClientBean: getSessionClient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public Integer getRate(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                RateEntity r = rateDAO.getRate(client.getUsername(), dish.getRestaurant().getName(), dish.getId());
                if(r != null){
                    return r.getRate();
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: rateDish");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public String rateDish(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                if(rate > 0){
                    rateDAO.createRate(client, dish, rate);
                    return "dish.xhtml";
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: rateDish");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    public String unrateDish(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                System.out.println("PASCOA");
                rateDAO.deleteRate(client.getUsername(), dish.getRestaurant().getName(), dish.getId());
                return "dish.xhtml";
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: unrateDish");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    private PurchaseEntity getShoppingCart(ClientEntity client){
        try {
            PurchaseEntity shoppingCart = purchaseDAO.getUserShoppingCart(client.getUsername());
            if(shoppingCart == null){
                shoppingCart = purchaseDAO.createShoppingCart(client);
            }
            return shoppingCart;
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: getShoppingCart");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public String addToShoppingCart(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                if(quantity > 0){
                    PurchaseEntity shoppingCart = getShoppingCart(client);
                    purchaseDAO.addDishToShoppingCart(shoppingCart, dish, quantity);
                    quantity = 0;
                } else {
                    errorMessage("You can't buy negative values", "dumbdumb");
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: addToShoppingCart");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    public String removeFromShoppingCart(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                if(quantity > 0){
                    PurchaseEntity shoppingCart = getShoppingCart(client);
                    purchaseDAO.removeDishFromShoppingCart(shoppingCart, dish, quantity);
                } else{
                    errorMessage("I do not care for double negatives", "dumbdumb");
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: removeFromShoppingCart");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    public void checkout(){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                purchaseDAO.checkout(client.getUsername());
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: checkout");
            System.out.println( e.getMessage() );
        }
    }
    
    private void errorMessage( String message, String details ){
        FacesContext.getCurrentInstance().addMessage( null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, message, details));
    }

}
