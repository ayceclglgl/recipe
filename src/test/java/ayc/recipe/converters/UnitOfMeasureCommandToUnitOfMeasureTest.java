package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;

public class UnitOfMeasureCommandToUnitOfMeasureTest {
	
	UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;
	
	@Before
	public void setUp() {
		unitOfMeasureCommandToUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(unitOfMeasureCommandToUnitOfMeasure.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(unitOfMeasureCommandToUnitOfMeasure.convert(new UnitOfMeasureCommand()));
	}
	
	@Test
	public void testConvert() {
		UnitOfMeasureCommand uomC = new UnitOfMeasureCommand();
		uomC.setId("1");
		uomC.setUom("Cup");
		
		UnitOfMeasure uom = unitOfMeasureCommandToUnitOfMeasure.convert(uomC);
		assertNotNull(uom);
		assertEquals(uomC.getId(), uom.getId());
		assertEquals(uomC.getUom(), uom.getUom());
	}

}
