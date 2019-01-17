package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
//import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;

public class UomServiceImplTest {

	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository; 
	//@Mock
	private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
	private UomService uomService;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		uomService = new UomServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
	}
	
	
	@Test
	public void listAllUoms() {
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(1L);
		uom.setUom("Cup");
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		uom2.setUom("Item");
		
		Set<UnitOfMeasure> uomSet = new HashSet<UnitOfMeasure>();
		uomSet.add(uom);
		uomSet.add(uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(uomSet);
		
		Set<UnitOfMeasureCommand> uomCommandSet = uomService.listAllUoms();
		
		assertNotNull(uomCommandSet);
		assertEquals(uomSet.size(), uomCommandSet.size());
		verify(unitOfMeasureRepository).findAll();
		//verify(unitOfMeasureToUnitOfMeasureCommand).convert(uom);
		
	}

}
