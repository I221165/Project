package application;

import java.io.File;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {

        	File fxmlFile = new File("MainMenu.fxml");
            URL fxmlUrl = fxmlFile.toURI().toURL();
            //Parent root = FXMLLoader.load(fxmlUrl);
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            Scene scene = new Scene(root, 700, 700);
            
            scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            primaryStage.setTitle("Blitzlogix");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
