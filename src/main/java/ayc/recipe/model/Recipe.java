package ayc.recipe.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Recipe {
	@Id
	private String id;
	private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    Difficulty difficulty;
    private Byte[] image; 
    private Notes notes;
    private Set<Ingredient> ingredient = new HashSet<>();
    @DBRef
    private Set<Category> categories = new HashSet<>();
	
    
    public Recipe addIngredients(Ingredient ingredient) {
		this.getIngredient().add(ingredient);
//		ingredient.setRecipe(this);
		return this;
	}
    
}
