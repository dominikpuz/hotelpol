package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;

@Component
public class RoomActionDialog extends ActionDialogPresenter<Room, Room> {
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

    @FXML
    private void initialize() {
        standardBox.getItems().addAll(RoomStandard.FAMILY, RoomStandard.BUSINESS,
                RoomStandard.CLASSIC, RoomStandard.EXCLUSIVE);
        bedChoiceBox.getItems().addAll(BedType.DOUBLE_BED, BedType.KID_BED, BedType.SINGLE_BED);
        bedList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        removeBedButton.disableProperty().bind(Bindings.isEmpty(bedList.getSelectionModel().getSelectedItems()));
        bedChoiceBox.setValue(BedType.SINGLE_BED);
    }

    @Override
    public void loadData() {
        roomNumberField.setText(model.getRoomNumber());
        floorField.setText(String.valueOf(model.getFloor()));
        rentPriceField.setText(String.valueOf(model.getRentPrice()));
        standardBox.setValue(model.getRoomStandard());
        bedList.setItems(FXCollections.observableArrayList(model.getBeds()));
    }

    @Override
    public boolean isAddingDialog() {
        return model.getId() == -1;
    }

    @Override
    public boolean validateAndSubmitModel() {
        model.setRoomNumber(roomNumberField.getText());
        model.setFloor(Integer.parseInt(floorField.getText()));
        model.setRoomStandard(standardBox.getValue());
        model.setRentPrice(Double.parseDouble(rentPriceField.getText()));
        model.setBeds(bedList.getItems());

        return tryDoAction(() -> onSave.accept(model));
    }

    @FXML
    public void handleAddBedAction(ActionEvent ignored) {
        bedList.getItems().add(bedChoiceBox.getValue());
    }

    @FXML
    public void handleRemoveBedAction(ActionEvent ignored) {
        BedType bed = bedList.getSelectionModel().getSelectedItem();
        bedList.getItems().remove(bed);
    }
}
