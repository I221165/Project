package view;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Manage_Parcel extends Application 
{
	private int width;
	private int height;
	public Manage_Parcel()
	{
		this.width = 700;
		this.height = 700;
	}
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Manage_Parcel.fxml"));
            Scene scene = new Scene(root, this.width, this.height);
            scene.getStylesheets().add(getClass().getResource("Manage_Parcel.css").toExternalForm());
            primaryStage.setTitle("Manage Parcel");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
