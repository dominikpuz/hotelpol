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
import pl.edu.agh.to2.hotel.presenter.MainView;

import static pl.edu.agh.to2.hotel.fxml.FxmlContextType.MAIN_VIEW;

public class App extends Application  {
    private ConfigurableApplicationContext applicationContext;
    private FxmlContextProvider fxmlContextProvider;
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
        FxmlContext<MainView> mainContext = fxmlContextProvider.load(MAIN_VIEW);
        primaryStage.setTitle("HotelPol");
        Pane mainView = mainContext.view();
        Scene scene = new Scene(mainView, mainView.getPrefWidth(), mainView.getPrefHeight());
        primaryStage.setScene(scene);
        mainContext.controller().setPrimaryStage(primaryStage);
        primaryStage.show();
    }
}
