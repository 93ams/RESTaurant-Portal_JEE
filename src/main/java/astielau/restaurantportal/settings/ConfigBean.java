package astielau.restaurantportal.settings;

import astielau.restaurantportal.dao.*;
import astielau.restaurantportal.entities.*;
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
            purchaseDAO.addDishToShoppingCart(shoppingCart, dish, 69);
            
            rateDAO.createRate(client, dish, 5);
            
            client.setCredit(9999.99);
            
            //purchaseDAO.checkout(client.getUsername());
            
            System.out.println("> " + purchaseDAO.getClientPurchases(client.getUsername()));
            
        } catch( Exception e ){
            System.out.println("Error @ ConfigBean");
            System.out.println( e.getMessage() );
        }
    }
}
