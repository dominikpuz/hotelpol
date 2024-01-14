package pl.edu.agh.to2.hotel.fxml;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import pl.edu.agh.to2.hotel.App;

import java.io.IOException;
import java.net.URL;

@Component
@NoArgsConstructor
public class FxmlContextProvider {
    private static final String FXML = ".fxml";
    private static ApplicationContext applicationContext;

    public <T extends IFxmlPresenter> FxmlContext<T> load(FxmlContextType type) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setControllerFactory(applicationContext::getBean);
            loader.setLocation(getFxmlFileLocation(type));
            Pane view = loader.load();
            T controller = loader.getController();
            return new FxmlContext<>(controller, view);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setApplicationContext(final ApplicationContext applicationContext) {
        FxmlContextProvider.applicationContext = applicationContext;
    }

    public static void loadComponent(String componentName, Object parent) {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(componentName));
        fxmlLoader.setRoot(parent);
        fxmlLoader.setController(parent);

        try {
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private URL getFxmlFileLocation(FxmlContextType type) {
        return App.class.getResource(type.fileName + FXML);
    }
}
