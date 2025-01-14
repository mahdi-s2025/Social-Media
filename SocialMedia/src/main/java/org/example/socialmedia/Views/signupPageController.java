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

public class signupPageController {


    @FXML
    private Button login_btn;

    @FXML
    private Label name_lbl;

    @FXML
    private TextField name_txt;

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
    void signupClick(ActionEvent event) throws SQLException {

        String name = name_txt.getText();
        String username = username_txt.getText();
        String password = password_txt.getText();

        AccountController ac = new AccountController();
        ac.signup(name , username , password);
    }

    @FXML
    void loginClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
