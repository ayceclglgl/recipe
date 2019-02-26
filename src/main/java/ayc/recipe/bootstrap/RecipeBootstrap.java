package ayc.recipe.bootstrap;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ayc.recipe.model.Category;
import ayc.recipe.model.Difficulty;
import ayc.recipe.model.Ingredient;
import ayc.recipe.model.Notes;
import ayc.recipe.model.Recipe;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.CategoryRepository;
import ayc.recipe.repositories.RecipeRepository;
import ayc.recipe.repositories.UnitOfMeasureRepository;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent>{
	
	RecipeRepository recipeRepository;
	UnitOfMeasureRepository uomRepository;
	CategoryRepository categoryRepository;
	
	public RecipeBootstrap(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository, CategoryRepository categoryRepository) {
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
		this.categoryRepository = categoryRepository;
	}
	
	private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2);

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3);

        Category cat4 = new Category();
        cat4.setDescription("Spanish");
        categoryRepository.save(cat4);
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setUom("Teaspoon");
        uomRepository.save(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setUom("Tablespoon");
        uomRepository.save(uom2);

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setUom("Cup");
        uomRepository.save(uom3);

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setUom("Pinch");
        uomRepository.save(uom4);

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setUom("Ounce");
        uomRepository.save(uom5);

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setUom("Each");
        uomRepository.save(uom6);

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setUom("Pint");
        uomRepository.save(uom7);

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setUom("Dash");
        uomRepository.save(uom8);
        
        UnitOfMeasure uom9 = new UnitOfMeasure();
        uom9.setUom("Pounds");
        uomRepository.save(uom9);
        
        UnitOfMeasure uom10 = new UnitOfMeasure();
        uom10.setUom("Item");
        uomRepository.save(uom10);
    }
    
    private List<Recipe> getRecipes() {
		
		Optional<UnitOfMeasure> tbles = uomRepository.findByUom("Tablespoon");
		Optional<UnitOfMeasure> teaspoon  = uomRepository.findByUom("Teaspoon");
		Optional<UnitOfMeasure> item  = uomRepository.findByUom("Item");
		Optional<UnitOfMeasure> pounds  = uomRepository.findByUom("Pounds");
		Optional<UnitOfMeasure> dash  = uomRepository.findByUom("Dash");
		Optional<Category> spanishCategory = categoryRepository.findByDescription("Spanish");
		Optional<Category> mexicanCategory = categoryRepository.findByDescription("Mexican");
		
		ArrayList<Recipe> recipeList = new ArrayList<>();
		Recipe tacos = new Recipe();
		tacos.setDescription("Spicy Grilled Chicken Tacos");
		tacos.setCookTime(15);
		tacos.getCategories().add(spanishCategory.get());
		tacos.getCategories().add(mexicanCategory.get());
		tacos.setDifficulty(Difficulty.EASY);
		String directionsTacos = "Prepare a gas or charcoal grill for medium-high, direct heat"
				+ "Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over."
				+ "Set aside to marinate while the grill heats and you prepare the rest of the toppings"
				+ "Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes."
				+ "Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side."
				+ "Wrap warmed tortillas in a tea towel to keep them warm until serving."
				+ " Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.";
		tacos.setDirections(directionsTacos);
		tacos.getIngredient().add(new Ingredient("ancho chili powder", BigDecimal.valueOf(2) , tbles.get(), tacos));
		tacos.getIngredient().add(new Ingredient("dried oregano", BigDecimal.valueOf(1) , teaspoon.get(), tacos));
		tacos.getIngredient().add(new Ingredient("dried cumin", BigDecimal.valueOf(1) , teaspoon.get(), tacos));
		tacos.getIngredient().add(new Ingredient("sugar", BigDecimal.valueOf(1) , teaspoon.get(), tacos));
		tacos.getIngredient().add(new Ingredient("salt", BigDecimal.valueOf(1) , teaspoon.get(), tacos));
		tacos.getIngredient().add(new Ingredient("garlic", BigDecimal.valueOf(1) , item.get(), tacos));
		tacos.getIngredient().add(new Ingredient("grated orange zest", BigDecimal.valueOf(1) , tbles.get(), tacos));
		tacos.getIngredient().add(new Ingredient("fresh-squeezed orange juice", BigDecimal.valueOf(3) , tbles.get(), tacos));
		tacos.getIngredient().add(new Ingredient("boneless chicken thighs", BigDecimal.valueOf(3) , tbles.get(), tacos));
		tacos.getIngredient().add(new Ingredient("olive oil", BigDecimal.valueOf(2) , pounds.get(), tacos));
		String recipeTacosNotes = "Spicy grilled chicken tacos! Quick marinade, then grill. Ready in about 30 minutes. Great for a quick weeknight dinner, backyard cookouts, and tailgate parties";
		tacos.setNotes(new Notes(recipeTacosNotes, tacos));
		tacos.setPrepTime(20);
		tacos.setServings(6);
		
		
		Recipe guacamole = new Recipe();
		guacamole.setDescription("Guacamole");
		guacamole.setCookTime(0);
		guacamole.getCategories().add(mexicanCategory.get());
		guacamole.setDifficulty(Difficulty.EASY);
		String recipeNotesGuacamole = "Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon."
				+ "Mash with a fork: Using a fork, roughly mash the avocado."
				+ "Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown."
				+ "Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve."
				+ "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving";
		guacamole.setDirections(recipeNotesGuacamole);
		guacamole.addIngredients(new Ingredient("avocados", BigDecimal.valueOf(2) , item.get())); //!!!
		guacamole.addIngredients(new Ingredient("Kosher salt", BigDecimal.valueOf(1) , teaspoon.get()));
		guacamole.addIngredients(new Ingredient("lemon juice", BigDecimal.valueOf(1) , tbles.get()));
		guacamole.addIngredients(new Ingredient("minced red onion", BigDecimal.valueOf(2) , tbles.get()));
		guacamole.addIngredients(new Ingredient("serrano chiles", BigDecimal.valueOf(1) , item.get()));
		guacamole.addIngredients(new Ingredient("garlic", BigDecimal.valueOf(1) , item.get()));
		guacamole.addIngredients(new Ingredient("cilantro", BigDecimal.valueOf(2) , tbles.get()));
		guacamole.addIngredients(new Ingredient("black pepper", BigDecimal.valueOf(1) , dash.get()));
		guacamole.addIngredients(new Ingredient("boneless chicken thighs", BigDecimal.valueOf(3) , tbles.get()));
		guacamole.addIngredients(new Ingredient("ripe tomato", BigDecimal.valueOf(1) , item.get()));
		String recipeGuacamoleNotes = "Be careful handling chiles if using. Wash your hands thoroughly after handling and do not touch your eyes or the area near your eyes with your hands for several hours.";
		Notes noteQuacamole = new Notes();
		noteQuacamole.setNotes(recipeGuacamoleNotes); //!!!!!
		guacamole.setNotes(noteQuacamole);
		guacamole.setPrepTime(10);
		guacamole.setServings(4);
		
		recipeList.add(tacos);
		recipeList.add(guacamole);
		return recipeList;
		
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		loadCategories();
		loadUom();
		recipeRepository.saveAll(getRecipes());
		
	}

	
	
}
