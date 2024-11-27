package view;



import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;



public class Customer_Update extends Application 
{
	private int width;
	private int height;
	public Customer_Update()
	{
		this.width = 700;
		this.height = 700;
	}
    @Override
    public void start(Stage primaryStage) 
    {
        try 
        {
            AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Customer_Update.fxml"));
            Scene scene = new Scene(root, this.width, this.height);
            scene.getStylesheets().add(getClass().getResource("Customer_Update.css").toExternalForm());
            primaryStage.setTitle("Customer Update");
            primaryStage.setScene(scene);
            primaryStage.show();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
}
