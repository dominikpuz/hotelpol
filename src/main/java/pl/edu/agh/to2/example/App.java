package pl.edu.agh.to2.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class App extends Application  {
    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new Label("test"));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
