package ayc.recipe.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ayc.recipe.commands.IngredientCommand;
import ayc.recipe.converters.IngredientCommandToIngredient;
import ayc.recipe.converters.IngredientToIngredientCommand;
import ayc.recipe.model.Ingredient;
import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService{
	
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCommand ingredientToIngredientCommand;
	private final IngredientCommandToIngredient ingredientCommandToIngredient;
	private final UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IngredientServiceImpl(RecipeRepository recipeRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand,
			IngredientCommandToIngredient ingredientCommandToIngredient,
			UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
		this.recipeRepository = recipeRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	public IngredientCommand findByRecipeIdandIngredientId(Long recipeId, Long id) {
		Optional<Recipe> recipeOp = recipeRepository.findById(recipeId);

		if (!recipeOp.isPresent()) {
			log.error("Recipe is not found. RecipeId: " + recipeId);
		}

		Optional<IngredientCommand> ingredientCommand = recipeOp.get().getIngredient()
				.stream()
				.filter(ingredient -> ingredient.getId().equals(id))
				.map(ingredient -> ingredientToIngredientCommand.convert(ingredient))
				.findFirst();

		if (!ingredientCommand.isPresent()) {
			log.error("Ingredient is not found. IngredientId: " + id);
		}

		return ingredientCommand.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
		Optional<Recipe> recipeOp = recipeRepository.findById(ingredientCommand.getRecipeId());

		if (!recipeOp.isPresent()) {
			log.debug("Recipe is not found. RecipeId: " + ingredientCommand.getRecipeId());
			throw new RuntimeException("Recipe is not found");
		}

		Optional<Ingredient> rIngredient = recipeOp.get().getIngredient()
				.stream()
				.filter(rId -> rId.getId().equals(ingredientCommand.getId()))
				.findFirst();

		//Update existing ingredient
		if (rIngredient.isPresent()) {
			Ingredient ingredient = rIngredient.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			ingredient.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId())
					.orElseThrow(() -> new RuntimeException("Uom not found")));
		} else {
			// Add new ingredient
			//we do not have a id value
			//??? ingredientRepo.save maybe???
			Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
			ingredient.setRecipe(recipeOp.get());
			recipeOp.get().addIngredients(ingredient);
			
		}
		
		Recipe savedRecipe = recipeRepository.save(recipeOp.get());
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredient()
				.stream().filter(rIngs -> rIngs.getId().equals(ingredientCommand.getId()))
				.findFirst();
		
		//if ingredientCommand.getId() == null getSavedIngredient from amount description and uom.
		//Generally id of ingredientCommand is null because we ingredientCommand is comming from form and we have not save it via ingredientRepo
		if(!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredient().stream()
					.filter(rIngs -> rIngs.getAmount().equals(ingredientCommand.getAmount()))
					.filter(rIngs -> rIngs.getDescription().equals(ingredientCommand.getDescription()))
					.filter(rIngs -> rIngs.getUom().getId().equals(ingredientCommand.getUom().getId()))
					.findFirst();
		}
		

//		return ingredientToIngredientCommand.convert(savedRecipe.getIngredient()
//				.stream()
//				.filter(ing -> ing.getId().equals(ingredientCommand.getId()))
//				.findFirst()
//				.get());
		
		return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
	}

	@Override
	public void deleteIngredientOfRecipe(Long recipeId, Long id) {
		Optional<Recipe> recipeOp = recipeRepository.findById(recipeId);
		if(recipeOp.isPresent()) {
			Set<Ingredient> recipeIngredient = recipeOp.get().getIngredient();
			Optional<Ingredient> ingredient = recipeIngredient
					.stream()
					.filter(rIng -> rIng.getId() == id)
					.findFirst();
			if(ingredient.isPresent()) {
				recipeOp.get().getIngredient().remove(ingredient.get());
				ingredient.get().setRecipe(null); // Do not forget !!!!
				recipeRepository.save(recipeOp.get());
			}
		}
	}

}
