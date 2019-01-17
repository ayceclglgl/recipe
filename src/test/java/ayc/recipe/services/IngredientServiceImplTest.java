package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.converters.IngredientCommandToIngredient;
import ayc.recipe.converters.IngredientToIngredientCommand;
import ayc.recipe.converters.UnitOfMeasureCommandToUnitOfMeasure;
import ayc.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ayc.recipe.model.Ingredient;
import ayc.recipe.model.Recipe;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.repositories.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	@Mock
	private RecipeRepository recipeRepository;
	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;
 
	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private IngredientCommandToIngredient ingredientCommandToIngredient;
	private IngredientService ingredientService;
	
	
	public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
		this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		ingredientService = new IngredientServiceImpl(recipeRepository,
				ingredientToIngredientCommand,
				ingredientCommandToIngredient,
				unitOfMeasureRepository);
	}
	
	@Test
	public void testFindByRecipeIdandIngredientId() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);
        ingredient1.setRecipe(recipe);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(1L);
        ingredient2.setRecipe(recipe);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);
        ingredient3.setRecipe(recipe);

        recipe.getIngredient().add(ingredient1);
        recipe.getIngredient().add(ingredient2);
        recipe.getIngredient().add(ingredient3);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdandIngredientId(1L, 3L);

        //when
        assertEquals(Long.valueOf(3L), ingredientCommand.getId());
        assertEquals(Long.valueOf(1L), ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
	}
	@Test
	public void testFindByRecipeIdandIngredientId2() {
		Long id = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		Ingredient ing1 = new Ingredient();
		ing1.setId(1L);
		Ingredient ing2 = new Ingredient();
		ing2.setId(2L);
		Ingredient ing3 = new Ingredient();
		ing3.setId(3L);
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		recipe.addIngredients(ing3);
		
		when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
		
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdandIngredientId(id,3L);
		
		assertNotNull(ingredientCommand);
		verify(recipeRepository).findById(id);
		
	}
	
	@Test
	public void testUpdateIngredientOfRecipe() {
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId(1L);
		
		Ingredient ing = new Ingredient();
		ing.setId(1L);
		ing.setAmount(BigDecimal.valueOf(250));
		
		savedRecipe.addIngredients(ing);
		
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(1L);
		uom.setUom("Cup");
		
		Ingredient ing1 = new Ingredient();
		ing1.setId(1L);
		ing1.setAmount(BigDecimal.valueOf(100));
		ing1.setUom(uom);
		Ingredient ing2 = new Ingredient();
		ing2.setId(2L);
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId(1L);
		uomCommand.setUom("Cup");
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		ingredientCommand.setAmount(BigDecimal.valueOf(250));
		ingredientCommand.setRecipeId(recipe.getId());
		ingredientCommand.setUom(uomCommand);
		
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		when(unitOfMeasureRepository.findById(anyLong())).thenReturn(Optional.of(uom));
		
		IngredientCommand updatedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		
		assertNotNull(updatedIngredientCommand);
		assertEquals(Long.valueOf(1L), updatedIngredientCommand.getId());
		verify(recipeRepository).findById(anyLong());
		verify(recipeRepository).save(any(Recipe.class));
		
	}
	
	@Test
	public void testUpdateIngredientOfRecipeAddNewIngredient() {
		//given
        IngredientCommand command = new IngredientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredients(new Ingredient());
        savedRecipe.getIngredient().iterator().next().setId(3L);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //then
        assertNotNull(savedCommand);
        assertEquals(Long.valueOf(3L), savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
	
	
	@Test
	public void testDeleteIngredientOfRecipe() {
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ing1 = new Ingredient();
		ing1.setId(1L);
		ing1.setAmount(BigDecimal.valueOf(100));
		Ingredient ing2 = new Ingredient();
		ing2.setId(2L);
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		
		
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		
		ingredientService.deleteIngredientOfRecipe(Long.valueOf(1L), Long.valueOf(2L));
		
		verify(recipeRepository).findById(any());
		verify(recipeRepository).save(any(Recipe.class));
		
		
	}
	
}
