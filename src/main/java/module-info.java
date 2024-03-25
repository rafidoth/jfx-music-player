module com.mp.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires atlantafx.base;
    requires com.jfoenix;



    opens com.mp.demo to javafx.fxml;
    exports com.mp.demo;
    exports com.mp.demo.Controllers;
    opens com.mp.demo.Controllers to javafx.fxml;
}