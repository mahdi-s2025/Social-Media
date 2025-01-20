package org.example.socialmedia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.socialmedia.Database.Database;
import org.example.socialmedia.Models.Account;

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

        Database database = Database.getDatabase();

        Account account1 = new Account("mohammad" , "Mohammad" , "mohammad");

        //database.addNewAccount(account1.getName() , account1.getUsername() , account1.getPassword());
        launch();
    }
}