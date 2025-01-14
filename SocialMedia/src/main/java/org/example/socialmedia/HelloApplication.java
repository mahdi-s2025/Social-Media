package org.example.socialmedia.Views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.socialmedia.Database.Database;
import org.example.socialmedia.Models.Account;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        System.out.println(fxmlLoader);
        fxmlLoader.load();
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Database database = Database.getDatabase();

        Account account1 = new Account("mohammad" , "Mohammad" , "mohammad");

        database.AddNewAccount(account1.getName() , account1.getUsername() , account1.getPassword());
        launch();
    }
}