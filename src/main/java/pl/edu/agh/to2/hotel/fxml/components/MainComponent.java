package pl.edu.agh.to2.hotel.fxml.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.App;
import pl.edu.agh.to2.hotel.fxml.FxmlContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContextType;
import pl.edu.agh.to2.hotel.fxml.IFxmlPresenter;

import java.util.HashMap;
import java.util.Map;
@Component
public class MainComponent implements IFxmlPresenter {

    @FXML
    private AnchorPane mainAppContainer;

    @FXML
    private HBox mainNavigationPanel;

    @FXML
    private Button hotelPolNavButton;

    @FXML
    private Button roomsNavButton;

    @FXML
    private Button reservationsNavButton;

    @FXML
    private Button customersNavButton;

    @FXML
    private Button reportsNavButton;

    @FXML
    private AnchorPane contentArea;

    private final Map<NavigationRoute, Button> navigationButtons = new HashMap<>();

    @FXML
    private void initialize() {
        navigationButtons.put(NavigationRoute.ROOMS, roomsNavButton);
        navigationButtons.put(NavigationRoute.RESERVATIONS, reservationsNavButton);
        navigationButtons.put(NavigationRoute.CUSTOMERS, customersNavButton);

        loadContentArea(NavigationRoute.ROOMS);
    }

    private void handleNavigationButtonClick(ActionEvent event, NavigationRoute type) {
        Button button = navigationButtons.get(type);

        navigationButtons.values().forEach(this::removeSelectionFromButton);

        button.getStyleClass().add("selected_tab");

        loadContentArea(type);
    }

    private void removeSelectionFromButton(Button button) {
        button.getStyleClass().remove("selected_tab");
    }

    private <T extends IFxmlPresenter>  void loadContentArea(NavigationRoute route) {
        FxmlContext<T> context = App.getFxmlContextProvider().load(route.contextType);
        contentArea.getChildren().clear();
        contentArea.getChildren().addAll(context.view());
    }

    @FXML
    private void handleRoomsNavButtonClick(ActionEvent event) {
        handleNavigationButtonClick(event, NavigationRoute.ROOMS);
    }

    @FXML
    private void handleReservationsNavButtonClick(ActionEvent event) {
        handleNavigationButtonClick(event, NavigationRoute.RESERVATIONS);
    }

    @FXML
    private void handleCustomersNavButtonClick(ActionEvent event) {
        handleNavigationButtonClick(event, NavigationRoute.CUSTOMERS);
    }

    private enum NavigationRoute {
        ROOMS(FxmlContextType.ROOM_OVERVIEW),
        RESERVATIONS(FxmlContextType.RESERVATION_OVERVIEW),
        CUSTOMERS(FxmlContextType.CUSTOMER_OVERVIEW);

        public final FxmlContextType contextType;

        NavigationRoute(FxmlContextType contextType) {
            this.contextType = contextType;
        }
    }
}
