package ayc.recipe.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;

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
	public Set<UnitOfMeasureCommand> listAllUoms() {
		Set<UnitOfMeasureCommand> uomcSet = new HashSet<UnitOfMeasureCommand>();
		
		Iterable<UnitOfMeasure> set = unitOfMeasureRepository.findAll();
		set.forEach(uom -> uomcSet.add(unitOfMeasureToUnitOfMeasureCommand.convert(uom)));
		
		return uomcSet;
	}

}
