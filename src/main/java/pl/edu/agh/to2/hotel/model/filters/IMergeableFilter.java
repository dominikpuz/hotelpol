package pl.edu.agh.to2.hotel.model.filters;

public interface IMergeableFilter<Filter> {
    /**
     * merges two filter fields' values
     * @param myValue - this filter field's value
     * @param otherValue - other filter field's value
     * @return {@code otherValue} if not null, myValue otherwise
     */
    default <T> T mergeValues(T myValue, T otherValue) {
        if (otherValue != null) {
            return otherValue;
        }
        return myValue;
    }

    /**
     * Merges conditions / predicates / constraints from the second filter.
     * If a second filter's field is not null then overwrite this filter's value.
     * <p>
     * Important! The {@code filterToMerge} must be an instance of the same type as this objects
     * @param filterToMerge filter to take non-null conditions from
     * @return filter with merged conditions
     */
    Filter mergeFilter(Filter filterToMerge);
}
