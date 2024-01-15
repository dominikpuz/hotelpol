package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.InfoDialogPresenter;

@Component
public class CustomerInfoDialog extends InfoDialogPresenter<Customer> {
    @FXML
    public Label firstName;
    @FXML
    public Label lastName;
    @FXML
    public Label email;
    @FXML
    public Label phoneNumber;

    @Override
    public void loadData() {
        firstName.setText(model.getFirstName());
        lastName.setText(model.getLastName());
        email.setText(model.getEmail());
        phoneNumber.setText(model.getPhoneNumber());
    }
}
