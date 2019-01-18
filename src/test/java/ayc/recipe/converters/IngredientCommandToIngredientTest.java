package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.model.Ingredient;

public class IngredientCommandToIngredientTest {
	
	IngredientCommandToIngredient ingredientCommandToIngredient;
	
	@Before
	public void setUp() {
		ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Test
	public void testNullObject() {
		assertNull(ingredientCommandToIngredient.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(ingredientCommandToIngredient.convert(new IngredientCommand()));
	}
	
	
	@Test
	public void testConvert() {
		//given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(2L);
		ingredientCommand.setAmount(BigDecimal.valueOf(100));
		ingredientCommand.setDescription("description");
		ingredientCommand.setRecipeId(1L);
		UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
		uom.setId(1L);
		ingredientCommand.setUom(uom);
		
		//when
		Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
		
		//then
		assertEquals(ingredientCommand.getId(), ingredient.getId());
		assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
		assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
		assertEquals(ingredientCommand.getRecipeId(), ingredient.getRecipe().getId());
		assertEquals(ingredientCommand.getUom().getId(), ingredient.getUom().getId());
		
	}
	
	 @Test
	public void convertWithNullUOM() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(2L);
		ingredientCommand.setAmount(BigDecimal.valueOf(100));
		ingredientCommand.setDescription("description");
		ingredientCommand.setRecipeId(1L);

		// when
		Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

		// then
		assertNotNull(ingredient);
		assertNull(ingredient.getUom());
		assertEquals(ingredientCommand.getId(), ingredient.getId());
		assertEquals(ingredientCommand.getAmount(), ingredient.getAmount());
		assertEquals(ingredientCommand.getDescription(), ingredient.getDescription());
		assertEquals(ingredientCommand.getRecipeId(), ingredient.getRecipe().getId());
	}
	
	

}
