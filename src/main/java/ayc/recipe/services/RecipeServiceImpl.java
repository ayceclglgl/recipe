package ayc.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.converters.RecipeCommandToRecipe;
import ayc.recipe.converters.RecipeToRecipeCommand;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;

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
	public Set<Recipe> findAllRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if(!recipe.isPresent())
			throw new RuntimeException("Recipe Not Found");
		
		return recipe.get();
	}

	@Override
	public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
		Recipe savedRecipe = recipeRepository.save(recipeCommandToRecipe.convert(recipeCommand));
		if(savedRecipe.getId() == null) 
			throw new RuntimeException("RecipeCommand is Null");
		
		return recipeToRecipeCommand.convert(savedRecipe);
	}

	
	@Override
	public RecipeCommand findCommandById(Long id) {
		return	recipeToRecipeCommand.convert(findById(id));
	}

}
