module com.warkop {
    requires transitive javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires transitive javafx.graphics;
    


    opens com.warkop to javafx.fxml;
    exports com.warkop;
}
