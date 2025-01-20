package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;

public class welcomePageController {

    @FXML
    private Button cancel_btn;

    @FXML
    private Button start_btn;

    @FXML
    void cancelClick(ActionEvent event) {
        HelloApplication.getStage().close();
    }

    @FXML
    void startClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}
