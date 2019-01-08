package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import lombok.Synchronized;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure>{

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		if(source == null)  return null;
		
		final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
		unitOfMeasure.setId(source.getId());
		unitOfMeasure.setUom(source.getUom());
		return unitOfMeasure;
	}

}
