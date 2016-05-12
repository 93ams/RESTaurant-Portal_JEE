package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.PurchaseEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-11T17:23:27")
@StaticMetamodel(OrderItemEntity.class)
public class OrderItemEntity_ { 

    public static volatile SingularAttribute<OrderItemEntity, DishEntity> dish;
    public static volatile SingularAttribute<OrderItemEntity, PurchaseEntity> purchase;
    public static volatile SingularAttribute<OrderItemEntity, Integer> quantity;

}