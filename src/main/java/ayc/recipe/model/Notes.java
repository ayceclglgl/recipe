package ayc.recipe.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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

}
