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
	public void track_parcel(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Track_Parcel.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Track Parcel");
            stage.setScene(new Scene(root, 700, 700));
            stage.show();
            //((Stage)((Node)e.getSource()).getScene().getWindow()).close();   
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
	}
	public void manage_parcel_intra(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manage_Parcel_Intra.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Parcel Intra");
            stage.setScene(new Scene(root, 1300, 700));
            stage.show();
            //((Stage)((Node)e.getSource()).getScene().getWindow()).close();   
        }
        catch (IOException ex) 
        {
            ex.printStackTrace();
        }
	}
	public void manage_parcel_inter(ActionEvent e)
	{
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Manage_Parcel_Inter.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Manage Parcel Inter");
            stage.setScene(new Scene(root, 1300, 700));
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
