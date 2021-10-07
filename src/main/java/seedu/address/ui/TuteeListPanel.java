package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tutee.Tutee;

/**
 * Panel containing the list of tutees.
 */
public class TuteeListPanel extends UiPart<Region> {
    private static final String FXML = "TuteeListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TuteeListPanel.class);

    @FXML
    private ListView<Tutee> tuteeListView;

    /**
     * Creates a {@code TuteeListPanel} with the given {@code ObservableList}.
     */
    public TuteeListPanel(ObservableList<Tutee> tuteeList) {
        super(FXML);
        tuteeListView.setItems(tuteeList);
        tuteeListView.setCellFactory(listView -> new TuteeListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Tutee} using a {@code TuteeCard}.
     */
    class TuteeListViewCell extends ListCell<Tutee> {
        @Override
        protected void updateItem(Tutee tutee, boolean empty) {
            super.updateItem(tutee, empty);

            if (empty || tutee == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TuteeCard(tutee, getIndex() + 1).getRoot());
            }
        }
    }

}
