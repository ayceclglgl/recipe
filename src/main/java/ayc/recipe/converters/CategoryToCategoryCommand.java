package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.CategoryCommand;
import ayc.recipe.model.Category;
import lombok.Synchronized;

@Component
public class CategoryToCategoryCommand implements Converter<Category, CategoryCommand>{

	@Synchronized
    @Nullable
    @Override
	public CategoryCommand convert(Category source) {
		if(source == null) return null;
		
		final CategoryCommand categoryCommand = new CategoryCommand();
		//Recipes??
		//If we implememt categories like ingredients then, we need to add Recipe object.
		//For now, it is skipped.
		categoryCommand.setDescription(source.getDescription());
		categoryCommand.setId(source.getId());
		return categoryCommand;
	}

}
