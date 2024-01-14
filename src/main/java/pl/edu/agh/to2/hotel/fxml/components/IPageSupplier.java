package pl.edu.agh.to2.hotel.fxml.components;

import org.springframework.data.domain.Page;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

@FunctionalInterface
public interface IPageSupplier<Record extends IPresentableModel> {
    Page<Record> supply(int page);
}
