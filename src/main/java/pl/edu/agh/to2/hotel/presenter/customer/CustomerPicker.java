package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;
import pl.edu.agh.to2.hotel.model.filters.IModelFilter;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;
import pl.edu.agh.to2.hotel.service.CustomerService;

import java.util.List;

@Component
public class CustomerPicker extends PickerDialogPresenter<Customer> {
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
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        phoneNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhoneNumber()));
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));

        okButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));

        customerFilteringController.modelFilter.addListener((observable, oldValue, newValue) -> loadData(newValue));
    }

    @Override
    public void loadData(IModelFilter customerFilter) {
        CustomerFilter fullFilter = (CustomerFilter) customerFilter.mergeFilter(partialFilter);

        List<Customer> filteredData = customerService.searchCustomers(fullFilter);
        ObservableList<Customer> observableList = FXCollections.observableList(filteredData);
        SortedList<Customer> sortedList = new SortedList<>(observableList);
        customerTable.setItems(FXCollections.observableArrayList(filteredData));
        sortedList.comparatorProperty().bind(customerTable.comparatorProperty());
    }

    @Override
    public void finalizeSelection() {
        model = customerTable.getSelectionModel().getSelectedItem();
    }
}
