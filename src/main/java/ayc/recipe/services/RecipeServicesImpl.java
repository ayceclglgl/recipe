package ayc.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecipeServicesImpl implements RecipeServices{

	private final RecipeRepository recipeRepository;
	
	public RecipeServicesImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}
	
	@Override
	public Set<Recipe> findAllRecipes() {
		log.debug("@Slf4j - RecipeServicesImpl - findAllRecipes");
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		return recipeSet;
	}

}
