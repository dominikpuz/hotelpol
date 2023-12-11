package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;

@Component
public class RoomEditDialog {
    @FXML
    public Button addBedButton;
    @FXML
    public ChoiceBox<BedType> bedChoiceBox;
    @FXML
    public ListView<BedType> bedList;
    @FXML
    public ChoiceBox<RoomStandard> standardBox;
    @FXML
    public TextField roomNumberField;
    @FXML
    public TextField floorField;
    @FXML
    public TextField rentPriceField;
    @FXML
    public Button removeBedButton;
    private Stage dialogStage;
    @Setter
    private Room room;
    @Getter
    private boolean approved;

    public RoomEditDialog() {
        approved = false;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        initializeControls();
    }

    @FXML
    private void initialize() {
        standardBox.getItems().addAll(RoomStandard.FAMILY, RoomStandard.BUSINESS,
                RoomStandard.CLASSIC, RoomStandard.EXCLUSIVE);
        bedChoiceBox.getItems().addAll(BedType.DOUBLE_BED, BedType.KID_BED, BedType.SINGLE_BED);
        bedList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        removeBedButton.disableProperty().bind(Bindings.isEmpty(bedList.getSelectionModel().getSelectedItems()));
        bedChoiceBox.setValue(BedType.SINGLE_BED);
    }

    public void initializeControls() {
        if (room.getId() != -1) {
            roomNumberField.setText(room.getRoomNumber());
            floorField.setText(String.valueOf(room.getFloor()));
            rentPriceField.setText(String.valueOf(room.getRentPrice()));
            standardBox.setValue(room.getRoomStandard());
            bedList.setItems(FXCollections.observableArrayList(room.getBeds()));
        }
    }

    private void updateModel() {
        room.setRoomNumber(roomNumberField.getText());
        room.setFloor(Integer.parseInt(floorField.getText()));
        room.setRoomStandard(standardBox.getValue());
        room.setRentPrice(Double.parseDouble(rentPriceField.getText()));
        room.setBeds(bedList.getItems());
    }

    @FXML
    public void handleOkAction(ActionEvent actionEvent) {
        updateModel();
        approved = true;
        dialogStage.close();
    }

    @FXML
    public void handleCancelAction(ActionEvent actionEvent) {
        dialogStage.close();
    }

    @FXML
    public void handleAddBedAction(ActionEvent actionEvent) {
        bedList.getItems().add(bedChoiceBox.getValue());
    }

    @FXML
    public void handleRemoveBedAction(ActionEvent actionEvent) {
        BedType bed = bedList.getSelectionModel().getSelectedItem();
        bedList.getItems().remove(bed);
    }
}
