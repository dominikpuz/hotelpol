package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.components.CustomerTable;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.CustomerService;

@Component
public class CustomerOverview {

    @FXML
    private CustomerTable customerTable;

    @FXML
    private Button editCustomerButton;

    @FXML
    private Button addCustomerButton;

    @FXML
    private CustomerFilteringPresenter customerFilteringController;

    private final MainView mainController;
    private final CustomerService customerService;

    public CustomerOverview(MainView mainController, CustomerService customerService) {
        this.mainController = mainController;
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        customerTable.setPageSupplier((page) -> customerService.searchCustomers(customerFilteringController.modelFilter.get(), page));
        customerFilteringController.modelFilter.addListener(observable -> customerTable.reloadDataAndShowFirstPage());
        editCustomerButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
        customerTable.reloadDataAndShowFirstPage();
    }

    @FXML
    public void handleAddCustomer(ActionEvent ignoreEvent) {
        mainController.showAddCustomerDialog(new Customer(), this::handleCustomerSave);
    }

    @FXML
    public void handleEditCustomer(ActionEvent ignoreEvent) {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        if(customer == null) return;
        mainController.showEditCustomerDialog(customer, this::handleCustomerUpdate);
    }

    private void handleCustomerSave(Customer toSave) {
        customerService.addNewCustomer(toSave);
        customerTable.reloadData();
    }

    private void handleCustomerUpdate(Customer toUpdate) {
        customerService.updateCustomer(toUpdate);
        customerTable.reloadData();
    }
}
