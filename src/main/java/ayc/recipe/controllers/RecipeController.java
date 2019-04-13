package ayc.recipe.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.exceptions.NotFoundException;
import ayc.recipe.services.RecipeService;
import lombok.extern.slf4j.Slf4j;


@Controller
@Slf4j
public class RecipeController {

	private final String RECIPEFORM_URL= "recipe/recipeform";
	
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
		m.addAttribute("recipes", recipeServices.findAllRecipes().collectList().block());
		return "recipe/recipe";
	}
	
	/**
	 * It is called from view link in the main recipe page.
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping("/recipe/{id}/show")
	public String showById(@PathVariable("id") String id, Model m) {
		m.addAttribute("recipe", recipeServices.findById(id).block());
		return "recipe/show";
	}
	
	@GetMapping("/recipe/new")
	public String newRecipe(Model m) {
		m.addAttribute("recipe", new RecipeCommand());
		return RECIPEFORM_URL;
	}
	
	/**
	 * It is called from update link in the main recipe page.
	 * Redirect to recipeform to perform update.
	 * @param id
	 * @param m
	 * @return
	 */
	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable("id") String id, Model m) {
		m.addAttribute("recipe", recipeServices.findCommandById(id).block());
		return RECIPEFORM_URL;
	}
	
	/**
	 * Update or add a new recipe.
	 * @param recipeCommand
	 * @return
	 */
	@PostMapping("/recipe")
	public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(error -> log.debug(error.toString()));
			
			return RECIPEFORM_URL;
		}
		if(!StringUtils.hasText(recipeCommand.getId())) {
			recipeCommand.setId(UUID.randomUUID().toString());
		}
		RecipeCommand savedCommand = recipeServices.saveRecipeCommand(recipeCommand).block();
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}
	
	/**
	 * It is called from delete link in the main recipe page.
	 * @param id
	 * @return
	 */
	@GetMapping("/recipe/{id}/delete")
	//@DeleteMapping
	public String deleteById(@PathVariable("id") String id) {
		recipeServices.deleteById(id).block();
		return "redirect:/recipeList";
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error(exception.getMessage());
		
		ModelAndView mv = new ModelAndView();
		mv.setViewName("404error");
		mv.addObject("exception", exception);
		return mv;
	}
	
	

}
