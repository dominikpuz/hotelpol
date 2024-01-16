package pl.edu.agh.to2.hotel.fxml.validation;

public record ValidationResult(
        boolean passedValidation,
        String errorMessage
) {

    public ValidationResult(boolean passedValidation) {
        this(passedValidation, null);
    }

    public boolean failedValidation() {
        return !passedValidation;
    }
}
