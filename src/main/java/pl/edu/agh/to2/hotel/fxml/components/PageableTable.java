package pl.edu.agh.to2.hotel.fxml.components;

import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import pl.edu.agh.to2.hotel.model.IPresentableModel;

public abstract class PageableTable<Model extends IPresentableModel> extends VBox {

    @FXML
    protected TableView<Model> tableView;

    @FXML
    @Getter
    protected Pagination tablePagination;

    @Setter
    protected IPageSupplier<Model> pageSupplier;

    private int currentPage = 0;

    @FXML
    private void initialize() {
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        initializeCellValueFactories();
        tablePagination.currentPageIndexProperty().addListener(((observable, oldValue, newValue) -> reloadData(newValue.intValue())));
    }

    protected abstract void initializeCellValueFactories();

    public void reloadData(int page) {
        Page<Model> data = pageSupplier.supply(page);
        tablePagination.setPageCount(data.getTotalPages());
        tableView.getItems().clear();
        tableView.getItems().addAll(data.getContent());
        currentPage = page;
    }

    public void reloadData() {
        reloadData(currentPage);
    }

    public void reloadDataAndShowFirstPage() {
        reloadData(0);
    }

    public final TableView.TableViewSelectionModel<Model> getSelectionModel() {
        return tableView.getSelectionModel();
    }
}
