package pl.edu.agh.to2.hotel.presenter.room;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Room;
import pl.edu.agh.to2.hotel.persistance.room.BedType;
import pl.edu.agh.to2.hotel.presenter.InfoDialogPresenter;

@Component
public class RoomInfoDialog extends InfoDialogPresenter<Room> {
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

    @FXML
    private void initialize() {
        bedsList.setMouseTransparent(true);
        bedsList.setFocusTraversable(false);
    }

    @Override
    public void loadData() {
        bedsList.setItems(FXCollections.observableArrayList(model.getBeds()));
        floorField.setText(String.valueOf(model.getFloor()));
        roomNumberField.setText(String.valueOf(model.getRoomNumber()));
        roomStandardField.setText(model.getRoomStandard().toString());
        rentPriceField.setText(String.valueOf(model.getRentPrice()));
    }
}
