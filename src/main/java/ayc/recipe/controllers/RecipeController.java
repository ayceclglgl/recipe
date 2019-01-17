package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.services.RecipeService;


@Controller
public class RecipeController {

	RecipeService recipeServices;
	
	public RecipeController(RecipeService recipeServices) {
		this.recipeServices = recipeServices;
	}
	
	@RequestMapping(value= "/recipeList")
	public String getRecipeList(Model m) {
		m.addAttribute("recipes", recipeServices.findAllRecipes());
		return "recipe/recipe";
	}
	
	@GetMapping("/recipe/{id}/show")
//	@RequestMapping(value="/recipe/{id}/show")
	public String showById(@PathVariable("id") long id, Model m) {
		m.addAttribute("recipe", recipeServices.findById(id));
		return "recipe/show";
	}
	
	@RequestMapping(value="/recipe/new")
	public String newRecipe(Model m) {
		m.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@RequestMapping(value="/recipe/{id}/update")
	public String updateRecipe(@PathVariable("id") long id, Model m) {
		m.addAttribute("recipe", recipeServices.findCommandById(id));
		return "recipe/recipeform";
	}
	
	@PostMapping
	@RequestMapping(value="/recipe")
	public String saveOrUpdateRecipe(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedCommand = recipeServices.saveRecipeCommand(recipeCommand);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping
	//@DeleteMapping
	@RequestMapping(value="/recipe/{id}/delete")
	public String deleteById(@PathVariable("id") long id) {
		recipeServices.deleteById(id);
		return "redirect:/recipeList";
	}
}
