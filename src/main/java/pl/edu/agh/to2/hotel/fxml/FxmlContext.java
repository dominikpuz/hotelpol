package pl.edu.agh.to2.hotel.fxml;

import javafx.scene.layout.Pane;

public record FxmlContext<T extends IFxmlPresenter>(
        T controller,
        Pane view
) {}
