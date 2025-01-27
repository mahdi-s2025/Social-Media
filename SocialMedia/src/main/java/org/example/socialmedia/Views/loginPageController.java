package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Stack;

public class loginPageController {

    @FXML
    private Button login_btn;

    @FXML
    private Label password_lbl;

    @FXML
    private PasswordField password_txt;

    @FXML
    private Button signup_btn;

    @FXML
    private Label username_lbl;

    @FXML
    private TextField username_txt;

    @FXML
    void loginClick(ActionEvent event) throws Exception {

        String username = username_txt.getText();
        String password = password_txt.getText();

        AccountController ac = AccountController.getAccountController();

        boolean login = ac.login(username , password);

        if (login) {
            Stage stage = HelloApplication.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        }

    }

    @FXML
    void signupClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signupPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Signup");
        stage.setScene(scene);
        stage.show();
    }
}
