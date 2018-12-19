package ayc.recipe.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import ayc.recipe.model.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
	Optional<UnitOfMeasure> findByUom(String uom);
}
