module Blitzlogix 
{
	requires javafx.controls;
	requires javafx.fxml;
	opens application to javafx.graphics, javafx.fxml;
	opens view to javafx.graphics, javafx.fxml;
	opens controller to javafx.graphics, javafx.fxml;
	opens model to javafx.graphics, javafx.fxml, javafx.base;
	
    exports model;
    exports view;               // If you use FXML in `view`
	requires java.sql;
}
