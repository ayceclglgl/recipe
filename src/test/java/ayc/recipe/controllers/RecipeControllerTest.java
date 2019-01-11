package ayc.recipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
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

import ayc.recipe.commands.RecipeCommand;
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
	
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeServices);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build(); 
	}
	
	@Test
	public void mockMVC() throws Exception {
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
	
	
	@Test
	public void showById() throws Exception {
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		
		when(recipeServices.findById(any())).thenReturn(recipe1);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isOk())	
		.andExpect(view().name("recipe/show"))
		.andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	public void newRecipe() throws Exception {
		mockMvc.perform(get("/recipe/new"))
		.andExpect(model().attributeExists("recipe"))
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(status().isOk());
	}

	
	@Test
	public void updateRecipe() throws Exception {
		Long id = 1L;
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		
		when(recipeServices.findCommandById(id)).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	
	@Test
	public void saveOrUpdateRecipe() throws Exception {
		Long id = 1L;
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		when(recipeServices.saveRecipeCommand(any())).thenReturn(recipeCommand);
		
		mockMvc.perform(post("/recipe"))
		.andExpect(view().name("redirect:/recipe/1/show"))
		.andExpect(status().isFound());
		
	}
}
