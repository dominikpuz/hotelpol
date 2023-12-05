package pl.edu.agh.to2.hotel.persistance.reservation;

public enum ReservationState {
    /**
     * Newly created reservation, not yet paid for
     */
    CREATED,

    /**
     * Paid reservation. Customer not checked in yet
     */
    PAID,

    CANCELLED,

    /**
     * The customer has arrived and has checked in. They are yet to check out
     */
    CHECKED_IN,

    CHECKED_OUT
}
