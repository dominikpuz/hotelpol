package pl.edu.agh.to2.hotel.presenter.customer;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.fxml.validation.RegexValidator;
import pl.edu.agh.to2.hotel.fxml.validation.ValidationTextField;
import pl.edu.agh.to2.hotel.model.Customer;
import pl.edu.agh.to2.hotel.presenter.ActionDialogPresenter;

import java.util.List;
import java.util.function.Consumer;

@Component
public class CustomerActionDialog extends ActionDialogPresenter<Customer, Customer> {
    @FXML
    public ValidationTextField firstName;
    @FXML
    public ValidationTextField email;
    @FXML
    public ValidationTextField phoneNumber;
    @FXML
    public ValidationTextField lastName;

    @Override
    public void initializeDialog(Stage stage, Customer model, Consumer<Customer> onSave) {
        super.initializeDialog(stage, model, onSave);
        email.addValidation(new RegexValidator("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$", "To nie jest poprawny email!"));
        phoneNumber.addValidation(new RegexValidator("^(\\+\\d{1,3}-?)?\\d{1,14}$", "To nie jest poprawny numer telefonu!"));
    }

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
        List<ValidationTextField> form = List.of(firstName, lastName, email, phoneNumber);
        form.forEach(ValidationTextField::validateField);
        boolean isFormValid = form.stream().allMatch(ValidationTextField::isValid);
        if(!isFormValid) return false;

        model.setFirstName(firstName.getText());
        model.setLastName(lastName.getText());
        model.setPhoneNumber(phoneNumber.getText());
        model.setEmail(email.getText());
        return tryDoDefaultAction(model);
    }
}
