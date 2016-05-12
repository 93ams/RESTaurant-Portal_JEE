package astielau.restaurantportal.dao;

import astielau.restaurantportal.entities.IngredientEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

@Stateless
public class IngredientDAO {
    @PersistenceContext
    private EntityManager em;
    
    public IngredientDAO (){}
    
    public List<IngredientEntity> getAllIngredients(){
        try {
            return (List<IngredientEntity>) em.createNamedQuery("findAllIngredients")
                                              .getResultList();
        } catch ( Exception e ) {
            System.out.println("Error @ IngredientDAO: getAllIngredients");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity getIngredient( String ingredientName ){
        try {
            return (IngredientEntity) em.find(IngredientEntity.class, ingredientName);
        } catch (NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ IngredientDAO: getIngredient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity getIngredientBySlug( String ingredientSlug ){
        try {
            return (IngredientEntity) em.createNamedQuery("findIngredientBySlug")
                                        .setParameter("ingredientSlug", ingredientSlug)
                                        .getSingleResult();
        } catch (NoResultException e ) {
            System.out.println( e.getMessage() );
        } catch ( Exception e ) {
            System.out.println("Error @ IngredientDAO: getIngredient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public IngredientEntity createIngredient( String ingredientName, String foodType ){
        try {
            IngredientEntity ingredient = getIngredient(ingredientName);
            if(ingredient == null){
                ingredient = new IngredientEntity( ingredientName, foodType );
                em.persist(ingredient);
            }
            return ingredient;
        } catch ( Exception e ) {
            System.out.println("Error @ IngredientDAO: createIngredient");
            System.out.println( e.getMessage() );
        }
        return null;
    }
    
    public void deleteIngredient( String ingredientName ){
        IngredientEntity ingredient = getIngredient( ingredientName );
        if( ingredient != null ){
            try {
                em.remove( ingredient );
            } catch ( Exception e ){
                System.out.println("Error @ IngredientDAO: deleteIngredient");
                System.out.println( e.getMessage() );
            }
        }
    }
}
