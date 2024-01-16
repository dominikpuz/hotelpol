package pl.edu.agh.to2.hotel.presenter.filter;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * This class can be used to create a TextFormatter based on given regex, so that you can
 * set TextFields' TextFormatters
 */
public class TextFormatterFactory {
    public static final Pattern NONNEGATIVE_INTEGER_REGEX = Pattern.compile("0|([1-9][0-9]*)?");
    public static final Pattern MONEY_REGEX = Pattern.compile("(0|([1-9][0-9]*))?(.[0-9]{0,2})?");

    static public <T> TextFormatter<T> getTextFormatter(StringConverter<T> valueConverter, T defaultValue, Pattern regex) {
        return new TextFormatter<>(
                valueConverter,
                defaultValue,
                getUnaryOperator(regex));
    }

    static private UnaryOperator<TextFormatter.Change> getUnaryOperator(Pattern regex) {
        return change -> {
            String newText = change.getControlNewText();
            if (newText.matches(regex.pattern())) {
                return change;
            }
            return null;
        };
    }
}
