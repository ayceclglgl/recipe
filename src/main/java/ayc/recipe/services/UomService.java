package ayc.recipe.services;

import ayc.recipe.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;

public interface UomService {
	Flux<UnitOfMeasureCommand> listAllUoms();

}
