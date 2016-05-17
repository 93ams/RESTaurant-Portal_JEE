package astielau.restaurantportal.facelets;

import astielau.restaurantportal.dao.ClientDAO;
import astielau.restaurantportal.dao.PurchaseDAO;
import astielau.restaurantportal.dao.RateDAO;
import astielau.restaurantportal.entities.ClientEntity;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.OrderItemEntity;
import astielau.restaurantportal.entities.PurchaseEntity;
import astielau.restaurantportal.entities.RateEntity;
import astielau.restaurantportal.wsclients.invoice.InvoiceDTO;
import astielau.restaurantportal.wsclients.invoice.InvoiceList;
import astielau.restaurantportal.wsclients.invoice.InvoiceRegistryWS;
import astielau.restaurantportal.wsclients.invoice.InvoiceRegistryWS_Service;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckDTO;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckWS;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckWS_Service;
import java.io.Serializable;
import java.util.List;
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
    
    @EJB
    ClientDAO clientDAO;
    
    private Integer rate;
    private Integer quantity;
    private Integer[] array;
    
    InvoiceRegistryWS invoiceRegistry;
    MealCheckWS mealCheck;
    
    public ClientBean() { 
        InvoiceRegistryWS_Service invoiceRegistryWS = new InvoiceRegistryWS_Service();
        invoiceRegistry = invoiceRegistryWS.getInvoiceRegistryWSPort();
        
        MealCheckWS_Service mealCheckWS = new MealCheckWS_Service();
        mealCheck = mealCheckWS.getMealCheckWSPort();
        rate = -1; 
    }

    public Integer getRate() { return rate; } 
    public void setRate(Integer rate) { this.rate = rate; }

    public Integer getQuantity() { return quantity; } 
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer[] getArray() { return array; }
     
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
    
    public PurchaseEntity getShoppingCart(){
        try {
            ClientEntity client = getSessionClient();
            PurchaseEntity shoppingCart = purchaseDAO.getUserShoppingCart(client.getUsername());
            if(shoppingCart == null){
                shoppingCart = purchaseDAO.createShoppingCart(client);
            }
            this.array = new Integer[shoppingCart.getItems().size()];
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
                    purchaseDAO.addDishToShoppingCart(client.getUsername(), dish, quantity);
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
    
    public String removeFromShoppingCart(OrderItemEntity item, Integer quantity){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                if(quantity <= item.getQuantity()){
                    purchaseDAO.removeDishFromShoppingCart(client.getUsername(), item.getDish(), quantity);
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
    
    public List<InvoiceDTO> getClientInvoices(){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                InvoiceList invoices = invoiceRegistry.listInvoices("321654987", client.getTaxId());
                return invoices.getInvoices();
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: getClientInvoices");
            System.out.println( e.getMessage() );
        }
        return null;
    } 
    
    public List<InvoiceDTO> getEmissorInvoices(){
        try {
            InvoiceList invoices = invoiceRegistry.getInvoices("321654987");
            return invoices.getInvoices();
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: getEmissorInvoices");
            System.out.println( e.getMessage() );
        }
        return null;
    } 
    
    public String goToInvoice(InvoiceDTO invoice){
        try {
            HttpSession session = SessionBean.getSession();
            session.setAttribute("invoice", invoice);
            return "invoice.xhtml?faces-redirect=true";
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: goToInvoice");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    public List<MealCheckDTO> getClientMealChecks(){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                return mealCheck.list(client.getUsername(), true);
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: getClientMealChecks");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public String useMealCheck(MealCheckDTO check){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                if(client.getUsername().equals(check.getUsername())){
                    if(!check.isUsed()){
                        Double amount = mealCheck.use(client.getUsername(), check.getId());
                        clientDAO.addCreditToClient(client.getUsername(), amount);
                        client.setCredit(client.getCredit() + amount);
                    }
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: getClientMealChecks");
            System.out.println( e.getMessage() );
        }
        return "";
    }
    
    public String checkout(){
        try {
            ClientEntity client = getSessionClient();
            if(client != null){
                PurchaseEntity shoppingCart = getShoppingCart();
                if((client.getCredit() - shoppingCart.getTotal()) >= 0){
                    purchaseDAO.checkout(client.getUsername());
                    client.setCredit(client.getCredit() - shoppingCart.getTotal());
                    return "shoppingcart.xhtml";
                }
            } else {
                errorMessage("You are not a known client", "toto");
            }
        } catch( Exception e ){
            System.out.println("Error @ ClientBean: checkout");
            System.out.println( e.getMessage() );
        }
        return "";
    }

    private void errorMessage( String message, String details ){
        FacesContext.getCurrentInstance().addMessage( null,
            new FacesMessage(FacesMessage.SEVERITY_WARN, message, details));
    }

}
