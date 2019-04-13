package ayc.recipe.services;

import org.springframework.stereotype.Service;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;
import reactor.core.publisher.Flux;

@Service
public class UomServiceImpl implements UomService{

	private UnitOfMeasureRepository unitOfMeasureRepository; 
	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	public UomServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, 
			UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommand = unitOfMeasureToUnitOfMeasureCommand;
	}
	@Override
	public Flux<UnitOfMeasureCommand> listAllUoms() {
		Flux<UnitOfMeasure> uomFlux = unitOfMeasureRepository.findAll();
		return uomFlux.map(unitOfMeasureToUnitOfMeasureCommand::convert);
	}

}
