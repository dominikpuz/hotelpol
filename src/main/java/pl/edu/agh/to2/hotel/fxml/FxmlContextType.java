package pl.edu.agh.to2.hotel.fxml;

public enum FxmlContextType {
    MAIN_VIEW("MainView"),
    CUSTOMER_INFO_DIALOG("CustomerInfoDialog"),
    ROOM_INFO_DIALOG("RoomInfoDialog"),
    ROOM_EDIT_DIALOG("RoomEditDialog"),
    RESERVATION_INFO_DIALOG("ReservationInfoDialog"),
    RESERVATION_EDIT_DIALOG("ReservationEditDialog");

    public final String fileName;

    FxmlContextType(String fileName) {
        this.fileName = fileName;
    }
}
