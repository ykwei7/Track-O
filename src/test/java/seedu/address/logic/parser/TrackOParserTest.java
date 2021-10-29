package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_OF_WEEK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURLY_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TUTEE;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddLessonCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearRemarkCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteLessonCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditTuteeDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.paymentcommand.PaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.Subject;
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
                FindCommand.COMMAND_WORD + " " + "n/Alice l/p5 subject/Math overdue/yes");
        assertEquals(new FindCommand(new CollectivePredicate(nameKeywords,
                levelKeywords, subjectKeywords, overdueKeywords)), command);
    }

    @Test
    public void parseCommand_payment() throws Exception {
        PaymentCommand command = (PaymentCommand) parser.parseCommand(
                PaymentCommand.COMMAND_WORD + " " + "1");
        assertEquals(new PaymentCommand(Index.fromOneBased(1)), command);
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
    public void parseCommand_clearRemark() throws Exception {
        ClearRemarkCommand command = (ClearRemarkCommand) parser.parseCommand(
                ClearRemarkCommand.COMMAND_WORD + " " + INDEX_FIRST_TUTEE.getOneBased());
        assertEquals(new ClearRemarkCommand(INDEX_FIRST_TUTEE), command);
    }

    @Test
    public void parseCommand_deleteLesson() throws Exception {
        Index tuteeIndex = Index.fromOneBased(1);
        Index lessonIndex = Index.fromOneBased(1);

        DeleteLessonCommand command = (DeleteLessonCommand) parser.parseCommand(DeleteLessonCommand.COMMAND_WORD
                + " " + tuteeIndex.getOneBased() + " " + PREFIX_LESSON + lessonIndex.getOneBased());

        assertEquals(new DeleteLessonCommand(tuteeIndex, lessonIndex), command);
    }

    @Test
    public void parseCommand_addLesson() throws Exception {
        final Subject subject = new Subject("Biology");
        final DayOfWeek dayOfWeek = DayOfWeek.THURSDAY;
        LocalTime startTime = LocalTime.of(11, 30);
        LocalTime endTime = LocalTime.of(13, 30);
        double hourlyRate = 40.50;

        AddLessonCommand command = (AddLessonCommand) parser.parseCommand(AddLessonCommand.COMMAND_WORD + " "
                + INDEX_FIRST_TUTEE.getOneBased() + " "
                + PREFIX_SUBJECT + subject + " "
                + PREFIX_DAY_OF_WEEK + dayOfWeek.getValue() + " "
                + PREFIX_START_TIME + startTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " "
                + PREFIX_END_TIME + endTime.format(DateTimeFormatter.ofPattern("HH:mm")) + " "
                + PREFIX_HOURLY_RATE + String.format("%.2f", hourlyRate));
        assertEquals(new AddLessonCommand(INDEX_FIRST_TUTEE, subject,
                dayOfWeek, startTime, endTime, hourlyRate), command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        assertTrue(parser.parseCommand(ScheduleCommand.COMMAND_WORD) instanceof ScheduleCommand);
        assertTrue(parser.parseCommand(ScheduleCommand.COMMAND_WORD + " 3") instanceof ScheduleCommand);
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
