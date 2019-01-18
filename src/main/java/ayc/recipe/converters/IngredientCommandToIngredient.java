package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.model.Ingredient;
import ayc.recipe.model.Recipe;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient>{

	private final UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure uomConverter) {
        this.uomConverter = uomConverter;
    }
	
//	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null)	return null;
		
		final Ingredient ingredient= new Ingredient();
		ingredient.setAmount(source.getAmount());
		ingredient.setDescription(source.getDescription());
		ingredient.setId(source.getId());
		ingredient.setUom(uomConverter.convert(source.getUom()));
		if(source.getRecipeId() != null) {
			Recipe recipe = new Recipe();
			recipe.setId(source.getRecipeId());
			ingredient.setRecipe(recipe);
			recipe.addIngredients(ingredient);
		}
		return ingredient;
	}

}
