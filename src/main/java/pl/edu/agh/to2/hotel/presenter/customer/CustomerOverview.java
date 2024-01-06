package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.persistance.customer.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.CustomerService;

@Component
public class CustomerOverview {
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableColumn<Customer, String> firstNameColumn;
    @FXML
    public TableColumn<Customer, String> lastNameColumn;
    @FXML
    public TableColumn<Customer, String> phoneNumberColumn;
    @FXML
    public TableColumn<Customer, String> emailColumn;
    @FXML
    public Button editCustomerButton;
    @FXML
    public Button addCustomerButton;

    private final MainView mainController;
    private final CustomerService customerService;

    public CustomerOverview(MainView mainController, CustomerService customerService) {
        this.mainController = mainController;
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        phoneNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhoneNumber()));
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));

        editCustomerButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));

        loadData();
    }

    private void loadData() {
        ObservableList<Customer> customers = FXCollections.observableArrayList(
          customerService.searchCustomers(CustomerFilter.builder().build())
        );
        customerTable.setItems(customers);
    }

    @FXML
    public void handleAddCustomer(ActionEvent ignoreEvent) {
        Customer customer = new Customer();
        if (mainController.showAddCustomerDialog(customer)) {
            customerService.addNewCustomer(customer);
            loadData();
        }
    }

    @FXML
    public void handleEditCustomer(ActionEvent ignoreEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if (customer != null) {
            if (mainController.showEditCustomerDialog(customer)) {
                // TODO: update customer
            }
        }
    }
}