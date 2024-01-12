package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;
import pl.edu.agh.to2.hotel.model.filters.IMergeableFilter;

import java.util.function.Consumer;

public abstract class PickerDialogPresenter<Model extends IPresentableModel, Filter extends IMergeableFilter<Filter>> implements IFxmlPresenter {
    protected Filter partialFilter;
    private Stage dialogStage;
    private Consumer<Model> onModelPicked;

    protected abstract Model getSelectedModel();

    /** Initializes dialog by injecting fxml stage and Model objects. */
    public void initializeDialog(Stage stage, Filter partialFilter, Consumer<Model> onModelPicked)  {
        this.dialogStage = stage;
        this.partialFilter = partialFilter;
        this.onModelPicked = onModelPicked;
    }

    @FXML
    public void handleCancelAction(ActionEvent ignoredEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent ignoredEvent) {
        onModelPicked.accept(getSelectedModel());
        dialogStage.close();
    }
}
