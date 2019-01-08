package ayc.recipe.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;

@Service
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository recipeRepository;
	
	public RecipeServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
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

}
