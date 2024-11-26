package view;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Manage_Parcel_Inter extends Application 
{
	private int width;
	private int height;
	public Manage_Parcel_Inter()
	{
		this.width = 1300;
		this.height = 700;
	}
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Manage_Parcel_Inter.fxml"));
            Scene scene = new Scene(root, this.width, this.height);
            scene.getStylesheets().add(getClass().getResource("Manage_Parcel_Inter.css").toExternalForm());
            primaryStage.setTitle("Manage Parcel Inter");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
