package view;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Manager_Menu extends Application
{
	private int width;
	private int height;
	public Manager_Menu()
	{
		this.width = 700;
		this.height = 700;
	}
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Manager_Menu.fxml"));
            Scene scene = new Scene(root, this.width, this.height);
            scene.getStylesheets().add(getClass().getResource("Manager_Menu.css").toExternalForm());
            primaryStage.setTitle("Manager Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
