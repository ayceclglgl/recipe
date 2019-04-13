package ayc.recipe.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import ayc.recipe.model.Recipe;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeRepositoryIT {
	
	@Autowired
	RecipeRepository recipeRepo;
	
	
	@Before
	public void setUp() {
		recipeRepo.deleteAll().block();
	}
	
	@Test
	public void findAllRecipe() {
		Flux<Recipe> fRepo = recipeRepo.findAll();
		Long count = fRepo.count().block();
		assertEquals(Long.valueOf(0L), count);
	}
	

	
	@Test
	public void testSaveRecipe() {
		Recipe recipe = new Recipe();
		recipe.setDescription("description");
		
		recipeRepo.save(recipe).block();
		
		Long count = recipeRepo.count().block();
		
		assertEquals(count, Long.valueOf(1L));
	}

}
