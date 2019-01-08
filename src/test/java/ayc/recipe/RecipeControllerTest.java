package ayc.recipe;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import ayc.recipe.controllers.RecipeController;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.services.RecipeService;

public class RecipeControllerTest {
	
	RecipeController recipeController;
	@Mock
	RecipeService recipeServices;
	@Mock
	RecipeRepository recipeRepository;
	@Mock
	Model m;
		
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeServices);
	}
	
	@Test
	public void mockMVC() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
		mockMvc.perform(get("/recipeList"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipe"));
	}
	
	@Test
	public void getRecipeList() {
		//given
		Set<Recipe> setRecipes = new HashSet<>();
		setRecipes.add(new Recipe());
		setRecipes.add(new Recipe());
		when(recipeServices.findAllRecipes()).thenReturn(setRecipes);
		
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		//when
		String viewName = recipeController.getRecipeList(m);
		
		
		//then		
		assertEquals(viewName, "recipe/recipe");
		verify(recipeServices, times(1)).findAllRecipes();
		//verify(recipeRepository, times(1)).findAll(); //not nested verification
		//verify(m, times(1)).addAttribute(eq("recipes"), anySet());
		verify(m, times(1)).addAttribute(eq("recipes"),argumentCaptor.capture());
		//verify(m).containsAttribute("recipes"); //not working
		assertEquals(argumentCaptor.getValue().size(), 2);
		
		
	}

}
