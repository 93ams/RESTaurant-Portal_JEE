package astielau.restaurantportal.entities;

import astielau.restaurantportal.entities.RateEntity;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-17T15:22:42")
@StaticMetamodel(ClientEntity.class)
public class ClientEntity_ extends UserEntity_ {

    public static volatile SingularAttribute<ClientEntity, String> email;
    public static volatile SingularAttribute<ClientEntity, String> address;
    public static volatile SingularAttribute<ClientEntity, String> taxId;
    public static volatile ListAttribute<ClientEntity, RateEntity> rates;
    public static volatile SingularAttribute<ClientEntity, Double> credit;

}