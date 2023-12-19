package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.Getter;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

/**
 * This is the superclass for all dialogs designed to perform actions or operations, potentially
 * involving updates or creations of objects through the service layer.
 * The intended usage is to provide a user interface for executing specific actions or operations
 * that may affect the underlying data.
 *
 * @param <Model> The class of the object involved in the action.
 */
public abstract class ActionDialogPresenter<Model extends IPresentableModel> implements IFxmlPresenter {

    protected Model model;
    private Stage dialogStage;

    @Getter
    protected boolean approved;

    /** Loads data from model to FXML components */
    public abstract void loadData();

    /** Should return true if dialog was opened for creating new object and false if dialog was opened to edit model*/
    public abstract boolean isAddingDialog();

    public abstract boolean validateAndSubmitModel();

    /** Initializes dialog by injecting fxml stage and Model object.
     * <p>
     * NOTE: Dialog will load model data to components if isAddingDialog() returns false. */
    public void initializeDialog(Stage stage, Model model) {
        this.approved = false;
        this.dialogStage = stage;
        this.model = model;
        if(model != null && !isAddingDialog())
            loadData();
    }

    @FXML
    public void handleCancelAction(ActionEvent ignoredEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent ignoredEvent) {
        if(!validateAndSubmitModel()) return;
        approved = true;
        dialogStage.close();
    }
}
