package ayc.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;

@Service
public class RecipeServicesImpl implements RecipeServices{

	private final RecipeRepository recipeRepository;
	
	public RecipeServicesImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public Set<Recipe> findAllRecipes() {
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

}
