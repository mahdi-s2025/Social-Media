package org.example.socialmedia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;

import java.io.IOException;
import java.nio.file.Paths;

public class HelloApplication extends Application {

    @Getter
    @Setter
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        HelloApplication.stage = stage;
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loadPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        String path1 = Paths.get("src/main/resources/org/example/pictures/mohammad.jpg").toAbsolutePath().toString();
        String path2 = Paths.get("src/main/resources/org/example/pictures/me.jpg").toAbsolutePath().toString();
        String path3 = Paths.get("src/main/resources/org/example/pictures/mehdii.jpg").toAbsolutePath().toString();
        String path4 = Paths.get("src/main/resources/org/example/pictures/diamond.png").toAbsolutePath().toString();


        Account account1 = new Account("mohammad" , "mohammad" , "mh","mohammadjamshidi","file:"+path1);
        Account account2 = new Account("poone" , "poone" , "pm","poone2025!","file:"+path2);
        Account account3 = new Account("Mehdi" , "mehdi" , "ms","mehdisemsarzade","file:"+path3);

        Account account4 = new Account("test" , "test1" , "test" , "t" , "file:" + path4);
        Account account5 = new Account("test" , "test2" , "test" , "t" , "file:" + path4);
        Account account6 = new Account("test" , "test3" , "test" , "t" , "file:" + path4);
        Account account7 = new Account("test" , "test4" , "test" , "t" , "file:" + path4);
        Account account8 = new Account("test" , "test5" , "test" , "t" , "file:" + path4);

        DataCenterController dataCenterController = DataCenterController.getDataCenterController();

        Graph graph = Graph.getGraph() ;

        graph.addVertex(account1.getUsername());
        graph.addVertex(account2.getUsername());
        graph.addVertex(account3.getUsername());

        graph.addVertex(account4.getUsername());
        graph.addVertex(account5.getUsername());
        graph.addVertex(account6.getUsername());
        graph.addVertex(account7.getUsername());
        graph.addVertex(account8.getUsername());

        dataCenterController.addUser(account1);
        dataCenterController.addUser(account2);
        dataCenterController.addUser(account3);

        dataCenterController.addUser(account4);
        dataCenterController.addUser(account5);
        dataCenterController.addUser(account6);
        dataCenterController.addUser(account7);
        dataCenterController.addUser(account8);


        launch();
    }
}