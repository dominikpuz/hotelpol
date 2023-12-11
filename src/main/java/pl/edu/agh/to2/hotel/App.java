package pl.edu.agh.to2.hotel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import pl.edu.agh.to2.hotel.presenter.MainView;

import java.io.IOException;

public class App extends Application  {
    private ConfigurableApplicationContext applicationContext;
    private FXMLLoaderProvider fxmlLoaderProvider;


    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
        fxmlLoaderProvider = new FXMLLoaderProvider();
        fxmlLoaderProvider.setApplicationContext(applicationContext);
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = fxmlLoaderProvider.load("MainView");
        Parent root = fxmlLoader.load();
        MainView mainView = fxmlLoader.getController();
        primaryStage.setTitle("HotelPol");
        Scene scene = new Scene(root, 800, 400);
        primaryStage.setScene(scene);
        mainView.setPrimaryStage(primaryStage);
        primaryStage.show();
    }
}
