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
    
    public ClientBean() {}
    
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
                RateEntity rate = rateDAO.getRate(client.getUsername(), dish.getRestaurant().getName(), dish.getId());
                if(rate != null){
                    return rate.getRate();
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
    
    public void rateDish(DishEntity dish, Integer rate){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                rateDAO.createRate(client, dish, rate);
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: rateDish");
            System.out.println( e.getMessage() );
        }
    }
    
    public void unrateDish(DishEntity dish){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: unrateDish");
            System.out.println( e.getMessage() );
        }
    }
    
    public void addToShoppingCart(DishEntity dish, Integer quantity){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                PurchaseEntity shoppingCart = purchaseDAO.getUserShoppingCart(client.getUsername());
                if(shoppingCart == null){
                    shoppingCart = purchaseDAO.createShoppingCart(client);
                }
                purchaseDAO.addDishToShoppingCart(shoppingCart, dish, quantity);
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: addToShoppingCart");
            System.out.println( e.getMessage() );
        }
    }
    
    public void removeFromShoppingCart(DishEntity dish, Integer quantity){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: removeFromShoppingCart");
            System.out.println( e.getMessage() );
        }
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
    
    public String goToShoppingCart(){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                return "shoppingCart.xhtml";
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: goToShoppingCart");
            System.out.println( e.getMessage() );
        }
        return "index.xhtml";
    }
    
    private void errorMessage( String message, String details ){
        FacesContext.getCurrentInstance().addMessage( null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, message, details));
    }

}
