package org.example.socialmedia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;

import java.io.IOException;
import java.nio.file.Paths;

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
        String path1 = Paths.get("src/main/resources/org/example/pictures/mohammad.jpg").toAbsolutePath().toString();
        String path2 = Paths.get("src/main/resources/org/example/pictures/me.jpg").toAbsolutePath().toString();
        String path3 = Paths.get("src/main/resources/org/example/pictures/mehdii.jpg").toAbsolutePath().toString();


        Account account1 = new Account("mohammad" , "mohammad" , "mh","m","file:"+path1);
        Account account2 = new Account("poone" , "poone" , "pm","p","file:"+path2);
        Account account3 = new Account("Mehdi" , "mehdi" , "ms","mh","file:"+path3);

        DataCenterController dataCenterController = DataCenterController.getDataCenterController();

        Graph graph = Graph.getGraph() ;

        graph.addVertex(account1.getUsername());
        graph.addVertex(account2.getUsername());
        graph.addVertex(account3.getUsername());

        dataCenterController.addUser(account1);
        dataCenterController.addUser(account2);
        dataCenterController.addUser(account3);


        launch();
    }
}