package ayc.recipe;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.services.RecipeService;
import ayc.recipe.services.RecipeServiceImpl;

public class RecipeServicesImplTest {
	
	RecipeService receipeService;
	@Mock
	RecipeRepository recipeRepository;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		receipeService = new RecipeServiceImpl(recipeRepository);
	}
	
	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
		HashSet set = new HashSet();
		set.add(recipe);
		
		when(receipeService.findAllRecipes()).thenReturn(set);
		
		Set<Recipe> recipes = receipeService.findAllRecipes();
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
		
	}
	

}
