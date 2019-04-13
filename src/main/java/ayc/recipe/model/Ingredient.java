package ayc.recipe.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredient {
	@Id
	private String id = UUID.randomUUID().toString();
	private String description;
	private BigDecimal amount;
	
//	@DBRef //Support DBRef in Spring Data Mongo Reactive - values come null
	private UnitOfMeasure uom;
//	private Recipe recipe;

	public Ingredient() {
		
	}
	
	public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom) {
		this.description = description;
		this.amount = amount;
		this.uom = uom;
	}
	
	public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, Recipe recipe) {
		this.description = description;
		this.amount = amount;
		this.uom = uom;
//		this.recipe = recipe;
	}
	
}
