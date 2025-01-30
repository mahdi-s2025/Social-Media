package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class loginPageController implements Initializable {

    @Getter
    private static final Stage signupStage = new Stage();
    private static Stage primaryStage = HelloApplication.getStage();
    @Getter
    private static final Stage googleStage = new Stage();

    static {
        signupStage.setTitle("Signup");
        signupStage.setResizable(false);
        signupStage.initOwner(primaryStage);
        signupStage.initModality(Modality.APPLICATION_MODAL);

        googleStage.setTitle("Login");
        googleStage.setResizable(false);
        googleStage.initOwner(primaryStage);
        googleStage.initModality(Modality.APPLICATION_MODAL);

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signupPage.fxml"));
        FXMLLoader fxmlLoader1 = new FXMLLoader(HelloApplication.class.getResource("googlePage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            signupStage.setScene(scene);

            Scene scene1 = new Scene(fxmlLoader1.load());
            googleStage.setScene(scene1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private Label error_lbl;

    @FXML
    private AnchorPane root;


    @FXML
    private Button googleLogin_btn1;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password_txt;


    @FXML
    private TextField username_txt;

    @FXML
    void googleLoginClicked(ActionEvent event) {
        primaryStage = HelloApplication.getStage();
        googleStage.show();
    }



    @FXML
    void loginClick(ActionEvent event) {

        String username = username_txt.getText();
        String password = password_txt.getText();

        try {

            username_txt.clear();
            password_txt.clear();
            AccountController ac = AccountController.getAccountController();
            ac.login(username, password);

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            primaryStage = HelloApplication.getStage();
            primaryStage.setTitle("Home");
            primaryStage.setScene(scene);

        } catch (Exception e) {
            error_lbl.setText(e.getMessage());

        }
    }

    @FXML
    void signupClick(MouseEvent event) {
        primaryStage = HelloApplication.getStage();
        signupStage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username_txt.setFocusTraversable(false);
        password_txt.setFocusTraversable(false);


        root.setOnMouseClicked(event -> {
            if (username_txt.isFocused() || password_txt.isFocused()) {
                root.requestFocus();
            }
        });

        UnaryOperator<TextFormatter.Change> usernameFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("^[a-zA-Z0-9_]+") || text.isEmpty()) {
                return change;
            }
            return null;
        };

        TextFormatter<String> formatter = new TextFormatter<>(usernameFilter);
        username_txt.setTextFormatter(formatter);

        UnaryOperator<TextFormatter.Change> passwordFilter = change -> {
            String text = change.getControlNewText();
            if (text.matches("^[a-zA-Z0-9_!@#$%^&*()]+") || text.isEmpty()) {
                return change;
            }
            return null;
        };

        TextFormatter<String> formatter2 = new TextFormatter<>(passwordFilter);
        password_txt.setTextFormatter(formatter2);


        BooleanBinding fieldsEmpty = username_txt.textProperty().isEmpty()
                .or(password_txt.lengthProperty().lessThan(8));
        login_btn.disableProperty().bind(fieldsEmpty);

    }

}
