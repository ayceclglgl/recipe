package ayc.recipe.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.services.IngredientService;
import ayc.recipe.services.RecipeService;
import ayc.recipe.services.UomService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class IngredientControllerTest {
	
	@Mock
	RecipeService recipeService;
	@Mock
	IngredientService ingredientService;
	@Mock
	UomService uomService;
	
	IngredientController controller;
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		controller = new IngredientController(recipeService, ingredientService, uomService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testListIngredients() throws Exception {
		String id = "1";
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(id);
		
		when(recipeService.findCommandById(any())).thenReturn(Mono.just(recipeCommand));
		
		
		mockMvc.perform(get("/recipe/1/ingredients"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/list"))
		.andExpect(model().attributeExists("recipe"));
		
		
		verify(recipeService).findCommandById(id);
		
	}

	
	@Test
	public void testShowIngredient() throws Exception {
		String id = "1";
		String recipeId = "1";
		IngredientCommand ic = new IngredientCommand();
		ic.setId(id);
		
		when(ingredientService.findByRecipeIdandIngredientId(recipeId, id)).thenReturn(Mono.just(ic));
		
		mockMvc.perform(get("/recipe/1/ingredient/1/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
	}
	
	@Test
	public void testDeleteIngredientOfRecipe() throws Exception {
		when(ingredientService.deleteIngredientOfRecipe(anyString(), anyString())).thenReturn(Mono.empty());
		
		mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
		verify(ingredientService).deleteIngredientOfRecipe(anyString(), anyString());
	}
	
	
	@Test
	public void testUpdateIngredientOfRecipe() throws Exception {
		String id = "1";
		String recipeId = "1";
		IngredientCommand ic = new IngredientCommand();
		ic.setId(id);
		
		when(ingredientService.findByRecipeIdandIngredientId(recipeId, id)).thenReturn(Mono.just(ic));
		when(uomService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		mockMvc.perform(get("/recipe/1/ingredient/1/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientform"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
	}
	
	@Test
	public void testSaveOrUpdate() throws Exception {
		
		IngredientCommand savedIngredientCommand = new IngredientCommand();
		savedIngredientCommand.setId("2");
		savedIngredientCommand.setRecipeId("1");
		
		when(ingredientService.saveIngredientCommand(any())).thenReturn(Mono.just(savedIngredientCommand));
		
		mockMvc.perform(post("/recipe/1/ingredient")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "").
				param("description", ""))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/ingredient/2/show"));
		
	}
	
	@Test
	public void testNewIngredient() throws Exception {
//		RecipeCommand recipeCommand = new RecipeCommand();
//		recipeCommand.setId("1");
		
//		when(recipeService.findCommandById(any())).thenReturn(recipeCommand);
		when(uomService.listAllUoms()).thenReturn(Flux.just(new UnitOfMeasureCommand()));
		
		mockMvc.perform(get("/recipe/1/ingredient/new"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"))
		.andExpect(view().name("recipe/ingredient/ingredientform"));
		
//		verify(recipeService).findCommandById(any());
		
	}
	
	
	
	
}
