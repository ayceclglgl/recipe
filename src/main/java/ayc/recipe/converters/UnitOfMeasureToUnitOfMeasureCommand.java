package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureToUnitOfMeasureCommand implements Converter<UnitOfMeasure, UnitOfMeasureCommand>{

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure source) {
		if(source == null) 	return null;
		
		final UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand(); 
		unitOfMeasureCommand.setId(source.getId());
		unitOfMeasureCommand.setUom(source.getUom());
		return unitOfMeasureCommand;
	}

}
