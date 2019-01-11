package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.model.Recipe;
import lombok.Synchronized;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe>{

	private final CategoryCommandToCategory categoryConveter;
    private final IngredientCommandToIngredient ingredientConverter;
    private final NotesCommandToNotes notesConverter;

    public RecipeCommandToRecipe(CategoryCommandToCategory categoryConveter, IngredientCommandToIngredient ingredientConverter,
                                 NotesCommandToNotes notesConverter) {
        this.categoryConveter = categoryConveter;
        this.ingredientConverter = ingredientConverter;
        this.notesConverter = notesConverter;
    }
    
	@Synchronized
    @Nullable
    @Override
	public Recipe convert(RecipeCommand source) {
		if(source == null) return null;
		
		final Recipe recipe = new Recipe();
		recipe.setCookTime(source.getCookTime());
		recipe.setDescription(source.getDescription());
		recipe.setDifficulty(source.getDifficulty());
		recipe.setDirections(source.getDirections());
		recipe.setId(source.getId());
		//image ??
		recipe.setPrepTime(source.getPrepTime());
		recipe.setServings(source.getServings());
		recipe.setSource(source.getSource());
		recipe.setUrl(source.getUrl());
		
		if(source.getCategories() != null && source.getCategories().size() > 0 ) {
			source.getCategories().forEach(cat -> recipe.getCategories().add(categoryConveter.convert(cat)));
		}
		
		if(source.getIngredient() != null && source.getIngredient().size() > 0) {
			source.getIngredient().forEach(ing -> recipe.getIngrident().add(ingredientConverter.convert(ing)));
		}
		
		if(source.getNotes() != null) {
			recipe.setNotes(notesConverter.convert(source.getNotes()));
		}
		
		return recipe;
	}

}
