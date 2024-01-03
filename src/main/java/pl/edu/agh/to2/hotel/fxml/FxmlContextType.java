package pl.edu.agh.to2.hotel.fxml;

public enum FxmlContextType {
    MAIN_VIEW("MainView"),
    CUSTOMER_INFO_DIALOG("CustomerInfoDialog"),
    CUSTOMER_EDIT_DIALOG("CustomerEditDialog"),
    ROOM_INFO_DIALOG("RoomInfoDialog"),
    ROOM_EDIT_DIALOG("RoomEditDialog"),
    CUSTOMER_PICKER("CustomerPicker"),
    ROOM_PICKER("RoomPicker"),
    RESERVATION_INFO_DIALOG("ReservationInfoDialog"),
    RESERVATION_EDIT_DIALOG("ReservationEditDialog");

    public final String fileName;

    FxmlContextType(String fileName) {
        this.fileName = fileName;
    }
}
