package pl.edu.agh.to2.hotel.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberValidator {

    private static final String PHONE_NUMBER_REGEX = "^(\\+\\d{1,3}-?)?\\d{1,14}$";

    private static final Pattern pattern = Pattern.compile(PHONE_NUMBER_REGEX);

    public static boolean isValidPhoneNumber(String phoneNumber) {
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }
}
