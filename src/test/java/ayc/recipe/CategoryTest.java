package ayc.recipe;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.model.Category;

public class CategoryTest {
	
	Category category;
	
	@Before
	public void setUp() {
		category = new Category();
	}

	
	@Test
	public void getId() {
		Long val = 4L;
		assertEquals(category.getId(), null);
	}
}
