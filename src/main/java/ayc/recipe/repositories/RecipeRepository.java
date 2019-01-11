package ayc.recipe.repositories;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import ayc.recipe.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

	public Optional<Recipe> findByCategories(Set categories);
	public Optional<Recipe> findByDescription(String description);
}
