package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.recipe.services.RecipeServices;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class RecipeController {

	RecipeServices recipeServices;
	
	public RecipeController(RecipeServices recipeServices) {
		this.recipeServices = recipeServices;
	}
	
	@RequestMapping(value="/recipeList")
	public String getRecipeList(Model m) {
		m.addAttribute("recipes", recipeServices.findAllRecipes());
		log.debug("/recipeList");
		return "recipe/recipe";
	}
}
