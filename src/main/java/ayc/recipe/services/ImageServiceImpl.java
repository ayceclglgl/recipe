package ayc.recipe.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import ayc.recipe.model.Recipe;
import ayc.recipe.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
	
	RecipeRepository recipeRepository;
	
	public ImageServiceImpl(RecipeRepository recipeRepository) {
		this.recipeRepository = recipeRepository;
	}

	
	@Override
	@Transactional
	public void saveImage(Long id, MultipartFile file) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if(!recipeOptional.isPresent()) {
			throw new RuntimeException("Recipe is not found while try to saving image");
		}
		
		Byte[] byteArray;
		try {
			byteArray = new Byte[file.getBytes().length];
			
			int index = 0;
			for (byte b : file.getBytes()) {
				byteArray[index++] = b;
			}
			
			Recipe recipe = recipeOptional.get();
			recipe.setImage(byteArray);
			
			recipeRepository.save(recipe);
			
		} catch (IOException e) {
			log.error("Error while saving image file to recipe", e);
			
			e.printStackTrace();
		}
		
		
	}

}
