package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;

@Builder
public record CustomerFilter(
        String firstName,
        String lastName,
        String phone,
        String email
) implements IModelFilter {}