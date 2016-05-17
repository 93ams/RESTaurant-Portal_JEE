package astielau.restaurantportal.entities;

import astielau.restaurantportal.enums.FoodTypeEnum;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-05-17T15:22:42")
@StaticMetamodel(IngredientEntity.class)
public class IngredientEntity_ { 

    public static volatile SingularAttribute<IngredientEntity, String> name;
    public static volatile SingularAttribute<IngredientEntity, String> slug;
    public static volatile SingularAttribute<IngredientEntity, FoodTypeEnum> type;

}