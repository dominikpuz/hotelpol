package pl.edu.agh.to2.hotel.model.filters;

import lombok.Builder;

@Builder
public record CustomerFilter(
        String firstName,
        String lastName,
        String phone,
        String email
) implements IMergeableFilter<CustomerFilter> {

    @Override
    public CustomerFilter mergeFilter(CustomerFilter otherFilter) {
        if(otherFilter == null) return this;
        return CustomerFilter.builder().
                firstName(mergeValues(firstName, otherFilter.firstName)).
                lastName(mergeValues(lastName, otherFilter.lastName)).
                phone(mergeValues(phone, otherFilter.phone)).
                email(mergeValues(email, otherFilter.email))
                .build();
    }
}