package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
//import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ayc.recipe.commands.UnitOfMeasureCommand;
import ayc.recipe.converters.UnitOfMeasureToUnitOfMeasureCommand;
import ayc.recipe.model.UnitOfMeasure;
import ayc.recipe.repositories.UnitOfMeasureRepository;
import reactor.core.publisher.Flux;

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
		uom.setId("1");
		uom.setUom("Cup");
		
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId("2");
		uom2.setUom("Item");
		
		Flux<UnitOfMeasure> uomFlux = Flux.just(uom, uom2);
		
		when(unitOfMeasureRepository.findAll()).thenReturn(uomFlux);
		
		Flux<UnitOfMeasureCommand> uomCommandFlux = uomService.listAllUoms();
		List<UnitOfMeasureCommand> list = uomCommandFlux.collectList().block();
		
		assertEquals(2, list.size());
		verify(unitOfMeasureRepository).findAll();
	}

}
