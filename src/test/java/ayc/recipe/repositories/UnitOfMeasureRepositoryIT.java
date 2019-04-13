package ayc.recipe.repositories;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIT {

	@Autowired //we have spring context since @RunWith(SpringRunner.class) so we can autowired
	UnitOfMeasureRepository uomRepo;

	
	@Before
	public void setUp() {
		uomRepo.deleteAll().block();
	}
	
	@Test
	public void findAllUom() {
		Flux<UnitOfMeasure> uF = uomRepo.findAll();
		Long count = uF.count().block();
		assertEquals(Long.valueOf(0L), count);
	}
	
	@Test
	public void testSaveUom() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setUom("Teaspoon");
		
		uomRepo.save(uom).block();
		
		Long count = uomRepo.count().block();
	
		assertEquals(Long.valueOf(1L), count);
	}
	
	@Test
	public void testFindByDescription() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setUom("Teaspoon");
		
		uomRepo.save(uom).block();
		
		UnitOfMeasure fetchUom = uomRepo.findByUom(uom.getUom()).block();
		
		assertEquals("Teaspoon", fetchUom.getUom());
	}
	
	
	
	
}
