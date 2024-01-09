package pl.edu.agh.to2.hotel.fxml.validation;

public interface IValidator<T> {
    ValidationResult validate(T data);
}
