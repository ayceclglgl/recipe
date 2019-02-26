package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.model.Ingredient;
import ayc.recipe.model.Recipe;
import ayc.recipe.model.UnitOfMeasure;

public class IngredientToIngredientCommandTest {
	
	IngredientToIngredientCommand ingredientToIngredientCommand;
	
	@Before
	public void setUp() {
		ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}
	
	@Test
	public void testNullObject() {
		assertNull(ingredientToIngredientCommand.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(ingredientToIngredientCommand.convert(new Ingredient()));
	}
	
	
	@Test
	public void testConvert() {
		//given
		Ingredient ingredient = new Ingredient();
		ingredient.setId("2");
		ingredient.setAmount(BigDecimal.valueOf(100));
		ingredient.setDescription("description");
		Recipe recipe = new Recipe();
		recipe.setId("1");
//		ingredient.setRecipe(recipe);
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId("1");
		ingredient.setUom(uom);
		
		//when
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);
		
		//then
		assertEquals(ingredient.getId(), ingredientCommand.getId());
		assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
		assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
//		assertEquals(ingredient.getRecipe().getId(), ingredientCommand.getRecipeId());
		assertEquals(ingredient.getUom().getId(), ingredientCommand.getUom().getId());
		
	}
	
	 @Test
	public void convertWithNullUOM() throws Exception {
		// given
		Ingredient ingredient = new Ingredient();
		ingredient.setId("2");
		ingredient.setAmount(BigDecimal.valueOf(100));
		ingredient.setDescription("description");
		Recipe recipe = new Recipe();
		recipe.setId("1");
//		ingredient.setRecipe(recipe);

		// when
		IngredientCommand ingredientCommand = ingredientToIngredientCommand.convert(ingredient);

		// then
		assertNotNull(ingredientCommand);
		assertNull(ingredientCommand.getUom());
		assertEquals(ingredient.getId(), ingredientCommand.getId());
		assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
		assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
//		assertEquals(ingredient.getRecipe().getId(), ingredientCommand.getRecipeId());
	}
	
	

}
