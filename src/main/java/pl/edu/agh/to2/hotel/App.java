package pl.edu.agh.to2.hotel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContext;
import pl.edu.agh.to2.hotel.fxml.FxmlContextProvider;
import pl.edu.agh.to2.hotel.presenter.MainView;

import static pl.edu.agh.to2.hotel.fxml.FxmlContextType.MAIN_VIEW;

public class App extends Application  {
    private ConfigurableApplicationContext applicationContext;
    private FxmlContextProvider fxmlContextProvider;


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
    public void start(Stage primaryStage) {
        FxmlContext<MainView> mainContext = fxmlContextProvider.load(MAIN_VIEW);

        primaryStage.setTitle("HotelPol");
        Scene scene = new Scene(mainContext.view(), 800, 400);
        primaryStage.setScene(scene);
        mainContext.controller().setPrimaryStage(primaryStage);
        primaryStage.show();
    }
}
