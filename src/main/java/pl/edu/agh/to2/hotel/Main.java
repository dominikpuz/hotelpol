package pl.edu.agh.to2.hotel;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Logger;

@SpringBootApplication
public class Main {

	private static final Logger log = Logger.getLogger(Main.class.toString());

	public static void main(String[] args) {
		log.info("Hello world");
		Application.launch(App.class, args);
	}
}
