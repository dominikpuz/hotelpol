package pl.edu.agh.to2.hotel.presenter.filter;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.filters.IModelFilter;

/**
 * Base class for model filter presenters. Extend it to create a presenter managing controls which will use them to create {@code ModelFilter}.
 * Don't forget to set {@code modelFilter}'s starting value
 * @param <ModelFilter>
 */
public abstract class FilteringPresenter <ModelFilter extends IModelFilter> implements IFxmlPresenter {
    public final ObjectProperty<ModelFilter> modelFilter;

    public FilteringPresenter() {
        modelFilter = new SimpleObjectProperty<>();
        resetModelFilter();
    }

    /**
     * Initialize necessary JavaFX fields, e.g. set TextFormatters of TextFields or add selectable values to ChoiceBoxes
     */
    @FXML
    protected abstract void initialize();

    protected abstract ModelFilter createFilter() throws IllegalFilterInput;

    @FXML
    public void handleFilter() {
        ModelFilter newFilter;
        try {
            newFilter = createFilter();
        } catch (IllegalFilterInput e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage());
            alert.showAndWait();
            return;
        }

        modelFilter.set(newFilter);
    }

    @FXML
    public void handleReset() {
        resetControls();
        resetModelFilter();
    }

    /**
     * Set all {@code modelFilter}'s fields to null (like {@code modelFilter.set(ModelFilter.builder().build())})
     */
    protected abstract void resetModelFilter();

    /**
     * Reset all controls: set nulls for fields, deselect all selections etc.
     */
    protected abstract void resetControls();
}
