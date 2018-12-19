package ayc.recipe.services;

import java.util.Set;

import ayc.recipe.model.Recipe;


public interface RecipeServices {
	Set<Recipe> findAllRecipes();
	
}
