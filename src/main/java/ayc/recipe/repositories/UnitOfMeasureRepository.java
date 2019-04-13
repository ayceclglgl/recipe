package ayc.recipe.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import ayc.recipe.model.UnitOfMeasure;
import reactor.core.publisher.Mono;

public interface UnitOfMeasureRepository extends ReactiveMongoRepository<UnitOfMeasure, String> {
	Mono<UnitOfMeasure> findByUom(String uom);
}
