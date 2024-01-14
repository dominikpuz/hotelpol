package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.MainView;
import pl.edu.agh.to2.hotel.service.CustomerService;

@Component
@Scope("prototype")
public class CustomerPickerSummary {
    @FXML
    private Button addNewCustomerButton;
    @FXML
    private Text customerSummary;


    @Getter
    private final ObjectProperty<CustomerFilter> partialFilter = new SimpleObjectProperty<>(CustomerFilter.builder().build());
    @Getter
    private final ObjectProperty<Customer> selectedCustomer = new SimpleObjectProperty<>();

    private final MainView mainController;
    private final CustomerService customerService;
    @Setter
    private Stage dialogStage;

    @Autowired
    public CustomerPickerSummary(MainView mainController, CustomerService customerService) {
        this.mainController = mainController;
        this.customerService = customerService;
    }

    @FXML
    private void initialize() {
        partialFilter.addListener(((observable, oldValue, newValue) -> selectedCustomer.set(null)));

        // customerSummary should always show selectedCustomer's first name and last name
        customerSummary.textProperty().bind(
                Bindings.createStringBinding(
                        () -> {
                            Customer customer = selectedCustomer.get();
                            if (customer == null) {
                                return "No customer selected";
                            }
                            return "Customer: " + customer.getFirstName() + " " + customer.getLastName();
                        },
                        selectedCustomer
                )
        );
    }

    public void setAddNewCustomerButtonEnabled(boolean isEnabled) {
        this.addNewCustomerButton.visibleProperty().set(isEnabled);
    }

    @FXML
    public void handleSelectCustomer(ActionEvent ignoredEvent) {
        mainController.showCustomerPicker(dialogStage, partialFilter.get(), selectedCustomer::set);
    }

    @FXML
    public void handleNewCustomer(ActionEvent ignoredEvent) {
        mainController.showAddCustomerDialog(new Customer(), toSave -> {
            var addedCustomer = customerService.addNewCustomer(toSave);
            selectedCustomer.set(addedCustomer);
        });
    }
}
