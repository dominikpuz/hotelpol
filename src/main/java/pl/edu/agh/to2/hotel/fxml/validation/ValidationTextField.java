package pl.edu.agh.to2.hotel.fxml.validation;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.to2.hotel.App;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValidationTextField extends VBox {

    @FXML
    private TextField textField;
    @FXML
    private Label errorLabel;

    private final StringProperty errorMessage = new SimpleStringProperty("");

    @Getter
    @Setter
    private boolean required = true;

    private final List<IValidator<String>> validators = new ArrayList<>();

    @Getter
    private boolean isValid = false;

    public ValidationTextField() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ValidationTextField.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        errorLabel.textProperty().bind(errorMessage);
        textField.textProperty().addListener(((observable, oldValue, newValue) -> isValid = validate(newValue)));
    }

    public String getText() {
        return textField.getText();
    }

    public void setText(String text) {
        textField.setText(text);
    }

    public TextField getTextField() {
        return textField;
    }

    public StringProperty errorMessageProperty() {
        return errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage.get();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage.set(errorMessage);
    }

    private boolean validate(String text) {
        if(text == null || text.isBlank()) {
            if(required) {
                setErrorMessage("Pole wymagane!");
                return false;
            }
        }
        var failedValidation = validators.stream().map(validator -> validator.validate(text)).filter(ValidationResult::failedValidation).findFirst();
        if(failedValidation.isPresent()) {
            setErrorMessage(failedValidation.get().errorMessage());
        }else {
            setErrorMessage("");
        }
        return failedValidation.isEmpty();
    }

    public boolean validateField() {
        String text = getText();
        this.isValid = validate(text);
        return isValid;
    }

    public void addValidation(IValidator<String> validator) {
        this.validators.add(validator);
    }
}

