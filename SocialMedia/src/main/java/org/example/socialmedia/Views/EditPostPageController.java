package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Post;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditPostPageController implements Initializable {
    private final static Stage editPostStage = profilePageController.getEditPostStage();

    private String file;

    @FXML
    private Button done_btn;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private ImageView imageView;

    @FXML
    private AnchorPane root;

    @FXML
    private Button select_btn;

    @FXML
    private Label subject_lbl;

    @FXML
    private TextField subject_txt;

    @FXML
    private Button delete_btn;

    static Event event;

    int postIndex;
    Post post;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        BooleanBinding emptyFields = subject_txt.textProperty().isEmpty().
                or(imageView.imageProperty().isNull()).or(descriptionTF.textProperty().isEmpty());
        done_btn.disableProperty().bind(emptyFields);

        Button data = (Button) event.getSource();

        postIndex = Integer.parseInt(data.getId());
        post = AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex);
        subject_txt.setText(post.getSubject());
        descriptionTF.setText(post.getDescription());

        file = post.getFile();
        Image image = new Image(post.getFile());
        imageView.setImage(image);
    }
    @FXML
    void backToProfile(MouseEvent event) throws IOException {
        editPostStage.close();
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
            imageView.setImage(image);
//            postImage.setFitWidth(400);
//            imageView.setPreserveRatio(true);
        }
    }

    @FXML
    void addPost(ActionEvent event) throws IOException {
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setSubject(subject_txt.getText());
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setDescription(descriptionTF.getText());
        AccountController.getAccountController().getCurrentAccount().getPosts().get(postIndex).setFile(file);
        editPostStage.close();
        AccountController.setScene("profilePage.fxml", "Profile");
    }

    @FXML
    void deletePost(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Post");
        alert.setContentText("Are you sure you want to delete this Post");
        alert.showAndWait();

        if (alert.getResult().getText().equals("OK")){
            AccountController.getAccountController().getCurrentAccount().getPosts().remove(postIndex);
            AccountController.setScene("profilePage.fxml", "Profile");
        }
    }


}
