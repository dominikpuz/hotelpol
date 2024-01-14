package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.components.CustomerTable;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;
import pl.edu.agh.to2.hotel.service.CustomerService;

@Component
public class CustomerPicker extends PickerDialogPresenter<Customer, CustomerFilter> {

    @FXML
    private CustomerTable customerTable;
    @FXML
    public Button okButton;
    @FXML
    private CustomerFilteringPresenter customerFilteringController;

    CustomerService customerService;

    @Autowired
    public CustomerPicker(CustomerService customerService) {
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        customerTable.setPageSupplier((page) -> customerService.searchCustomers(customerFilteringController.modelFilter.get().mergeFilter(partialFilter), page));
        okButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));

        customerFilteringController.modelFilter.addListener((observable) -> customerTable.reloadDataAndShowFirstPage());

        customerTable.reloadDataAndShowFirstPage();
    }

    @Override
    protected Customer getSelectedModel() {
        return customerTable.getSelectionModel().getSelectedItem();
    }
}
