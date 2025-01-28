module org.example.socialmedia {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires java.mail;
    requires com.google.api.client.auth;
    requires google.api.client;
    requires com.google.api.client;
    requires com.google.api.client.json.gson;
    requires com.google.api.services.gmail;
    requires org.apache.commons.codec;
    requires com.google.api.client.extensions.java6.auth;
    requires com.google.api.client.extensions.jetty.auth;
    requires jdk.httpserver;



    opens org.example.socialmedia to javafx.fxml;
    exports org.example.socialmedia;
    exports org.example.socialmedia.Views;
    opens org.example.socialmedia.Views to javafx.fxml;

}

