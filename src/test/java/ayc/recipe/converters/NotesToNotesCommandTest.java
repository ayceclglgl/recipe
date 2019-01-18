package ayc.recipe.converters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import ayc.recipe.commands.NotesCommand;
import ayc.recipe.model.Notes;

public class NotesToNotesCommandTest {
	
	NotesToNotesCommand notesToNotesCommand;

	@Before
	public void setUp() {
		notesToNotesCommand = new NotesToNotesCommand();
	}

	@Test
	public void testNullObject() {
		assertNull(notesToNotesCommand.convert(null));
	}

	@Test
	public void testEmptyObject() {
		assertNotNull(notesToNotesCommand.convert(new Notes()));
	}

	@Test
	public void testConvert() {
		Notes notes = new Notes();
		notes.setId(1L);
		notes.setNotes("notes");

		NotesCommand notesCommand = notesToNotesCommand.convert(notes);

		assertNotNull(notes);
		assertEquals(notes.getId(), notesCommand.getId());
		assertEquals(notes.getNotes(), notesCommand.getNotes());

	}
}
