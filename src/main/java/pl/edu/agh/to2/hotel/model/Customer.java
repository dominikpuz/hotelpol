package pl.edu.agh.to2.hotel.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Customer implements IPresentableModel {

    private final long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;

    public Customer() {
        this.id = -1;
    }

    public Customer(String firstName, String lastName, String phoneNumber, String email) {
        this(-1, firstName, lastName, phoneNumber, email);
    }

    public Customer(long id, String firstName, String lastName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
