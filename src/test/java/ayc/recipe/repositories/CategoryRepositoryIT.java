package ayc.recipe.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import ayc.recipe.model.Category;
import ayc.recipe.repositories.CategoryRepository;
import reactor.core.publisher.Flux;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CategoryRepositoryIT {
	
	@Autowired
	CategoryRepository categoryRepo;
	
	
	@Before
	public void setUp() {
		categoryRepo.deleteAll().block();
	}
	
	@Test
	public void findAllCategory() {
		Flux<Category> uC = categoryRepo.findAll();
		Long count = uC.count().block();
		assertEquals(Long.valueOf(0L), count);
	}
	
	
	@Test
	public void testSave() {
		Category cat = new Category();
		cat.setDescription("description");
		
		categoryRepo.save(cat).block();
		
		Long count = categoryRepo.count().block();
		
		assertEquals(Long.valueOf(1L), count);
	}
	
	
	@Test
	public void testFindByDescription() {
		Category cat = new Category();
		cat.setDescription("description");
		
		
		categoryRepo.save(cat).then().block();
		
		Category fetchCat = categoryRepo.findByDescription("description").block();
		
		assertNotNull(fetchCat.getId());
	}

}
