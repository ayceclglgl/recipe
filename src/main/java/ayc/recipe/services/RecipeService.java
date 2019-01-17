package ayc.recipe.services;

import java.util.Set;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.model.Recipe;


public interface RecipeService {
	Set<Recipe> findAllRecipes();
	Recipe findById(Long id);
	RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
	RecipeCommand findCommandById(Long id);
	void deleteById(Long id);
}
