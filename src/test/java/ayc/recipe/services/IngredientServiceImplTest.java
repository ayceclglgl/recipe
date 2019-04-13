package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
import reactor.core.publisher.Mono;

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
        recipe.setId("1");

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId("1");
//        ingredient1.setRecipe(recipe);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId("1");
//        ingredient2.setRecipe(recipe);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId("3");
//        ingredient3.setRecipe(recipe);

        recipe.getIngredient().add(ingredient1);
        recipe.getIngredient().add(ingredient2);
        recipe.getIngredient().add(ingredient3);

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));

        //then
        IngredientCommand ingredientCommand = ingredientService.findByRecipeIdandIngredientId("1", "3").block();

        //when
        assertEquals("3", ingredientCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
	}
	@Test
	public void testFindByRecipeIdandIngredientId2() {
		String id = "1";
		Recipe recipe = new Recipe();
		recipe.setId(id);
		
		Ingredient ing1 = new Ingredient();
		ing1.setId("1");
		Ingredient ing2 = new Ingredient();
		ing2.setId("2");
		Ingredient ing3 = new Ingredient();
		ing3.setId("3");
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		recipe.addIngredients(ing3);
		
		when(recipeRepository.findById(id)).thenReturn(Mono.just(recipe));
		
		IngredientCommand ingredientCommand = ingredientService.findByRecipeIdandIngredientId(id,"3").block();
		
		assertNotNull(ingredientCommand);
		verify(recipeRepository).findById(id);
		
	}
	
	@Test
	public void testUpdateIngredientOfRecipe() {
		Recipe savedRecipe = new Recipe();
		savedRecipe.setId("1");
		
		Ingredient ing = new Ingredient();
		ing.setId("1");
		ing.setAmount(BigDecimal.valueOf(250));
		
		savedRecipe.addIngredients(ing);
		
		
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId("1");
		uom.setUom("Cup");
		
		Ingredient ing1 = new Ingredient();
		ing1.setId("1");
		ing1.setAmount(BigDecimal.valueOf(100));
		ing1.setUom(uom);
		Ingredient ing2 = new Ingredient();
		ing2.setId("2");
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		
		
		UnitOfMeasureCommand uomCommand = new UnitOfMeasureCommand();
		uomCommand.setId("1");
		uomCommand.setUom("Cup");
		
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId("1");
		ingredientCommand.setAmount(BigDecimal.valueOf(250));
		ingredientCommand.setRecipeId(recipe.getId());
		ingredientCommand.setUom(uomCommand);
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));
		when(unitOfMeasureRepository.findById(anyString())).thenReturn(Mono.just(uom));
		
		IngredientCommand updatedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand).block();
		
		assertNotNull(updatedIngredientCommand);
		assertEquals("1", updatedIngredientCommand.getId());
		verify(recipeRepository).findById(anyString());
		verify(recipeRepository).save(any(Recipe.class));
		
	}
	
	@Test
	public void testUpdateIngredientOfRecipeAddNewIngredient() {
		//given
        IngredientCommand command = new IngredientCommand();
        command.setId("3");
        command.setRecipeId("2");

        Recipe savedRecipe = new Recipe();
        savedRecipe.addIngredients(new Ingredient());
        savedRecipe.getIngredient().iterator().next().setId("3");

        when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(Mono.just(savedRecipe));

        //when
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command).block();

        //then
        assertNotNull(savedCommand);
        assertEquals("3", savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyString());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
	}
	
	
	@Test
	public void testDeleteIngredientOfRecipe() {
		
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		Ingredient ing1 = new Ingredient();
		ing1.setId("1");
		ing1.setAmount(BigDecimal.valueOf(100));
		Ingredient ing2 = new Ingredient();
		ing2.setId("2");
		
		recipe.addIngredients(ing1);
		recipe.addIngredients(ing2);
		
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		when(recipeRepository.save(any(Recipe.class))).thenReturn(Mono.just(recipe));
		
		ingredientService.deleteIngredientOfRecipe("1", "2");
		
		verify(recipeRepository).findById(anyString());
		verify(recipeRepository).save(any(Recipe.class));
		
		
	}
	
}
