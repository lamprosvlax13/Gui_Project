module com.example.guiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.FrontEnd to javafx.fxml;
    exports com.example.FrontEnd;
    exports com.example.Utils;
    opens com.example.Utils to javafx.fxml;
}