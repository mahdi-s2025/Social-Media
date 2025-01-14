module org.example.socialmedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;


    opens org.example.socialmedia to javafx.fxml;
    exports org.example.socialmedia;
    exports org.example.socialmedia.Views;
    opens org.example.socialmedia.Views to javafx.fxml;
}