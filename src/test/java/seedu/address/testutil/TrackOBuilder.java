package seedu.address.testutil;

import seedu.address.model.TrackO;
import seedu.address.model.tutee.Tutee;

/**
 * A utility class to help with building TrackO objects.
 * Example usage: <br>
 *     {@code TrackO ab = new TrackOBuilder().withTutee("John", "Doe").build();}
 */
public class TrackOBuilder {

    private TrackO trackO;

    public TrackOBuilder() {
        trackO = new TrackO();
    }

    public TrackOBuilder(TrackO trackO) {
        this.trackO = trackO;
    }

    /**
     * Adds a new {@code Tutee} to the {@code TrackO} that we are building.
     */
    public TrackOBuilder withTutee(Tutee tutee) {
        trackO.addTutee(tutee);
        return this;
    }

    public TrackO build() {
        return trackO;
    }
}
