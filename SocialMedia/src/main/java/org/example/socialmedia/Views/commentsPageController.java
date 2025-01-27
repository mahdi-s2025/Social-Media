package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Comment;
import org.example.socialmedia.Models.Graph;
import org.example.socialmedia.Models.Post;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class commentsPageController implements Initializable {
    @FXML
    private Button addCommentBT;

    @FXML
    private ScrollPane commentsSP;

    @FXML
    private TextArea contentTF;
    Post currentPost;
    static Event event2;
    int postIndex;
    @FXML
    VBox commentsVbox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Button data = (Button) event2.getSource();
        postIndex=Integer.parseInt(data.getId());
        //System.out.println(postIndex);
        List<Account> connections= Graph.getGraph().findUserConnections(AccountController.getAccountController().getCurrentAccount().getUsername());
        Account poster=connections.get(postIndex);
         currentPost=poster.getPosts().getLast();
        for(Comment comment:currentPost.getComments()){
            AnchorPane pane=new AnchorPane();
            ImageView prof=new ImageView();
            Image profile=new Image(comment.getWriter().getProfilePicture());
            prof.setImage(profile);
            prof.setFitWidth(30);
            prof.setPreserveRatio(true);
            prof.setLayoutX(5);
            prof.setLayoutY(5);

            Label userName=new Label();
            userName.setText(comment.getWriter().getUsername());
            userName.setLayoutX(5);
            userName.setLayoutY(35);

            Label contentLB=new Label();
            contentLB.setText(comment.getContent());
            contentLB.setLayoutX(40);
            contentLB.setLayoutY(5);
            pane.getChildren().addAll(prof,userName,contentLB);
            commentsVbox.getChildren().add(pane);
        }
    }
    @FXML
    void addComment(ActionEvent event) throws IOException {
        Comment comment=new Comment(AccountController.getAccountController().getCurrentAccount(),contentTF.getText());
        currentPost.getComments().add(comment);
        AccountController.setStage("commentsPage.fxml");
    }

    @FXML
    void backToHome(ActionEvent event) throws IOException {
        AccountController.setStage("homePage.fxml");
    }
}
