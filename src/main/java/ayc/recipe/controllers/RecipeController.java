package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.services.RecipeService;


@Controller
public class RecipeController {

	RecipeService recipeServices;
	
	public RecipeController(RecipeService recipeServices) {
		this.recipeServices = recipeServices;
	}
	
	/**
	 * It is like a index(=main) page
	 * @param m
	 * @return
	 */
	@GetMapping("/recipeList")
	public String getRecipeList(Model m) {
		m.addAttribute("recipes", recipeServices.findAllRecipes());
		return "recipe/recipe";
	}
	
	/**
	 * It is called from view link in the main recipe page.
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable("id") long id, Model m) {
		m.addAttribute("recipe", recipeServices.findById(id));
		return "recipe/show";
	}
	
	@GetMapping("/recipe/new")
	public String newRecipe(Model m) {
		m.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	/**
	 * It is called from update link in the main recipe page.
	 * Redirect to recipeform to perform update.
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable("id") long id, Model m) {
		m.addAttribute("recipe", recipeServices.findCommandById(id));
		return "recipe/recipeform";
	}
	
	/**
	 * Update or add a new recipe.
	 * @param recipeCommand
	 * @return
	 */
	@PostMapping("/recipe")
	public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedCommand = recipeServices.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	/**
	 * It is called from delete link in the main recipe page.
	 * @param id
	 * @return
	 */
	@GetMapping("/recipe/{id}/delete")
	//@DeleteMapping
	public String deleteById(@PathVariable("id") long id) {
		recipeServices.deleteById(id);
		return "redirect:/recipeList";
	}
}
