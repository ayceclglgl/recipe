package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.NotesCommand;
import ayc.recipe.model.Notes;
import lombok.Synchronized;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

	@Synchronized
    @Nullable
    @Override
	public Notes convert(NotesCommand source) {
		if(source == null) 	return null;
		
		final Notes notes= new Notes();
		notes.setNotes(source.getNotes());
		notes.setId(source.getId());
		//Recipe ??
		//If we implememt notes like ingredients then, we need to add Recipe object.
		//For now, it is skipped.
		return notes;
		
	}

}
