package pl.edu.agh.to2.hotel.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import lombok.Getter;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

import java.util.List;
import java.util.function.Predicate;

public abstract class PickerDialogPresenter<Model extends IPresentableModel> implements IFxmlPresenter {
    protected ObservableList<Model> modelList;
    @Getter
    protected Model model;
    private Stage dialogStage;

    /** Loads data from model to FXML components */
    public abstract void loadData();

    public abstract void finalizeSelection();

    /** Initializes dialog by injecting fxml stage and Model objects. */
    public void initializeDialog(Stage stage, List<Model> modelList) {
        this.dialogStage = stage;
        this.modelList = FXCollections.observableList(modelList);
        this.model = null;
        loadData();
    }

    protected Predicate<Model> createPredicate(String searchText){
        return model -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindsModel(model, searchText);
        };
    }

    protected abstract boolean searchFindsModel(Model model, String searchText);

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
