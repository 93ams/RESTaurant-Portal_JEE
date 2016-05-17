package astielau.restaurantportal.settings;

import astielau.restaurantportal.dao.*;
import astielau.restaurantportal.entities.*;
import astielau.restaurantportal.wsclients.invoice.InvoiceDTO;
import astielau.restaurantportal.wsclients.invoice.InvoiceList;
import astielau.restaurantportal.wsclients.invoice.InvoiceRegistryWS;
import astielau.restaurantportal.wsclients.invoice.InvoiceRegistryWS_Service;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckDTO;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckWS;
import astielau.restaurantportal.wsclients.mealcheck.MealCheckWS_Service;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {
    
    @EJB
    ManagerDAO managerDAO;
    
    @EJB
    ClientDAO clientDAO;
    
    @EJB
    RestaurantDAO restaurantDAO;
    
    @EJB
    DishDAO dishDAO;
    
    @EJB
    PurchaseDAO purchaseDAO;
    
    @EJB
    RateDAO rateDAO;
    
    @PostConstruct
    public void runApp(){
        try {
            ManagerEntity manager = managerDAO.registerManager("tilau", "asd", "Tilau");
            ClientEntity client = clientDAO.registerClient("mimi", "asd", "Mimi", "yolo", "KEK", "1234");
    
            RestaurantEntity restaurant = restaurantDAO.createRestaurant("Tasca do Tilau", "PASCUA", manager);
            DishEntity dish = restaurantDAO.addDishToRestaurant(restaurant.getName(), "Lasanha de Soja", 5.00);
            
            PurchaseEntity shoppingCart = purchaseDAO.createShoppingCart(client);
            purchaseDAO.addDishToShoppingCart(client.getUsername(), dish, 69);
            
            rateDAO.createRate(client, dish, 5);
            
            client.setCredit(9999.99);
            
            //purchaseDAO.checkout(client.getUsername());
            
            System.out.println("> " + purchaseDAO.getClientPurchases(client.getUsername()));
            
            InvoiceRegistryWS_Service invoiceRegistryWS = new InvoiceRegistryWS_Service();
            InvoiceRegistryWS invoiceRegistry = invoiceRegistryWS.getInvoiceRegistryWSPort();
            
            InvoiceList invoices = invoiceRegistry.getInvoices("123456789");
            for(InvoiceDTO invoice: invoices.getInvoices()){
                System.out.println("> " + invoice.getEmisorName());
            }
            
            MealCheckWS_Service mealCheckWS = new MealCheckWS_Service();
            MealCheckWS mealCheck = mealCheckWS.getMealCheckWSPort();
            
            MealCheckDTO check = mealCheck.emit(client.getUsername(), 619.69, true);
            System.out.println("> " + check.getId());
            
            
        } catch( Exception e ){
            System.out.println("Error @ ConfigBean");
            System.out.println( e.getMessage() );
        }
    }
}
