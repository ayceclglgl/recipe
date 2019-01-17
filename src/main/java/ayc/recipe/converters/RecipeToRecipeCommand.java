package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.model.Recipe;
import lombok.Synchronized;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand>{
	
	private final IngredientToIngredientCommand ingridientConverter;
	private final CategoryToCategoryCommand categoryConverter;
	private final NotesToNotesCommand notesConverter;
	
	
	public RecipeToRecipeCommand(IngredientToIngredientCommand ingridientConverter, CategoryToCategoryCommand categoryConverter,
			NotesToNotesCommand notesConverter) {
		this.ingridientConverter = ingridientConverter;
		this.categoryConverter = categoryConverter;
		this.notesConverter = notesConverter;
		
	}
	

	@Synchronized
    @Nullable
    @Override
	public RecipeCommand convert(Recipe source) {
		if(source == null) return null;
		
		final RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setCookTime(source.getCookTime());
		recipeCommand.setDescription(source.getDescription());
		recipeCommand.setDifficulty(source.getDifficulty());
		recipeCommand.setDirections(source.getDirections());
		recipeCommand.setId(source.getId());
		//image ??
		recipeCommand.setPrepTime(source.getPrepTime());
		recipeCommand.setServings(source.getServings());
		recipeCommand.setSource(source.getSource());
		recipeCommand.setUrl(source.getUrl());
		
		if(source.getCategories() != null && source.getCategories().size() > 0) {
			source.getCategories().forEach(cat -> recipeCommand.getCategories().add(categoryConverter.convert(cat)));
		}
		
		if(source.getIngredient() != null && source.getIngredient().size() > 0) {
			source.getIngredient().forEach(ing -> recipeCommand.getIngredient().add(ingridientConverter.convert(ing)));
		}
		
		if(source.getNotes() != null) {
			recipeCommand.setNotes(notesConverter.convert(source.getNotes()));
		}
		
		return recipeCommand;
	}

}
