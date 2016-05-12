package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.ClientEntity;
import astielau.restaurantportal.entities.OrderItemEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-11T17:23:27")
@StaticMetamodel(PurchaseEntity.class)
public class PurchaseEntity_ { 

    public static volatile SingularAttribute<PurchaseEntity, Long> id;
    public static volatile SingularAttribute<PurchaseEntity, Double> total;
    public static volatile SingularAttribute<PurchaseEntity, ClientEntity> client;
    public static volatile ListAttribute<PurchaseEntity, OrderItemEntity> items;
    public static volatile SingularAttribute<PurchaseEntity, Boolean> payed;

}