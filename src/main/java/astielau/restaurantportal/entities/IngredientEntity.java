package astielau.restaurantportal.entities;

import astielau.restaurantportal.dto.IngredientDTO;
import astielau.restaurantportal.enums.FoodTypeEnum;
import java.io.Serializable;
import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
    @NamedQuery( name="findAllIngredients", query="SELECT i FROM IngredientEntity i"),
    @NamedQuery( name="findIngredientBySlug", query="SELECT i FROM IngredientEntity i WHERE i.slug = :ingredientSlug")
})
public class IngredientEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id private String name;
    private String slug;
    
    private FoodTypeEnum type;
    
    public IngredientEntity() {}
    public IngredientEntity(String name, String type) {
        this.name = name;
        this.slug = toSlug(name);
        this.type = convertFoodType(type);
    }
    
    public String getName() { return name; } 
    public void setName(String name) { 
        this.name = name; 
        this.slug = toSlug(name);
    }

    public String getSlug() { return slug; }
    
    public String getType() { return type.getType(); }
    public void setType(String type) { this.type = convertFoodType(type); }
    
    public IngredientDTO toDTO(){ return new IngredientDTO(this.name, this.type.getType()); }
    
    private FoodTypeEnum convertFoodType(String type){
        switch(type){
            case "meat":
                return FoodTypeEnum.MEAT;
            case "fish":
                return FoodTypeEnum.FISH;
            case "vegetarian":
                return FoodTypeEnum.VEGETARIAN;
            default:
                return null;
        }
    }
    
    private String toSlug( String value ){
        String nowhitespace = Pattern.compile("[\\s]").matcher(value).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slugified = Pattern.compile("[^\\w-]").matcher(normalized).replaceAll("");
        return slugified.toLowerCase(Locale.ENGLISH);
    }
    
    @Override
    public int hashCode() { return name != null ? name.hashCode() : 0; }

    @Override
    public boolean equals( Object object ) {
        if (!(object instanceof IngredientEntity)) { return false; }
        IngredientEntity other = (IngredientEntity) object;
        return !((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name)));
    }

    @Override
    public String toString() { return "Ingredient: " + name; }
}
