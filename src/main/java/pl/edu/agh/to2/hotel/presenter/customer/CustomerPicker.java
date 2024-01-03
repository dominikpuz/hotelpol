package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.PickerDialogPresenter;

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
    public TextField searchField;
    @FXML
    public Button okButton;

    @FXML
    private void initialize() {
        customerTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        firstNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getLastName()));
        phoneNumberColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPhoneNumber()));
        emailColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getEmail()));

        okButton.disableProperty().bind(Bindings.isEmpty(customerTable.getSelectionModel().getSelectedItems()));
    }

    @Override
    public void loadData() {
        FilteredList<Customer> filteredData = new FilteredList<>(FXCollections.observableArrayList(modelList));
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue))
        );
        SortedList<Customer> sortableData = new SortedList<>(filteredData);
        customerTable.setItems(sortableData);
        sortableData.comparatorProperty().bind(customerTable.comparatorProperty());
    }

    @Override
    public void finalizeSelection() {
        model = customerTable.getSelectionModel().getSelectedItem();
    }

    @Override
    protected boolean searchFindsModel(Customer model, String searchText) {
        return (model.getFirstName() + model.getLastName()).toLowerCase().contains(searchText.toLowerCase());
    }
}
