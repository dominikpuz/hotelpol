package pl.edu.agh.to2.hotel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;

public class App extends Application  {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(Main.class).run();
    }

    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Label("test"));
        stage.setScene(scene);
        stage.show();
    }
}
