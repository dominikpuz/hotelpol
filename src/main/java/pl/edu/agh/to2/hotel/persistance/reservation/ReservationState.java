package pl.edu.agh.to2.hotel.persistance.reservation;

import java.util.Arrays;
import java.util.List;

public enum ReservationState {
    /**
     * Newly created reservation, not yet paid for
     */
    CREATED(),

    /**
     * Paid reservation. Customer not checked in yet
     */
    PAID(CREATED),

    CANCELLED(CREATED, PAID),

    /**
     * The customer has arrived and has checked in. They are yet to check out
     */
    CHECKED_IN(PAID),

    CHECKED_OUT(CHECKED_IN);

    public final List<ReservationState> availablePreviousStates;

    ReservationState(ReservationState... availablePreviousStates) {
        this.availablePreviousStates = Arrays.asList(availablePreviousStates);
    }

    public boolean canChangeTo(ReservationState nextState) {
        return nextState.availablePreviousStates.contains(this);
    }
}
