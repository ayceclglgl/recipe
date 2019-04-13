package ayc.recipe.services;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.model.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface RecipeService {
	Flux<Recipe> findAllRecipes();
	Mono<Recipe> findById(String id);
	Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
	Mono<RecipeCommand> findCommandById(String id);
	Mono<Void> deleteById(String id);
}
