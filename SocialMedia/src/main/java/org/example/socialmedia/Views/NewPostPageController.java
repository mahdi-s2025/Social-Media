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
import org.example.socialmedia.Models.Post;

import java.io.File;
import java.io.IOException;

public class NewPostPageController {

    @FXML
    private Button chooseImageButton;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private ImageView imageView;

    @FXML
    private Label subject_lbl;

    @FXML
    private TextField subject_txt;
    String file;

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
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
        }
    }
    @FXML
    void addPost(ActionEvent event) throws IOException {
        if (file != null && !subject_txt.getText().isEmpty()) {
            Post post = new Post(AccountController.getAccountController().getCurrentAccount(), file, subject_txt.getText(), descriptionTF.getText());
            AccountController.getAccountController().getCurrentAccount().getPosts().add(post);
            AccountController.showAlert("Post Added!", Alert.AlertType.INFORMATION, "Done!");
            AccountController.setStage("HomePage.fxml");
        }
    }
    @FXML
    void backToHome(ActionEvent event) throws IOException {
        AccountController.setStage("homePage.fxml");
    }
}

