package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.converters.RecipeCommandToRecipe;
import ayc.recipe.converters.RecipeToRecipeCommand;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class RecipeServicesImplTest {
	
	RecipeService recipeService;
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	
	String id = "1";
	Recipe recipe;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
		recipe = new Recipe();
		recipe.setId(id);
		recipe.setDescription("test");
	}
	
	@Test
	public void getRecipes() {
		
		when(recipeService.findAllRecipes()).thenReturn(Flux.just(recipe));
		long count = recipeService.findAllRecipes().count().block();
		
		assertEquals(1L, count);
		verify(recipeRepository, times(1)).findAll();
	}

	
	@Test
	public void getRecipeById() {
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(recipe));
		
		Recipe returnedRecipe = recipeService.findById(id).block();
		assertNotNull(returnedRecipe);
		assertEquals(id, returnedRecipe.getId());
		verify(recipeRepository).findById(id);
		verify(recipeRepository, never()).findAll();
	}
	
//	@Test(expected = NotFoundException.class)
	public void getRecipeByIdTestNotFound() {
		
		when(recipeRepository.findById(anyString())).thenReturn(Mono.just(new Recipe()));
		
		Recipe returnedRecipe = recipeService.findById(id).block();
		assertEquals(returnedRecipe.getId(), null);
	}
	
	@Test
	public void saveRecipeCommand() {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		recipeCommand.setDescription("test");
		recipeCommand.setDirections("directions");
		recipeCommand.setIngredient(new ArrayList<>());
		recipeCommand.setCategories(new ArrayList<>());
		
		
//		when(recipeCommandToRecipe.convert(any())).thenReturn(recipe);
//		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);
		when(recipeRepository.save(any())).thenReturn(Mono.just(recipe));
		
		Recipe recipe = recipeRepository.save(any()).block();

		assertNotNull(recipe);
		assertEquals(id, recipe.getId());
//		verify(recipeCommandToRecipe).convert(recipeCommand);
//		verify(recipeToRecipeCommand).convert(recipe);
	}

	@Test
	public void findCommandById() {
		when(recipeRepository.findById(id)).thenReturn(Mono.just(recipe));
		
		Recipe recipe = recipeRepository.findById(id).block();
		assertNotNull(recipe);
	}
	
	@Test
	public void deleteById() {
		when(recipeRepository.deleteById(anyString())).thenReturn(Mono.empty());
		recipeService.deleteById(id);
		verify(recipeRepository).deleteById(anyString());
	}
}
