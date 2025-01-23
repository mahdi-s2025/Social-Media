package org.example.socialmedia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;

import java.io.IOException;

public class HelloApplication extends Application {

    @Getter
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Account account1 = new Account("mohammad" , "mohammad" , "mh",":)","src/main/resources/org/example/pictures/mohammad.jpg");
        Account account2 = new Account("poone" , "poone" , "pm",":)","src/main/resources/org/example/pictures/me.jpg");
        Account account3 = new Account("Mehdi" , "mehdi" , "ms",":)","src/main/resources/org/example/pictures/mehdii.jpg");

        Graph graph = Graph.getGraph() ;

        graph.addVertex(account1.getUsername());
        graph.addVertex(account2.getUsername());
        graph.addVertex(account3.getUsername());
        DataCenterController.getInstance().getUsers().add(account1);
        DataCenterController.getInstance().getUsers().add(account2);
        DataCenterController.getInstance().getUsers().add(account3);


        launch();
    }
}