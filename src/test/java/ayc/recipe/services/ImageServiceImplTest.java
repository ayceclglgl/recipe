package ayc.recipe.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;

public class ImageServiceImplTest {

	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;
	
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		imageService = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void testSaveImage() throws IOException {
		//given
		MockMultipartFile file = new MockMultipartFile("imagefile",
				"testing.txt", "text/plain", "imageimage".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(recipe));
		
		ArgumentCaptor<Recipe> argCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//when
		imageService.saveImage(1L, file);
		
		//then
		verify(recipeRepository).findById(anyLong());
		verify(recipeRepository).save(argCaptor.capture());
		Recipe savedRecipe = argCaptor.getValue();
		assertEquals(file.getBytes().length, savedRecipe.getImage().length);
		
	}
}
