package ayc.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ayc.recipe.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
	public Optional<Recipe> findByDescription(String description);
}
