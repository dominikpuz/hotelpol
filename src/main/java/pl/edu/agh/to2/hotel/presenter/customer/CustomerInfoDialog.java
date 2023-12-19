package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.InfoDialogPresenter;

@Component
public class CustomerInfoDialog extends InfoDialogPresenter<Customer> {
    @FXML
    public Text firstName;
    @FXML
    public Text lastName;
    @FXML
    public Text email;
    @FXML
    public Text phoneNumber;

    @Override
    public void loadData() {
        firstName.setText(model.getFirstName());
        lastName.setText(model.getLastName());
        email.setText(model.getEmail());
        phoneNumber.setText(model.getPhoneNumber());
    }
}
