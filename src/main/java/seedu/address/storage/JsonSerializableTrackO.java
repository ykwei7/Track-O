package seedu.address.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.core.JsonProcessingException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyTrackO;
import seedu.address.model.TrackO;
import seedu.address.model.tutee.Tutee;

/**
 * An Immutable Track-O that is serializable to JSON format.
 */
@JsonRootName(value = "tracko")
class JsonSerializableTrackO {

    public static final String MESSAGE_DUPLICATE_TUTEE = "Tutees list contains duplicate tutee(s).";

    private final List<JsonAdaptedTutee> tutees = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTrackO} with the given tutees.
     */
    @JsonCreator
    public JsonSerializableTrackO(@JsonProperty("tutees") List<JsonAdaptedTutee> tutees) {
        this.tutees.addAll(tutees);
    }

    /**
     * Converts a given {@code ReadOnlyTrackO} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTrackO}.
     */
    public JsonSerializableTrackO(ReadOnlyTrackO source) throws JsonProcessingException {
        ObservableList<Tutee> sourceTuteeList = source.getTuteeList();
        for (Tutee tutee : sourceTuteeList) {
            tutees.add(new JsonAdaptedTutee(tutee));
        }
    }

    /**
     * Converts this Track-O into the model's {@code TrackO} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TrackO toModelType() throws IllegalValueException, IOException {
        TrackO trackO = new TrackO();
        for (JsonAdaptedTutee jsonAdaptedTutee : tutees) {
            Tutee tutee = jsonAdaptedTutee.toModelType();
            if (trackO.hasTutee(tutee)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TUTEE);
            }
            trackO.addTutee(tutee);
        }
        return trackO;
    }

}
