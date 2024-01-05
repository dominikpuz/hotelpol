package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;

@Component
public class CustomerActionDialog extends ActionDialogPresenter<Customer> {
    @FXML
    public TextField firstName;
    @FXML
    public TextField email;
    @FXML
    public TextField phoneNumber;
    @FXML
    public TextField lastName;

    @Override
    public void loadData() {
        firstName.setText(model.getFirstName());
        lastName.setText(model.getLastName());
        phoneNumber.setText(model.getPhoneNumber());
        email.setText(model.getEmail());
    }

    @Override
    public boolean isAddingDialog() {
        return model.getId() == -1;
    }

    @Override
    public boolean validateAndSubmitModel() {
        model.setFirstName(firstName.getText());
        model.setLastName(lastName.getText());
        model.setPhoneNumber(phoneNumber.getText());
        model.setEmail(email.getText());
        return true;
    }
}
