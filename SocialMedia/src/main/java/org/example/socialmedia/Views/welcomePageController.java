package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.socialmedia.HelloApplication;

public class welcomePageController {
    private final Stage stage = HelloApplication.getStage();

    @FXML
    private Button close_btn;

    @FXML
    private Button start_btn;

    @FXML
    void closeClick(ActionEvent event) {
        stage.close();
    }

    @FXML
    void startClick(ActionEvent event) throws Exception {
        stage.close();
        Thread.sleep(500);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.centerOnScreen();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
        HelloApplication.setStage(stage);
    }

}

