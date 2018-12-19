package ayc.recipe.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

@Entity
public class Notes {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Lob // Without worriy size of the String
	private String notes;
	@OneToOne //Cascade type is in Recipe. If recipe is deleted then notes should be deleted; not vice versa.
	//If we delete the notes, recipe will still in the db.
	private Recipe recipe;
	
	
	public Notes() {
		
	}
	
	public Notes(String recipeNotes, Recipe recipe) {
		this.notes = recipeNotes;
		this.recipe = recipe;
	}
	
	
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	

}
