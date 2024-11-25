package controller;



import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Manager_Login_Controller 
{
	public void login(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manager_Menu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manager Menu");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
            //((Stage)((Node)e.getSource()).getScene().getWindow()).close();
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
	}
}
