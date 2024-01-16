package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

import java.util.function.Consumer;

/**
 * This is the superclass for all dialogs designed to perform actions or operations, potentially
 * involving updates or creations of objects through the service layer.
 * The intended usage is to provide a user interface for executing specific actions or operations
 * that may affect the underlying data.
 *
 * @param <Model> The class of the object involved in the action.
 * @param <ResultModel> The class of the object returned by the presenter due to performed action.
 *                    It represents the result model expected from the default action performed in the dialog.
 */
public abstract class ActionDialogPresenter<Model extends IPresentableModel, ResultModel> implements IFxmlPresenter {

    protected Model model;
    protected Stage dialogStage;

    /** Loads data from model to FXML components */
    public abstract void loadData();

    /** Should return true if dialog was opened for creating new object and false if dialog was opened to edit model*/
    public abstract boolean isAddingDialog();

    public abstract boolean validateAndSubmitModel();

    protected Consumer<ResultModel> onSave;

    /** Initializes dialog by injecting fxml stage and Model object.
     * <p>
     * NOTE: Dialog will load model data to components if isAddingDialog() returns false. */
    public void initializeDialog(Stage stage, Model model, Consumer<ResultModel> onSave) {
        this.dialogStage = stage;
        this.model = model;
        this.onSave = onSave;
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
        dialogStage.close();
    }

    /** Shows FXML Error alert with given title, headerText and contentText */
    private void showErrorDialog(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Attempts to execute the specified action, catching any {@link RuntimeException}.
     * Displays an error dialog with the details of the caught exception.
     *
     * @param action The functional interface representing the action to be executed.
     * @return true if the action was executed successfully, false otherwise.
     */
    protected boolean tryDoAction(ActionFunction action) {
        try {
            action.execute();
            return true;
        } catch (RuntimeException exception) {
            showErrorDialog("Error", exception.getClass().getSimpleName(), exception.getMessage());
            return false;
        }
    }


    /**
     * Attempts to execute the default action associated with this dialog by invoking the {@code onSave}
     * consumer with the provided result model. It utilizes the {@link #tryDoAction(ActionFunction)} method
     * to handle any potential runtime exceptions, displaying an error dialog if necessary.
     *
     * @param result The result model to be accepted by the {@code onSave} consumer.
     * @return true if the default action was executed successfully, false otherwise.
     */
    protected boolean tryDoDefaultAction(ResultModel result) {
        return tryDoAction(() -> onSave.accept(result));
    }
}
