package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.CategoryCommand;
import ayc.recipe.model.Category;
import lombok.Synchronized;

@Component
public class CategoryCommandToCategory implements Converter<CategoryCommand, Category> {

	@Synchronized
    @Nullable
    @Override
	public Category convert(CategoryCommand source) {
		if (source == null) return null;
		
		final Category category = new Category();
		category.setId(source.getId());
		category.setDescription(source.getDescription());
		//Recipes??
		//If we implememt categories like ingredients then, we need to add Recipe object.
		//For now, it is skipped.
		return category;
	}

}
