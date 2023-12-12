package pl.edu.agh.to2.hotel.presenter.room;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.BedType;

@Component
public class RoomInfo {
    @FXML
    public Text floorField;
    @FXML
    public Text roomStandardField;
    @FXML
    public Text rentPriceField;
    @FXML
    public Text roomNumberField;
    @FXML
    public ListView<BedType> bedsList;
    @Setter
    private Room room;
    private Stage dialogStage;

    @FXML
    private void initialize() {
        bedsList.setMouseTransparent(true);
        bedsList.setFocusTraversable(false);
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        initializeStage();
    }

    private void initializeStage() {
        bedsList.setItems(FXCollections.observableArrayList(room.getBeds()));
        floorField.setText(String.valueOf(room.getFloor()));
        roomNumberField.setText(String.valueOf(room.getRoomNumber()));
        roomStandardField.setText(room.getRoomStandard().toString());
        rentPriceField.setText(String.valueOf(room.getRentPrice()));
    }

    @FXML
    public void handleOkAction(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
