package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.recipe.services.RecipeServices;

@Controller
public class RecipeController {

	RecipeServices recipeServices;
	
	public RecipeController(RecipeServices recipeServices) {
		this.recipeServices = recipeServices;
	}
	
	@RequestMapping(value="/recipeList")
	public String getRecipeList(Model m) {
		m.addAttribute("recipes", recipeServices.findAllRecipes());
		return "recipe/recipe";
	}
}
