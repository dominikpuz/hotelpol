package pl.edu.agh.to2.hotel.presenter.room;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.model.filters.RoomFilter;
import pl.edu.agh.to2.hotel.persistance.room.RoomStandard;
import pl.edu.agh.to2.hotel.presenter.filter.FilteringPresenter;
import pl.edu.agh.to2.hotel.presenter.filter.IllegalFilterInput;
import pl.edu.agh.to2.hotel.presenter.filter.TextFormatterFactory;

import java.util.Optional;

@Component
@Scope("prototype")
public class RoomFilteringPresenter extends FilteringPresenter<RoomFilter> {
    @FXML
    private TextField roomName;
    @FXML
    private TextField floorNumber;
    @FXML
    private TextField minPrice;
    @FXML
    private TextField maxPrice;
    @FXML
    private ChoiceBox<RoomStandard> roomStandard;

    @Override
    @FXML
    protected void initialize() {
        floorNumber.setTextFormatter(
                TextFormatterFactory.getTextFormatter(
                        new IntegerStringConverter(),
                        null,
                        TextFormatterFactory.NONNEGATIVE_INTEGER_REGEX)
        );
        minPrice.setTextFormatter(
                TextFormatterFactory.getTextFormatter(
                        new DoubleStringConverter(),
                        null,
                        TextFormatterFactory.MONEY_REGEX)
        );
        maxPrice.setTextFormatter(
                TextFormatterFactory.getTextFormatter(
                        new DoubleStringConverter(),
                        null,
                        TextFormatterFactory.MONEY_REGEX)
        );
        roomStandard.getItems().addAll(RoomStandard.values());
    }

    @Override
    public RoomFilter createFilter() throws IllegalFilterInput {
        var filter = RoomFilter.builder()
                .roomNumber(roomName.getText().trim().isBlank() ? null : roomName.getText().trim())
                .floor(readAndValidateFloorNumber().orElse(null))
                .standard(roomStandard.getSelectionModel().getSelectedItem())
                .minRentPrice(readAndValidateMinPrice().orElse(null))
                .maxRentPrice(readAndValidateMaxPrice().orElse(null))
                .build();

        if (filter.minRentPrice() != null && filter.maxRentPrice() != null && filter.minRentPrice() > filter.maxRentPrice()) {
            throw new IllegalFilterInput("Minimal rent value cannot be smaller than maximal rent value.");
        }

        return filter;
    }

    @Override
    protected void resetModelFilter() {
        modelFilter.set(RoomFilter.builder().build());
    }

    @Override
    protected void resetControls() {
        roomName.setText("");
        floorNumber.setText("");
        roomStandard.getSelectionModel().clearSelection();
        minPrice.setText("");
        maxPrice.setText("");
    }

    private Optional<Integer> readAndValidateFloorNumber() throws IllegalFilterInput {
        int readFloorNumber;
        if (floorNumber.getText() == null || floorNumber.getText().isEmpty()) {
            return Optional.empty();
        }

        try {
            readFloorNumber = Integer.parseInt(floorNumber.getText().trim());
        } catch (NumberFormatException e) {
            throw new IllegalFilterInput("Invalid floor number. Please enter a valid integer.");
        }

        if (readFloorNumber < 0) {
            throw new IllegalFilterInput("Floor cannot be an integer smaller than zero.");
        }

        return Optional.of(readFloorNumber);
    }

    private Optional<Double> readAndValidateMinPrice() throws IllegalFilterInput {
        String readMinPrice = minPrice.getText().trim();
        if (readMinPrice.isEmpty()) return Optional.empty();

        if (!TextFormatterFactory.MONEY_REGEX.matcher(readMinPrice).matches()) {
            throw new IllegalFilterInput("Invalid minimal rent price. Please enter a valid value.");
        }

        double parsedMinPrice;
        try {
            parsedMinPrice = Double.parseDouble(readMinPrice);
        } catch (NumberFormatException e) {
            throw new IllegalFilterInput("The minimal rent price passed the regex but not parsing. Please, contact the programmers, because this shouldn't have happened.");
        }
        return Optional.of(parsedMinPrice);
    }

    private Optional<Double> readAndValidateMaxPrice() throws IllegalFilterInput {
        String readMaxPrice = maxPrice.getText().trim();
        if (readMaxPrice.isEmpty()) return Optional.empty();

        if (!TextFormatterFactory.MONEY_REGEX.matcher(readMaxPrice).matches()) {
            throw new IllegalFilterInput("Invalid maximal rent price. Please enter a valid value.");
        }

        double parsedMaxPrice;
        try {
            parsedMaxPrice = Double.parseDouble(readMaxPrice);
        } catch (NumberFormatException e) {
            throw new IllegalFilterInput("The maximal rent price passed the regex but not parsing. Please, contact the programmers, because this shouldn't have happened.");
        }
        return Optional.of(parsedMaxPrice);
    }

}
