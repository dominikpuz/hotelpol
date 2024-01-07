package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.filters.CustomerFilter;
import pl.edu.agh.to2.hotel.presenter.filter.FilteringPresenter;
import pl.edu.agh.to2.hotel.presenter.filter.IllegalFilterInput;

import java.util.List;

@Component
@Scope("prototype")
public class CustomerFilteringPresenter extends FilteringPresenter<CustomerFilter> {
    @FXML
    public TextField firstName;
    @FXML
    public TextField lastName;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField email;

    @Override
    @FXML
    protected void initialize() {

    }

    @Override
    public CustomerFilter createFilter() throws IllegalFilterInput {
        return CustomerFilter.builder()
                .firstName(firstName.getText())
                .lastName(lastName.getText())
                .phone(phoneNumber.getText())
                .email(email.getText())
                .build();
    }

    @Override
    protected void resetModelFilter() {
        modelFilter.set(CustomerFilter.builder().build());
    }

    @Override
    protected void resetControls() {
        for (TextField textField : List.of(firstName, lastName, phoneNumber, email)) {
            textField.setText(null);
        }
    }
}
