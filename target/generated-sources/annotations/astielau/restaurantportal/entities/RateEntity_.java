package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.ClientEntity;
import astielau.restaurantportal.entities.DishEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-11T17:23:27")
@StaticMetamodel(RateEntity.class)
public class RateEntity_ { 

    public static volatile SingularAttribute<RateEntity, DishEntity> dish;
    public static volatile SingularAttribute<RateEntity, Integer> rate;
    public static volatile SingularAttribute<RateEntity, ClientEntity> client;

}