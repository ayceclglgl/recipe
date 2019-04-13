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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.controllers.RecipeController;
import ayc.recipe.exceptions.NotFoundException;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.services.RecipeService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController)
				.setControllerAdvice(new ControllerExceptionHandler())
				.build(); 
	}
	
	@Test
	public void mockMVC() throws Exception {
		when(recipeServices.findAllRecipes()).thenReturn(Flux.empty());
		
		mockMvc.perform(get("/recipeList"))
			.andExpect(status().isOk())
			.andExpect(view().name("recipe/recipe"));
	}
	
	@Test
	public void testShowById() throws Exception {
		Recipe recipe1 = new Recipe();
		recipe1.setId("1");
		
		when(recipeServices.findById(any())).thenReturn(Mono.just(recipe1));
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isOk())	
		.andExpect(view().name("recipe/show"))
		.andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	public void testShowByIdTestNotFound() throws Exception {
		when(recipeServices.findById(any())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isNotFound())
		.andExpect(view().name("404error"));
		
	}
	

	@Test
	public void testDeleteById() throws Exception {
		when(recipeServices.deleteById(anyString())).thenReturn(Mono.empty());
		
		mockMvc.perform(get("/recipe/1/delete"))
		.andExpect(view().name("redirect:/recipeList"))
		.andExpect(status().is3xxRedirection());
		
		verify(recipeServices).deleteById(anyString());
	}
	
	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/new"))
		.andExpect(model().attributeExists("recipe"))
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(status().isOk());
	}

	
	@Test
	public void testUpdateRecipe() throws Exception {
		String id = "1";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		
		when(recipeServices.findCommandById(id)).thenReturn(Mono.just(recipeCommand));
		
		mockMvc.perform(get("/recipe/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeform"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	
	@Test
	public void testPostNewRecipeFormValid() throws Exception {
		String id = "1";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		recipeCommand.setDescription("test");
		
		when(recipeServices.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));
		
		mockMvc.perform(post("/recipe")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("id", "")
		.param("description", "desc")
		.param("directions", "direction")
		)
		.andExpect(view().name("redirect:/recipe/1/show"))
		.andExpect(status().is3xxRedirection()); //.andExpect(status().isFound());
		
	}
	
	@Test
	public void testPostNewRecipeFormValidationFail() throws Exception {
		String id = "1";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		
		when(recipeServices.saveRecipeCommand(any())).thenReturn(Mono.just(recipeCommand));
		
		mockMvc.perform(post("/recipe")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "")
				.param("cookTime", "100000000")
				)
				.andExpect(view().name("recipe/recipeform"))
				.andExpect(status().isOk());
	}
}
