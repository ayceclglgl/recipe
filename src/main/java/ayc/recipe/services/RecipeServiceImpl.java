package ayc.recipe.services;

import org.springframework.stereotype.Service;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.converters.RecipeCommandToRecipe;
import ayc.recipe.converters.RecipeToRecipeCommand;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
	private final RecipeToRecipeCommand recipeToRecipeCommand;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository,
			RecipeCommandToRecipe recipeCommandToRecipe,
			RecipeToRecipeCommand recipeToRecipeCommand) {
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipe = recipeCommandToRecipe;
		this.recipeToRecipeCommand = recipeToRecipeCommand;
	}
	
	@Override
	public Flux<Recipe> findAllRecipes() {
		return recipeRepository.findAll();
	}

	@Override
	public Mono<Recipe> findById(String id) {
		return recipeRepository.findById(id);
		
	}

	
	@Override
	public Mono<RecipeCommand> findCommandById(String id) {
		return recipeRepository.findById(id)
				.map(recipe -> {
					RecipeCommand recipeCommand = recipeToRecipeCommand.convert(recipe);
					
					recipeCommand.getIngredient().forEach(i -> {
						i.setRecipeId(recipeCommand.getId());
					});
					
					return recipeCommand;
				});
	}

	@Override
	public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
		return recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand))
				.map(recipeToRecipeCommand::convert);
	}

	@Override
	public Mono<Void> deleteById(String id) {
		recipeRepository.deleteById(id).block();
		return Mono.empty();
	}

}
