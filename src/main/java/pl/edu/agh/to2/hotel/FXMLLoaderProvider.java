package pl.edu.agh.to2.hotel;

import javafx.fxml.FXMLLoader;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FXMLLoaderProvider {
    private static final String FXML = ".fxml";
    private static ApplicationContext applicationContext;

    public FXMLLoaderProvider() {
    }

    public FXMLLoader load(final String viewName) {
        FXMLLoader loader;
        loader = new FXMLLoader();
        loader.setControllerFactory(applicationContext::getBean);
        loader.setLocation(getClass().getResource(viewName + FXML));
        return loader;
    }

    public void setApplicationContext(final ApplicationContext applicationContext) {
        FXMLLoaderProvider.applicationContext = applicationContext;
    }
}
