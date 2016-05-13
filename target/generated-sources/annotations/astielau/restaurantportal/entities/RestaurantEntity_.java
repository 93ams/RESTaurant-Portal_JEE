package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.DishEntity;
import astielau.restaurantportal.entities.ManagerEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-13T15:50:26")
@StaticMetamodel(RestaurantEntity.class)
public class RestaurantEntity_ { 

    public static volatile SingularAttribute<RestaurantEntity, Double> rate;
    public static volatile ListAttribute<RestaurantEntity, ManagerEntity> managers;
    public static volatile SingularAttribute<RestaurantEntity, String> address;
    public static volatile SingularAttribute<RestaurantEntity, String> name;
    public static volatile ListAttribute<RestaurantEntity, DishEntity> dishes;
    public static volatile SingularAttribute<RestaurantEntity, String> slug;

}