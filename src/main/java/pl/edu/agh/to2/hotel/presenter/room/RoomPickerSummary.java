package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.presenter.MainView;

@Component
public class RoomPickerSummary {
    @FXML
    @Getter
    private Button selectRoomButton;
    @FXML
    private Text roomSummary;

    @Getter
    private final ListProperty<Room> selectableRooms = new SimpleListProperty<>();
    @Getter
    private final ObjectProperty<Room> selectedRoom = new SimpleObjectProperty<>();

    private final MainView mainController;
    @Setter
    private Stage dialogStage;

    @Autowired
    public RoomPickerSummary(MainView mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void initialize() {
        selectableRooms.addListener(((observable, oldValue, newValue) -> selectedRoom.set(null)));

        roomSummary.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            Room room = selectedRoom.get();
                            if (room == null) {
                                return "No room selected";
                            }
                            return "Room no. " + room.getRoomNumber() + " on floor no. " + room.getFloor();
                        },
                        selectedRoom
                )
        );
    }

    public void handleSelectRoom(ActionEvent ignoreEvent) {
        Room newSelectedRoom = mainController.showRoomPicker(dialogStage, selectableRooms);
        this.selectedRoom.set(newSelectedRoom);
    }
}
