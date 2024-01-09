package pl.edu.agh.to2.hotel.fxml.validation;

import java.util.regex.Pattern;

public class RegexValidator implements IValidator<String> {

    private final Pattern regex;

    private final String errorMessage;

    public RegexValidator(Pattern regex, String errorMessage) {
        this.regex = regex;
        this.errorMessage = errorMessage;
    }

    public RegexValidator(String pattern, String errorMessage) {
        this(Pattern.compile(pattern), errorMessage);
    }

    @Override
    public ValidationResult validate(String data) {
        if(regex.matcher(data).matches()) {
            return new ValidationResult(true);
        }else {
            return new ValidationResult(false, errorMessage);
        }
    }
}
