package ayc.recipe.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ayc.recipe.commands.RecipeCommand;
import ayc.recipe.services.ImageService;
import ayc.recipe.services.RecipeService;

@Controller
public class ImageController {
	
	RecipeService recipeService;
	ImageService imageService;
	
	public ImageController(RecipeService recipeService, ImageService imageService) {
		this.recipeService = recipeService;
		this.imageService = imageService;
	}
	
	
	@GetMapping("recipe/{recipeId}/image")
	public String showImageUploadForm(@PathVariable("recipeId") String id, Model m) {
		m.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/imageuploadform";
	}
	
	@PostMapping("recipe/{recipeId}/image")
	public String imagePost(@PathVariable("recipeId") String id,
			@RequestParam("imagefile") MultipartFile mfile) {
		imageService.saveImage(id, mfile);
		return"redirect:/recipe/" + id + "/show";
	}
	
	@GetMapping("recipe/{recipeId}/recipeimage")
	public void renderImage(@PathVariable("recipeId") String id, HttpServletResponse response) throws IOException {
		RecipeCommand recipeCommand = recipeService.findCommandById(id);
		if (recipeCommand.getImage() != null) {
			byte[] byteArray = new byte[recipeCommand.getImage().length];
			int index = 0;
			for (byte b : recipeCommand.getImage()) {
				byteArray[index++] = b;
				
			}
			
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			InputStream is = new ByteArrayInputStream(byteArray);
			IOUtils.copy(is, response.getOutputStream());
		}
	}

}
