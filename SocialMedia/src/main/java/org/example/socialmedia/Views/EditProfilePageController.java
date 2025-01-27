package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class EditProfilePageController implements Initializable {
    String file = AccountController.getAccountController().getCurrentAccount().getProfilePicture();

    @FXML
    private TextField NameTF;

    @FXML
    private Button backBT;

    @FXML
    private Button confirmBT;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField passwordTF;

    @FXML
    private ImageView prof;

    @FXML
    private Button uploadProfBT;

    @FXML
    private TextField userNameTF;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NameTF.setText(AccountController.getAccountController().getCurrentAccount().getName());
        userNameTF.setText(AccountController.getAccountController().getCurrentAccount().getUsername());
        emailTF.setText(AccountController.getAccountController().getCurrentAccount().getEmail());
        passwordTF.setText(AccountController.getAccountController().getCurrentAccount().getPassword());
        Image profile=new Image(AccountController.getAccountController().getCurrentAccount().getProfilePicture());
        prof.setImage(profile);

    }

    @FXML
    void back(ActionEvent event) throws IOException {
        AccountController.setStage("HomePage.fxml");
    }

    @FXML
    void confirmEdits(ActionEvent event) {
        AccountController.getAccountController().getCurrentAccount().setName(NameTF.getText());
        AccountController.getAccountController().getCurrentAccount().setUsername(userNameTF.getText());
        AccountController.getAccountController().getCurrentAccount().setEmail(emailTF.getText());
        AccountController.getAccountController().getCurrentAccount().setPassword(passwordTF.getText());
        AccountController.getAccountController().getCurrentAccount().setProfilePicture(file);
        AccountController.showAlert("Successful!", Alert.AlertType.CONFIRMATION, "Your informations updated successfully!");
    }

    @FXML
    void uploadPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            file=selectedFile.toURI().toString();
            Image image = new Image(file);
            prof.setImage(image);
            prof.setFitWidth(400);
            prof.setPreserveRatio(true);
        }
    }

}