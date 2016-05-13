package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.IngredientEntity;
import astielau.restaurantportal.entities.RateEntity;
import astielau.restaurantportal.entities.RestaurantEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-13T15:50:26")
@StaticMetamodel(DishEntity.class)
public class DishEntity_ { 

    public static volatile SingularAttribute<DishEntity, Long> id;
    public static volatile ListAttribute<DishEntity, IngredientEntity> ingredients;
    public static volatile SingularAttribute<DishEntity, Double> price;
    public static volatile SingularAttribute<DishEntity, Double> rate;
    public static volatile SingularAttribute<DishEntity, String> name;
    public static volatile ListAttribute<DishEntity, RateEntity> rates;
    public static volatile SingularAttribute<DishEntity, RestaurantEntity> restaurant;

}