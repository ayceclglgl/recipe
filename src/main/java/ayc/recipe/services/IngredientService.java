package ayc.recipe.services;

import ayc.recipe.commands.IngredientCommand;

public interface IngredientService {

	IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long id);
	IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
	void deleteIngredientOfRecipe(Long recipeId, Long id);

}
