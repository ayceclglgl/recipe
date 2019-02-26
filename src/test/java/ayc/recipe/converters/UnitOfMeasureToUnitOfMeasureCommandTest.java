package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;

public class UnitOfMeasureToUnitOfMeasureCommandTest {
	UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
	
	@Before
	public void setUp() {
		unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	}
	
	@Test
	public void testNullObject() throws Exception {
		assertNull(unitOfMeasureToUnitOfMeasureCommand.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(unitOfMeasureToUnitOfMeasureCommand.convert(new UnitOfMeasure()));
	}
	
	@Test
	public void testConvert() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId("1");
		uom.setUom("Cup");
		
		UnitOfMeasureCommand uomC = unitOfMeasureToUnitOfMeasureCommand.convert(uom);
		assertNotNull(uomC);
		assertEquals(uom.getId(), uomC.getId());
		assertEquals(uom.getUom(), uomC.getUom());
	}
}
