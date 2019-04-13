package ayc.recipe.services;

import ayc.recipe.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
	Mono<IngredientCommand> findByRecipeIdandIngredientId(String recipeId, String id);
	Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
	Mono<Void> deleteIngredientOfRecipe(String recipeId, String id);

}
