package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.CategoryCommand;
import ayc.recipe.model.Category;

public class CategoryToCategoryCommandTest {
	
	public static final Long ID_VALUE = new Long(1L);
    public static final String DESCRIPTION = "description";
    CategoryToCategoryCommand categoryToCategoryCommand;
    
    @Before
    public void setUp() {
    	categoryToCategoryCommand = new CategoryToCategoryCommand();
    }
    
    @Test
    public void testNullObject() {
    	assertNull(categoryToCategoryCommand.convert(null));
    }
    
    @Test
    public void testEmptyObject() {
    	assertNotNull(categoryToCategoryCommand.convert(new Category()));
    }
    
    
    @Test
    public void testConvert() {
    	Category category = new Category();
    	category.setId(1L);
    	category.setDescription("description");
    	
    	CategoryCommand categoryCommand = categoryToCategoryCommand.convert(category);
    	
    	assertEquals(category.getId(), categoryCommand.getId());
    	assertEquals(category.getDescription(), categoryCommand.getDescription());
    }
    
    

}
