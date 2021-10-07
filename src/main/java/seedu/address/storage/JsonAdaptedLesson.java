package seedu.address.storage;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import seedu.address.model.lesson.Lesson;

/**
 * Jackson-friendly version of {@link Lesson}.
 */
class JsonAdaptedLesson {

    private final String serializedLesson;
    private final ObjectMapper objectMapper;

    /**
     * Converts a given {@code Lesson} into this class for Jackson use.
     */
    @JsonCreator
    public JsonAdaptedLesson(Lesson source) throws JsonProcessingException {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        serializedLesson = objectMapper.writeValueAsString(source);
    }

    /**
     * Converts this Jackson-friendly adapted tag object into the model's {@code Lesson} object.
     *
     * @throws IOException if there were any data constraints violated in the adapted lesson.
     */
    public Lesson toModelType() throws IOException {
        return objectMapper.readValue(serializedLesson, Lesson.class);
    }

}
