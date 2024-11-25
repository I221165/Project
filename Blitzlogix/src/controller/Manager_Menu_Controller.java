package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Manager_Menu_Controller 
{
	public void manage_parcel(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manage_Parcel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Parcel");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
            //((Stage)((Node)e.getSource()).getScene().getWindow()).close();   
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
	}
	public void receive_feedback(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Receive_Feedback.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Receive Feedback");
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
