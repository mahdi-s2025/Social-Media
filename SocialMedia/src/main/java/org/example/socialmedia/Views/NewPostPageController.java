package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
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
        Post post=new Post(file,descriptionTF.getText());
        AccountController.getAccountController().getCurrentAccount().getPosts().add(post);
        AccountController.showAlert("Post Added!", Alert.AlertType.INFORMATION,"Done!");
        AccountController.setStage("HomePage.fxml");
    }
}

