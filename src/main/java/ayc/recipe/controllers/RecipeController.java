package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.recipe.services.RecipeService;


@Controller
public class RecipeController {

	RecipeService recipeServices;
	
	public RecipeController(RecipeService recipeServices) {
		this.recipeServices = recipeServices;
	}
	
	@RequestMapping(value="/recipeList")
	public String getRecipeList(Model m) {
		m.addAttribute("recipes", recipeServices.findAllRecipes());
		return "recipe/recipe";
	}
	
	@RequestMapping(value="/recipe/{id}")
	public String getRecipe(Model m, @PathVariable("id") long id) {
		m.addAttribute("recipe", recipeServices.findById(id));
		return "recipe/show";
		
	}
}
