package ayc.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.services.IngredientService;
import ayc.recipe.services.RecipeService;
import ayc.recipe.services.UomService;

@Controller
public class IngredientController {

	RecipeService recipeService;
	IngredientService ingredientService;
	UomService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UomService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}

	/**
	 * List all ingredients of a recipe. Have view, update and delete links.
	 * 
	 * @param recipeId
	 * @param m
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/recipe/{recipeId}/ingredients")
	public String listIngredients(@PathVariable("recipeId") long recipeId, Model m) {
		m.addAttribute("recipe", recipeService.findCommandById(recipeId)); // Burada sadece ingridientleri koyabilirim
		return "recipe/ingredient/list";
	}

	/**
	 * It shows a ingredient of a recipe and its details via 'show.html' in ingredient folder. Gets
	 * called from list.html in ingredient folder.
	 * 
	 * @param recipeId
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/recipe/{recipeId}/ingredient/{id}/show")
	public String showIngredientOfRecipe(@PathVariable("recipeId") long recipeId, @PathVariable("id") long id,
			Model m) {
		m.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(recipeId, id));
		return "recipe/ingredient/show";
	}

	/**
	 * It deletes the selected ingredient. Gets called from list.html in ingredient
	 * folder.
	 * 
	 * @param recipeId
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredientOfRecipe(@PathVariable("recipeId") long recipeId, @PathVariable("id") long id,
			Model m) {
		ingredientService.deleteIngredientOfRecipe(recipeId, id);
		return "redirect:/recipe/" + recipeId + "/ingredients";

	}

	/**
	 * It updates the selected ingredient via recipe form. Gets called from
	 * list.html in ingredient folder
	 * Not real update process happens here, it collects necessary information and redirect to real update.
	 * 
	 * @param recipeId
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping
	@RequestMapping(value = "/recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredientOfRecipe(@PathVariable("recipeId") long recipeId, @PathVariable("id") long id,
			Model m) {
		m.addAttribute("ingredient", ingredientService.findByRecipeIdandIngredientId(recipeId, id));
		m.addAttribute("uomList", uomService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}
	
	/**
	 * It gets called from ingredientform via Submit button.
	 * 
	 * @param recipeId
	 * @param ingredientCommand
	 * @return
	 */
	@PostMapping
	@RequestMapping(value = "/recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@PathVariable("recipeId") long recipeId,
			@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}
	
	@GetMapping
	@RequestMapping(value="/recipe/{recipeId}/ingredient/new")
	public String newIngredientofRecipe(@PathVariable("recipeId") long recipeId, Model m) {
		RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		ingredientCommand.setUom(new UnitOfMeasureCommand());//init uom against null pointer exception
		
		
		m.addAttribute("ingredient", ingredientCommand);
		m.addAttribute("uomList", uomService.listAllUoms());
		return "recipe/ingredient/ingredientform";
	}
	
	
	
	

}
