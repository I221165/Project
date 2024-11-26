module Blitzlogix 
{
	requires javafx.controls;
	requires javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	requires java.sql;
}
