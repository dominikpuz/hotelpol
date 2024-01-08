package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;

@Builder
public record CustomerFilter(
        String firstName,
        String lastName,
        String phone,
        String email
) implements IModelFilter {
    @Override
    public IModelFilter mergeFilter(IModelFilter filterToMerge) {
        if (!(filterToMerge instanceof CustomerFilter otherFilter)) {
            throw new IllegalArgumentException("Cannot merge filter of different class");
        }

        return CustomerFilter.builder().
                firstName(mergeValues(firstName, otherFilter.firstName)).
                lastName(mergeValues(lastName, otherFilter.lastName)).
                phone(mergeValues(phone, otherFilter.phone)).
                email(mergeValues(email, otherFilter.email))
                .build();
    }
}