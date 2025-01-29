package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPostPageController implements Initializable {
    private final static Stage postStage = homePageController.getPostStage();

    @FXML
    private TextArea descriptionTF;

    @FXML
    private ImageView imageView;

    @FXML
    private Label subject_lbl;

    @FXML
    private Button done_btn;

    @FXML
    private Button select_btn;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField subject_txt;

    private String file;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BooleanBinding emptyFields = subject_txt.textProperty().isEmpty().
                or(imageView.imageProperty().isNull()).or(descriptionTF.textProperty().isEmpty());
        done_btn.disableProperty().bind(emptyFields);
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
            Image image = new Image(selectedFile.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(400);
            imageView.setPreserveRatio(true);
            select_btn.setOpacity(0.5);
        }
    }
    @FXML
    void addPost(ActionEvent event) throws IOException {
        if (file != null && !subject_txt.getText().isEmpty()) {
            Post post = new Post(AccountController.getAccountController().getCurrentAccount(), file, subject_txt.getText(), descriptionTF.getText());
            AccountController.getAccountController().getCurrentAccount().getPosts().add(post);
            postStage.hide();
            AccountController.setScene("homePage.fxml", "Home");
        }
    }
    @FXML
    void backToHome(MouseEvent event) throws IOException {
        postStage.hide();
        //AccountController.setScene("homePage.fxml", "Home");
    }
}

