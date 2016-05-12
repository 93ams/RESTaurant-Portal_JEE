package astielau.restaurantportal.dao;

import astielau.restaurantportal.cpk.DishPK;
import astielau.restaurantportal.cpk.OrderItemPK;
import astielau.restaurantportal.cpk.PurchasePK;
import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.OrderItemEntity;
import astielau.restaurantportal.entities.PurchaseEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class OrderItemDAO {
    
    @PersistenceContext
    private EntityManager em;
    
    public OrderItemEntity getOrderItem(String username, Long purchaseId, String restaurantName, Long dishId){
        try {
            PurchasePK purchasePK = new PurchasePK(username, purchaseId);
            DishPK dishPK = new DishPK(restaurantName, dishId);
            OrderItemPK orderItemPK = new OrderItemPK(purchasePK, dishPK);
            return (OrderItemEntity) em.find(OrderItemEntity.class, orderItemPK);
        } catch ( Exception e ) {
            System.out.println("Error @ OrderItemDAO: getOrderItem");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public OrderItemEntity createOrderItem(PurchaseEntity purchase, DishEntity dish, Integer quantity){
        try {
            if(purchase != null && dish != null){
                if(getOrderItem(purchase.getClient().getUsername(), purchase.getId(), dish.getRestaurant().getName(), dish.getId()) == null){
                    OrderItemEntity orderItem = new OrderItemEntity(purchase, dish, quantity);
                    em.persist(orderItem);
                    return orderItem;
                }
            }
        } catch ( Exception e ) {
            System.out.println("Error @ OrderItemDAO: createOrderItem");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void deleteOrderItem(String username, Long purchaseId, String restaurantName, Long dishId){
        try {
            OrderItemEntity orderItem = getOrderItem(username, purchaseId, restaurantName, dishId);
            if(orderItem != null){
                em.remove(orderItem);
            }
        } catch ( Exception e ) {
            System.out.println("Error @ OrderItemDAO: deleteOrderItem");
            System.out.println( e.getMessage() );
        }
    }
}
