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
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

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
	public Mono<IngredientCommand> findByRecipeIdandIngredientId(String recipeId, String id) {
		return recipeRepository
		.findById(recipeId)
		.flatMapIterable(Recipe::getIngredient)
		.filter(ingredient -> ingredient.getId().equalsIgnoreCase(id))
		.single()
		.map(ingredient -> {
			IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
			command.setRecipeId(recipeId);
			return command;
		});

//		return recipeRepository
//				.findById(recipeId).map(r -> r.getIngredient()
//						.stream()
//						.filter(ingredient -> ingredient.getId().equalsIgnoreCase(id))
//						.findFirst())
//				.filter(Optional::isPresent)
//				.map(ingredient -> {
//					IngredientCommand command = ingredientToIngredientCommand.convert(ingredient.get());
//					command.setRecipeId(recipeId);
//					return command;
//				});
	}

	@Override
	@Transactional
	public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
		Mono<Recipe> recipeMono = recipeRepository.findById(ingredientCommand.getRecipeId());
		Recipe recipe = recipeMono.block();
		
		if (recipe == null) {
			log.debug("Recipe is not found. RecipeId: " + ingredientCommand.getRecipeId());
			throw new RuntimeException("Recipe is not found");
		}

		Optional<Ingredient> rIngredient = recipe.getIngredient()
				.stream()
				.filter(rId -> rId.getId().equals(ingredientCommand.getId()))
				.findFirst();

		//Update existing ingredient
		if (rIngredient.isPresent()) {
			Ingredient ingredient = rIngredient.get();
			ingredient.setAmount(ingredientCommand.getAmount());
			ingredient.setDescription(ingredientCommand.getDescription());
			UnitOfMeasure uom = unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).block();
			if(uom == null)
				throw new RuntimeException("Uom not found");
			ingredient.setUom(uom);
		} else {
			//Add new ingredient
			//We do not have a id value
			//???Maybe creationg a ingredientRepository and its save()???
			Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
//			ingredient.setRecipe(recipeOp.get());
			recipe.addIngredients(ingredient);
			
		}
		
		Recipe savedRecipe = recipeRepository.save(recipe).block();
		Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredient()
				.stream().filter(rIngs -> rIngs.getId().equals(ingredientCommand.getId()))
				.findFirst();
		
		//if ingredientCommand.getId() == null getSavedIngredient from amount description and uom.
		//Generally id of ingredientCommand is null because we have ingredientCommand is coming from the form and we have not save it yet
		if(!savedIngredientOptional.isPresent()) {
			savedIngredientOptional = savedRecipe.getIngredient().stream()
					.filter(rIngs -> rIngs.getAmount().equals(ingredientCommand.getAmount()))
					.filter(rIngs -> rIngs.getDescription().equals(ingredientCommand.getDescription()))
					.filter(rIngs -> rIngs.getUom().getId().equals(ingredientCommand.getUom().getId()))
					.findFirst();
		}
		
		IngredientCommand ingredientCommandSaved = ingredientToIngredientCommand.convert(savedIngredientOptional.get());
		ingredientCommandSaved.setRecipeId(recipe.getId());
		return Mono.just(ingredientCommandSaved);
	}

	@Override
	public Mono<Void> deleteIngredientOfRecipe(String recipeId, String id) {
		Recipe recipe = recipeRepository.findById(recipeId).block();
		if(recipe == null) {
			log.error("Recipe not found");
		}else {
			Optional<Ingredient> optionalIngredients = recipe.getIngredient()
					.stream()
					.filter(in -> in.getId().equals(id))
					.findFirst();
			if(optionalIngredients.isPresent()) {
				recipe.getIngredient().remove(optionalIngredients.get());
				recipeRepository.save(recipe).block();
			}
		}
		return Mono.empty();
	}

}
