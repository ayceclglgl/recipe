package ayc.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ayc.recipe.model.Recipe;
import reactor.core.publisher.Mono;

public interface RecipeRepository extends ReactiveMongoRepository<Recipe, String>{
	Mono<Recipe> findByDescription(String description);
}
