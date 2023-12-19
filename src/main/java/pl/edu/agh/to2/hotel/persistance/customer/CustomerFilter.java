package pl.edu.agh.to2.hotel.persistance.customer;

import lombok.Builder;

@Builder
public record CustomerFilter(
        String firstName,
        String lastName,
        String phone,
        String email
) {}