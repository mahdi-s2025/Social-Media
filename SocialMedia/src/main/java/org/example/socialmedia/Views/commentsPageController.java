package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Comment;
import org.example.socialmedia.Models.Graph;
import org.example.socialmedia.Models.Post;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class commentsPageController implements Initializable {

    private static final Stage commentStage = homePageController.getCommentStage();

    @FXML
    private ImageView addCommentBT;

    @FXML
    private ScrollPane commentsSP;

    @FXML
    private VBox commentsVbox;

    @FXML
    private TextArea contentTF;

    @FXML
    private AnchorPane root;


    Post currentPost;
    static Event event2;
    int postIndex;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        BooleanBinding emptyComment = contentTF.textProperty().isEmpty();
        addCommentBT.disableProperty().bind(emptyComment);



        ImageView data = (ImageView) event2.getSource();
        String[] id = data.getId().split("-");
        postIndex=Integer.parseInt(id[0]);
        List<Account> connections= Graph.getGraph().findUserConnections(id[1]);
        Account poster=connections.get(0);
         currentPost=poster.getPosts().get(postIndex);
        for(Comment comment:currentPost.getComments()){

            FXMLLoader fxml = new FXMLLoader(HelloApplication.class.getResource("comment.fxml"));

            HBox mainHbox;

            try {
                mainHbox = fxml.load();

                Circle profile = (Circle) mainHbox.getChildren().get(0);
                Image image = new Image(comment.getWriter().getProfilePicture());
                profile.setFill(new ImagePattern(image));

                VBox infoVbox = (VBox) mainHbox.getChildren().get(1);

                Label username = (Label) infoVbox.getChildren().get(0);
                Label commentTxt = (Label) infoVbox.getChildren().get(1);

                username.setText(comment.getWriter().getUsername());
                commentTxt.setText(comment.getContent());

                Label date = (Label) mainHbox.getChildren().get(2);

                date.setText(comment.getDateAndTime());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            commentsVbox.getChildren().add(mainHbox);
        }
    }


    @FXML
    void addComment(MouseEvent event) throws IOException {
        Comment comment=new Comment(AccountController.getAccountController().getCurrentAccount(),contentTF.getText());
        currentPost.getComments().add(comment);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("commentsPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        commentStage.setScene(scene);
        //AccountController.setScene("commentsPage.fxml", "Comments");
    }
    @FXML
    void backToHome(MouseEvent event) throws IOException {
        commentStage.hide();
        //AccountController.setScene("homePage.fxml", "Home");
    }
}
