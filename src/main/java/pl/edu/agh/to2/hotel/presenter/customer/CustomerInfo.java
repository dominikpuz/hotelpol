package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.Setter;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.Customer;

@Component
public class CustomerInfo {
    @FXML
    public Text firstName;
    @FXML
    public Text lastName;
    @FXML
    public Text email;
    @FXML
    public Text phoneNumber;
    private Stage dialogStage;
    @Setter
    private Customer customer;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
        initializeControls();
    }

    private void initializeControls() {
        firstName.setText(customer.getFirstName());
        lastName.setText(customer.getLastName());
        email.setText(customer.getEmail());
        phoneNumber.setText(customer.getPhoneNumber());
    }
    @FXML
    public void handleOkAction(ActionEvent actionEvent) {
        dialogStage.close();
    }
}
