package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.TrackO;
import seedu.address.model.tutee.Tutee;


/**
 * A utility class containing a list of {@code Tutee} objects to be used in tests.
 */
public class TypicalTutees {

    public static final Tutee ALICE = new TuteeBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withLevel("p1")
            .withPhone("94351253")
            .withTags("friends")
            .withPayment("0", LocalDate.of(2021, 10, 20)).build();
    public static final Tutee BENSON = new TuteeBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withLevel("p2").withPhone("98765432")
            .withRemark("Good progress")
            .withTags("owesMoney", "friends").withPayment("90",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee CARL = new TuteeBuilder().withName("Carl Kurz").withPhone("95352563")
            .withLevel("p3").withAddress("wall street").withPayment("80",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee DANIEL = new TuteeBuilder().withName("Daniel Meier").withPhone("87652533")
            .withLevel("p4").withAddress("10th street").withTags("friends").withPayment("85",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee ELLE = new TuteeBuilder().withName("Elle Meyer").withPhone("9482224")
            .withLevel("p5").withAddress("michegan ave").withPayment("75",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee FIONA = new TuteeBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withLevel("p6").withAddress("little tokyo").withPayment("70",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee GEORGE = new TuteeBuilder().withName("George Best").withPhone("9482442")
            .withLevel("p5").withAddress("4th street").withPayment("65",
                    LocalDate.of(2021, 10, 20)).build();

    // Manually added
    public static final Tutee HOON = new TuteeBuilder().withName("Hoon Meier").withPhone("8482424")
            .withLevel("p5").withAddress("little india").withPayment("100",
                    LocalDate.of(2021, 10, 20)).build();
    public static final Tutee IDA = new TuteeBuilder().withName("Ida Mueller").withPhone("8482131")
            .withLevel("p4").withAddress("chicago ave").withPayment("110",
                    LocalDate.of(2021, 10, 20)).build();

    // Manually added - Tutee's details found in {@code CommandTestUtil}
    public static final Tutee AMY = new TuteeBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withLevel(VALID_LEVEL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Tutee BOB = new TuteeBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withLevel(VALID_LEVEL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalTutees() {} // prevents instantiation

    /**
     * Returns a {@code TrackO} with all the typical tutees.
     */
    public static TrackO getTypicalTrackO() {
        TrackO ab = new TrackO();
        for (Tutee tutee : getTypicalTutees()) {
            ab.addTutee(tutee);
        }
        return ab;
    }

    public static List<Tutee> getTypicalTutees() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
