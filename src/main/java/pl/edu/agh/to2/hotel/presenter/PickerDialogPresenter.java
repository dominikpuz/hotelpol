package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.Getter;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;
import pl.edu.agh.to2.hotel.model.filters.IModelFilter;

public abstract class PickerDialogPresenter<Model extends IPresentableModel> implements IFxmlPresenter {
    protected IModelFilter partialFilter;
    @Getter
    protected Model model;
    private Stage dialogStage;

    /** Loads data from model to FXML components */
    public abstract void loadData(IModelFilter filter);

    public abstract void finalizeSelection();

    /** Initializes dialog by injecting fxml stage and Model objects. */
    public void initializeDialog(Stage stage, IModelFilter partialFilter)  {
        this.dialogStage = stage;
        this.partialFilter = partialFilter;
        this.model = null;
        loadData(partialFilter);
    }

    @FXML
    public void handleCancelAction(ActionEvent ignoredEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent ignoredEvent) {
        finalizeSelection();
        dialogStage.close();
    }
}
