package pl.edu.agh.to2.hotel.presenter.room;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.presenter.MainView;

@Component
@Scope("prototype")
public class RoomPickerSummary {
    @FXML
    @Getter
    private Button selectRoomButton;
    @FXML
    private Label roomSummary;

    @Getter
    private final ObjectProperty<RoomFilter> partialFilter = new SimpleObjectProperty<>(RoomFilter.builder().build());
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
        partialFilter.addListener(((observable, oldValue, newValue) -> selectedRoom.set(null)));
//        selectableRooms.addListener(((observable, oldValue, newValue) -> selectedRoom.set(null)));

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
        mainController.showRoomPicker(dialogStage, partialFilter.get(), selectedRoom::set);
    }
}
