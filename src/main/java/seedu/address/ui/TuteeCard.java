package seedu.address.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.lesson.Subject;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Tutee;


/**
 * An UI component that displays information of a {@code Tutee}.
 */
public class TuteeCard extends UiPart<Region> {

    private static final String FXML = "TuteeListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */


    public final Tutee tutee;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label overdue;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label school;
    @FXML
    private Label level;
    @FXML
    private FlowPane tags;

    /** A List of five hexadecimal values of colors for subject labels (Green, Pink, Orange, Purple, Cyan). */
    private List<String> subjectColors = Arrays.asList("#35893b", "#d2729d", "#ae950c", "#5912b0",
            "#48ac9a");

    /** The index of the color to choose from. */
    private int colorIndex = 0;

    /**
     * Creates a {@code TuteeCard} with the given {@code Tutee} and index to display.
     * @param tutee The Tutee whose information is to be displayed
     * @param displayedIndex The index to be displayed alongside the tutee's information
     */
    public TuteeCard(Tutee tutee, int displayedIndex) {
        super(FXML);
        assert tutee != null && displayedIndex > 0 : "Tutee cannot be null and index cannot be less than 1";
        this.tutee = tutee;
        id.setText(displayedIndex + ". ");
        name.setText(tutee.getName().fullName);
        overdue.setVisible(tutee.getPayment().isOverdue);
        phone.setText(tutee.getPhone().value);
        address.setText(tutee.getAddress().value);
        school.setText(tutee.getSchool().value);
        level.setText(tutee.getLevel().stringRepresentation);

        // Adds subject names as tags
        tutee.getLessons().stream()
                .sorted(Comparator.comparing(lesson -> lesson.getSubject().toString()))
                .forEach(lesson -> addSubjectToTag(lesson.getSubject()));

        tutee.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    /**
     * Adds colored subject tags to a TuteeCard.
     * @param subject The Subject to be added and displayed
     */
    public void addSubjectToTag(Subject subject) {
        Label subjectLabel = new Label(subject.toString());

        // This sets the subject to be displayed as a tag with white font colour and a coloured background
        subjectLabel.setStyle(" -fx-text-fill: white;\n"
                + "    -fx-background-color: " + this.subjectColors.get(colorIndex)
                + ";\n"
                + "    -fx-padding: 1 3 1 3;\n"
                + "    -fx-border-radius: 2;\n"
                + "    -fx-background-radius: 2;\n"
                + "    -fx-font-size: 11;\n");

        // Wrap-around if student has more than 5 subjects
        colorIndex = colorIndex == 4 ? 0 : colorIndex + 1;

        tags.getChildren().add(subjectLabel);
    }

    /**
     * Gets the tags of the TuteeCard as a List of Tag
     * @return the Tags which belong to the TuteeCard
     */
    public List<Tag> getTags() {
        return tags.getChildren().stream()
                .map(child -> {
                    Label label = (Label) child;
                    return new Tag(label.getText());
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TuteeCard)) {
            return false;
        }

        // state check
        TuteeCard card = (TuteeCard) other;
        return id.getText().equals(card.id.getText())
                && tutee.equals(card.tutee);
    }
}
