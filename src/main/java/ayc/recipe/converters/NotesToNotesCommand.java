package ayc.recipe.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import ayc.recipe.commands.NotesCommand;
import ayc.recipe.model.Notes;
import lombok.Synchronized;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

	@Synchronized
    @Nullable
    @Override
	public NotesCommand convert(Notes source) {
		if(source == null ) return null;
		
		final NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(source.getId());
		notesCommand.setNotes(source.getNotes());
		//Recipe ??
		//If we implememt notes like ingredients then, we need to add Recipe object.
		//For now, it is skipped.
		return notesCommand;

	}

	
	
	

}
