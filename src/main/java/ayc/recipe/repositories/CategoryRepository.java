package ayc.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ayc.recipe.model.Category;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
	Mono<Category> findByDescription(String description);
}

