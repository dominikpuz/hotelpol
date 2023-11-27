package pl.edu.agh.to2.hotel.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

@Getter
public class Customer {
    private final long id;
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty phoneNumber;
    private final StringProperty email;

    public Customer() {
        id = -1;
        firstName = new SimpleStringProperty();
        lastName = new SimpleStringProperty();
        phoneNumber = new SimpleStringProperty();
        email = new SimpleStringProperty();
    }

    public Customer(String firstName, String lastName, String phoneNumber, String email) {
        this(-1, firstName, lastName, phoneNumber, email);
    }

    public Customer(long id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.phoneNumber = new SimpleStringProperty(phoneNumber);
        this.email = new SimpleStringProperty(email);
    }

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }
}
