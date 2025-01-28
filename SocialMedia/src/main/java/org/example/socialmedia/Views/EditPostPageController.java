package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class EditPostPageController implements Initializable {
    String file;

    @FXML
    private Button backBT;

    @FXML
    private Button confirmBT;

    @FXML
    private TextArea postCaption;

    @FXML
    private ImageView postImage;

    @FXML
    private Button uploadBT;

    @FXML
    private TextField subject_txt;
    static Event event;

    int postIndex;
    Post post;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button data = (Button) event.getSource();

        postIndex = Integer.parseInt(data.getId());
        post = AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex);
        subject_txt.setText(post.getSubject());
        postCaption.setText(post.getDescription());

        file = post.getFile();
        Image image = new Image(post.getFile());
        postImage.setImage(image);
    }
    @FXML
    void backToProfile(ActionEvent event) throws IOException {
        AccountController.setStage("profilePage.fxml");
    }

    @FXML
    void chooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            file=selectedFile.toURI().toString();
            Image image = new Image(file);
            postImage.setImage(image);
            postImage.setFitWidth(400);
            postImage.setPreserveRatio(true);
        }
    }

    @FXML
    void confirmChanges(ActionEvent event) throws IOException {
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setSubject(subject_txt.getText());
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setDescription(postCaption.getText());
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setFile(file);
        AccountController.setStage("profilePage.fxml");
    }


}
