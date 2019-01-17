package ayc.recipe.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.services.ImageService;
import ayc.recipe.services.RecipeService;

public class ImageControllerTest {
	
	@Mock
	RecipeService recipeService; 
	@Mock
	ImageService imageService;
	
	ImageController controller;
	MockMvc mockMvc;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		controller = new ImageController(recipeService, imageService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
		
	@Test
	public void testShowImageUploadForm() throws Exception {
		//given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		mockMvc.perform(get("/recipe/1/image"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService).findCommandById(anyLong());
	}

	
	@Test
	public void testImagePost() throws Exception {
		MockMultipartFile  multipartFile = new MockMultipartFile("imagefile",
				"testing.txt", "text/plain", "imageimage".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multipartFile))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService).saveImage(anyLong(), any());
	}
	
	@Test
	public void testRenderImage() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		
		String s = "imageimage";
		Byte[] byteBoxed = new Byte[s.getBytes().length];
		int index = 0;
		for (byte b : s.getBytes()) {
			byteBoxed[index++] = b;
		}
		recipeCommand.setImage(byteBoxed);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		//when
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
				.andExpect(status().isOk())
				.andReturn().getResponse();
		
		byte[] responseBytes = response.getContentAsByteArray();
		
		assertEquals(s.getBytes().length, responseBytes.length);
		

	}
}
