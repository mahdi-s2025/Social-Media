package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class signupPageController {
    @FXML
    private TextArea bioText;

    @FXML
    private Button login_btn;

    @FXML
    private TextField name_txt;

    @FXML
    private Label password_lbl;

    @FXML
    private PasswordField password_txt;

    @FXML
    private ImageView profPhoto;

    @FXML
    private Button signup_btn;

    @FXML
    private Label username_lbl;

    @FXML
    private Label username_lbl1;

    @FXML
    private TextField username_txt;

    String file;
    @FXML
    void signupClick(ActionEvent event) throws Exception {

        String name = name_txt.getText();
        String username = username_txt.getText();
        String email = "";
        String password = password_txt.getText();

        AccountController ac = AccountController.getAccountController();
        boolean signup = ac.signup(name , username , email , password , file);

        if (signup) {
            AccountController.setStage("homePage.fxml");
        }
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
    @FXML
    void onChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
             file=selectedFile.toURI().toString();
            Image image = new Image(file);
            profPhoto.setImage(image);
            profPhoto.setFitWidth(400);
            profPhoto.setPreserveRatio(true);
        }
    }
}
