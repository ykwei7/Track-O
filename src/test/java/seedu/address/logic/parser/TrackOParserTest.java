package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTuteeDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tutee.CollectivePredicate;
import seedu.address.model.tutee.Remark;
import seedu.address.model.tutee.Tutee;
import seedu.address.testutil.EditTuteeDescriptorBuilder;
import seedu.address.testutil.TuteeBuilder;
import seedu.address.testutil.TuteeUtil;

public class TrackOParserTest {

    private final TrackOParser parser = new TrackOParser();

    @Test
    public void parseCommand_add() throws Exception {
        Tutee tutee = new TuteeBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(TuteeUtil.getAddCommand(tutee));
        assertEquals(new AddCommand(tutee), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_TUTEE.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_TUTEE), command);
    }

    @Test
    public void parseCommand_get() throws Exception {
        GetCommand command = (GetCommand) parser.parseCommand(
                GetCommand.COMMAND_WORD + " " + INDEX_FIRST_TUTEE.getOneBased());
        assertEquals(new GetCommand(INDEX_FIRST_TUTEE), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Tutee tutee = new TuteeBuilder().build();
        EditTuteeDescriptor descriptor = new EditTuteeDescriptorBuilder(tutee).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TUTEE.getOneBased() + " " + TuteeUtil.getEditTuteeDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_TUTEE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> nameKeywords = Collections.singletonList("Alice");
        List<String> levelKeywords = Collections.singletonList("p5");
        List<String> subjectKeywords = Collections.singletonList("Math");
        List<String> overdueKeywords = Collections.singletonList("true");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + "n/Alice l/p5 subject/Math overdue/true");
        assertEquals(new FindCommand(new CollectivePredicate(nameKeywords, levelKeywords, subjectKeywords, overdueKeywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TUTEE.getOneBased() + " " + PREFIX_REMARK + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_TUTEE, remark), command);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
