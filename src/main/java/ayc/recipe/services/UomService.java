package ayc.recipe.services;

import java.util.Set;

import ayc.recipe.commands.UnitOfMeasureCommand;

public interface UomService {
	Set<UnitOfMeasureCommand> listAllUoms();

}
