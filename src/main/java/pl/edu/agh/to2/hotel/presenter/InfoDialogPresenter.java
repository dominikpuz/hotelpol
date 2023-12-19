package pl.edu.agh.to2.hotel.presenter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.Getter;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

/**
 * This is the superclass for all dialogs designed to display information without modifying it,
 * i.e., without updating or creating any objects through the service layer.
 * The intended usage is to present the user with detailed information about an object, which may not be accessible
 * in the general overview.
 *
 * @param <Model> The class of the object being described.
 */
public abstract class InfoDialogPresenter<Model extends IPresentableModel> implements IFxmlPresenter {

    protected Model model;
    private Stage dialogStage;

    @Getter
    protected boolean approved = false;

    /** Loads data from model to FXML components */
    public abstract void loadData();

    /** Initializes dialog by injecting fxml stage and Model object. */
    public void initializeDialog(Stage stage, Model model) {
        this.dialogStage = stage;
        this.model = model;
        loadData();
    }

    @FXML
    public void handleCancelAction(ActionEvent ignoredEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleOkAction(ActionEvent ignoredEvent) {
        approved = true;
        dialogStage.close();
    }
}
