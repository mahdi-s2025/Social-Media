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
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class loginPageController implements Initializable {

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
    private Label signup_btn;

    @FXML
    private TextField username_txt;

    @FXML
    void googleLoginClicked(ActionEvent event) {

    }

    @FXML
    void lbl_signup_mouseEntered(MouseEvent event) {

    }

    @FXML
    void lbl_signup_mouseExited(MouseEvent event) {

    }

    @FXML
    void loginClick(ActionEvent event) {

        String username = username_txt.getText();
        String password = password_txt.getText();

        try {

            username_txt.clear();
            password_txt.clear();
            error_lbl.setText(null);
            AccountController ac = AccountController.getAccountController();
            ac.login(username, password);

            Stage stage = HelloApplication.getStage();
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("homePage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Home");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            error_lbl.setText(e.getMessage());

        }


    }

    @FXML
    void signupClick(MouseEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signupPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Signup");
        stage.setScene(scene);
        stage.show();
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

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();
            if (text.matches("^[a-zA-Z0-9_]+") || text.isEmpty()) {
                return change;
            }
            return null;
        };

        BooleanBinding fieldsEmpty = username_txt.textProperty().isEmpty()
                .or(password_txt.lengthProperty().lessThan(8));
        login_btn.disableProperty().bind(fieldsEmpty);

        TextFormatter<String> formatter = new TextFormatter<>(filter);
        username_txt.setTextFormatter(formatter);

    }

}
