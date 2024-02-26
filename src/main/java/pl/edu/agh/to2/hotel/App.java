package pl.edu.agh.to2.hotel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.fxml.components.MainComponent;
import pl.edu.agh.to2.hotel.presenter.MainView;

import static pl.edu.agh.to2.hotel.fxml.FxmlContextType.MAIN_COMPONENT;
import static pl.edu.agh.to2.hotel.fxml.FxmlContextType.MAIN_VIEW;

public class App extends Application  {
    private ConfigurableApplicationContext applicationContext;
    @Getter
    private static FxmlContextProvider fxmlContextProvider;
    @Getter
    private static Stage primaryStage;


    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
        fxmlContextProvider = new FxmlContextProvider();
        fxmlContextProvider.setApplicationContext(applicationContext);
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        FxmlContext<MainComponent> mainContext = fxmlContextProvider.load(MAIN_COMPONENT);
        primaryStage.setTitle("HotelPol");
        Pane mainView = mainContext.view();
        Scene scene = new Scene(mainView, mainView.getPrefWidth(), mainView.getPrefHeight());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
