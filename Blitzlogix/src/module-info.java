module Blitzlogix 
{
	requires javafx.controls;
	requires javafx.fxml;
	opens model to javafx.base;
	opens application to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	requires java.sql;
}
