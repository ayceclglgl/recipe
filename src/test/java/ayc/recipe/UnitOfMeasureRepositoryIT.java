package ayc.recipe;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {
	
	@Autowired //we have spring context since @RunWith(SpringRunner.class) so we can autowired
	UnitOfMeasureRepository uomr;
	
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
