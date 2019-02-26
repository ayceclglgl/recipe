package ayc.recipe.repositories;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import ayc.recipe.bootstrap.RecipeBootstrap;
import ayc.recipe.model.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {
	
	@Autowired //we have spring context since @RunWith(SpringRunner.class) so we can autowired
	UnitOfMeasureRepository uomr;
	
	@Autowired
	RecipeRepository reciper;
	
	@Autowired
	CategoryRepository categoryr;
	
	
	@Before
	public void setUp() {
		//since running before every test, duplication error is rise.
		reciper.deleteAll();
		uomr.deleteAll();
		categoryr.deleteAll();
		
		//for initialization data
		RecipeBootstrap recipeBootstrap = new RecipeBootstrap(reciper, uomr, categoryr);
		recipeBootstrap.onApplicationEvent(null);
	}
	
	@Test
	public void findByUom() {
		Optional<UnitOfMeasure> uoms = uomr.findByUom("Teaspoon");
		assertEquals("Teaspoon", uoms.get().getUom());
	}
	
	@Test
	public void findByUomCup() {
		Optional<UnitOfMeasure> uoms = uomr.findByUom("Cup");
		assertEquals("Cup", uoms.get().getUom());
	}

}
