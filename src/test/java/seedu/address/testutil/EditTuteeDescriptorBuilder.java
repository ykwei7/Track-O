package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditTuteeDescriptor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tutee.Address;
import seedu.address.model.tutee.Level;
import seedu.address.model.tutee.Name;
import seedu.address.model.tutee.Phone;
import seedu.address.model.tutee.School;
import seedu.address.model.tutee.Tutee;

/**
 * A utility class to help with building EditTuteeDescriptor objects.
 */
public class EditTuteeDescriptorBuilder {

    private EditTuteeDescriptor descriptor;

    public EditTuteeDescriptorBuilder() {
        descriptor = new EditTuteeDescriptor();
    }

    public EditTuteeDescriptorBuilder(EditTuteeDescriptor descriptor) {
        this.descriptor = new EditTuteeDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditTuteeDescriptor} with fields containing {@code tutee}'s details
     */
    public EditTuteeDescriptorBuilder(Tutee tutee) {
        descriptor = new EditTuteeDescriptor();
        descriptor.setName(tutee.getName());
        descriptor.setPhone(tutee.getPhone());
        descriptor.setSchool(tutee.getSchool());
        descriptor.setLevel(tutee.getLevel());
        descriptor.setAddress(tutee.getAddress());
        descriptor.setTags(tutee.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditTuteeDescriptor} that we are building.
     */
    public EditTuteeDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditTuteeDescriptor} that we are building.
     */
    public EditTuteeDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code School} of the {@code EditTuteeDescriptor} that we are building.
     */
    public EditTuteeDescriptorBuilder withSchool(String school) {
        descriptor.setSchool(new School(school));
        return this;
    }

    /**
     * Sets the {@code Level} of the {@code EditTuteeDescriptor} that we are building.
     */
    public EditTuteeDescriptorBuilder withLevel(String level) {
        descriptor.setLevel(new Level(level));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditTuteeDescriptor} that we are building.
     */
    public EditTuteeDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }


    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditTuteeDescriptor}
     * that we are building.
     */
    public EditTuteeDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditTuteeDescriptor build() {
        return descriptor;
    }
}
