package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    @FXML
    private Button logout_btn;

    @FXML
    private Label name_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        name_lbl.setText(AccountController.getCurrentAccount().getName());
    }


    @FXML
    void logoutClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

}
