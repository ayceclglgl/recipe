package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.converters.RecipeCommandToRecipe;
import ayc.recipe.converters.RecipeToRecipeCommand;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.services.RecipeService;
import ayc.recipe.services.RecipeServiceImpl;

public class RecipeServicesImplTest {
	
	RecipeService recipeService;
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	RecipeCommandToRecipe recipeCommandToRecipe;
	@Mock
	RecipeToRecipeCommand recipeToRecipeCommand;
	
	
	Long id = 1L;
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
		HashSet<Recipe> set = new HashSet<Recipe>();
		set.add(recipe);
		
		when(recipeService.findAllRecipes()).thenReturn(set);
		
		Set<Recipe> recipes = recipeService.findAllRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
	}

	
	@Test
	public void getRecipeById() {
		Optional<Recipe> optionalRecipe = Optional.of(recipe);
		
		when(recipeRepository.findById(any())).thenReturn(optionalRecipe);
		
		Recipe returnedRecipe = recipeService.findById(id);
		assertNotNull(returnedRecipe);
		assertEquals(id, returnedRecipe.getId());
		verify(recipeRepository).findById(id);
		verify(recipeRepository, never()).findAll();
	}
	
	@Test
	public void saveRecipeCommand() {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		recipeCommand.setDescription("test");
		
		
//		when(recipeCommandToRecipe.convert(any())).thenReturn(recipe);
//		when(recipeToRecipeCommand.convert(any())).thenReturn(recipeCommand);
		when(recipeRepository.save(any())).thenReturn(recipe);
		
		RecipeCommand savedRC = recipeService.saveRecipeCommand(recipeCommand);
//		assertNotNull(savedRC);
//		assertEquals(id, savedRC.getId());
		verify(recipeCommandToRecipe).convert(recipeCommand);
		verify(recipeToRecipeCommand).convert(recipe);
	}

	@Test
	public void findCommandById() {
		when(recipeRepository.findById(id)).thenReturn(Optional.of(recipe));
		
		RecipeCommand savedRC = recipeService.findCommandById(id);
		assertNull(savedRC);
		verify(recipeToRecipeCommand).convert(any());
	}
}
