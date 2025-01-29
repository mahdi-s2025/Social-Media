package org.example.socialmedia.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import org.example.socialmedia.HelloApplication;

import java.io.IOException;

public class PostController {

    @FXML
    private ImageView comment;

    @FXML
    private Label date_lbl;

    @FXML
    private Label description_lbl;

    @FXML
    private ImageView like;

    @FXML
    private Label likeCount_lbl;

    @FXML
    private Label name_lbl;

    @FXML
    private Rectangle postPhoto;

    @FXML
    private Circle profile;

    @FXML
    private Label subject_lbl;

    @FXML
    private Label username_lbl;


    @FXML
    void addComment(MouseEvent event) throws IOException {

        commentsPageController.event2 = event;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commentsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        homePageController.getCommentStage().setScene(scene);
        homePageController.getCommentStage().show();
    }

}
