package ayc.recipe.model;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Notes {
	@Id
	private String id;
	private String notes;
//	private Recipe recipe;
	
	
	public Notes() {
		
	}
	
	public Notes(String recipeNotes, Recipe recipe) {
		this.notes = recipeNotes;
//		this.recipe = recipe;
	}
	

}
