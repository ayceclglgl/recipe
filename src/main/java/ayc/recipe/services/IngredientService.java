package ayc.recipe.services;

import ayc.recipe.commands.IngredientCommand;

public interface IngredientService {
	IngredientCommand findByRecipeIdandIngredientId(String recipeId, String id);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	void deleteIngredientOfRecipe(String recipeId, String id);

}
