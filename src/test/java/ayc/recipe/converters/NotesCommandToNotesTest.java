package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.NotesCommand;
import ayc.recipe.model.Notes;

public class NotesCommandToNotesTest {
	
	NotesCommandToNotes notesCommandToNotes;
	
	@Before
	public void setUp() {
		notesCommandToNotes = new NotesCommandToNotes();
	}
	
	@Test
	public void testNullObject() {
		assertNull(notesCommandToNotes.convert(null));
	}
	
	@Test
	public void testEmptyObject() {
		assertNotNull(notesCommandToNotes.convert(new NotesCommand()));
	}
	
	@Test
	public void testConvert() {
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(1L);
		notesCommand.setNotes("notes");
		
		Notes notes = notesCommandToNotes.convert(notesCommand);
		
		assertNotNull(notes);
		assertEquals(notesCommand.getId(), notes.getId());
		assertEquals(notesCommand.getNotes(), notes.getNotes());
		
	}

}
