

package controller;
import model.*;
//import blitzlogix.*;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Duration;

public class MainMenuController {

	
	
	
    @FXML
    private void openUserRegistration() {
        loadScene("/view/Login.fxml", "User Registration");
    }

    @FXML
    private void openDriverRegistration() {
        loadScene("/view/AdminLogin.fxml", "Driver Registration");
    }
    
    @FXML 
    private void openDriverLogin() {
    	loadScene("/view/DriverLogin.fxml","Driver Login");
    }
    @FXML
    private void openManagerLogin() {
    	loadScene("/view/Manager_Login.fxml","Manager Login");
    }

    
    private void loadScene(String fxmlFile, String title) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
