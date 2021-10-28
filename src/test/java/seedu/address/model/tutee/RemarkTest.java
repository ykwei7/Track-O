package seedu.address.model.tutee;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void appendRemark() {
        Remark existingRemark = new Remark("What's up");
        Remark newRemark = new Remark("Not much");
        Remark emptyRemark = new Remark("");

        //Both remarks have text
        Remark appendedRemark = existingRemark.appendRemark(newRemark);
        Remark expectedRemark = new Remark("What's up\nNot much");

        assertTrue(appendedRemark.equals(expectedRemark));

        //Empty remark at the front
        Remark appendedEmptyRemarkFront = emptyRemark.appendRemark(newRemark);
        Remark expectedEmptyRemarkFront = new Remark("\nNot much");

        assertTrue(appendedEmptyRemarkFront.equals(expectedEmptyRemarkFront));

        //Empty remark at the back
        Remark appendedEmptyRemarkBack = existingRemark.appendRemark(emptyRemark);
        Remark expectedEmptyRemarkBack = new Remark("What's up\n");

        assertTrue(appendedEmptyRemarkBack.equals(expectedEmptyRemarkBack));
    }

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> returns true
        assertTrue(remark.equals(remark));

        // same values -> returns true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> returns false
        assertFalse(remark.equals(1));

        // null -> returns false
        assertFalse(remark.equals(null));

        // different remark -> returns false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }
}
