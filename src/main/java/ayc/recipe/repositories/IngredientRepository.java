package ayc.recipe.repositories;

import org.springframework.data.repository.CrudRepository;

import ayc.recipe.model.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, Long>{

}
