package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;
import org.example.socialmedia.Models.Post;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class homePageController implements Initializable {
    @FXML
    private Label bioLB;

    @FXML
    private ScrollPane connectionsSP;

    @FXML
    private Button logout_btn;

    @FXML
    private Button logout_btn1;

    @FXML
    private Label name1;

    @FXML
    private Label name2;

    @FXML
    private Label name3;

    @FXML
    private Label name4;

    @FXML
    private Label name5;

    @FXML
    private Label name6;

    @FXML
    private Label nameLB;

    @FXML
    private Label name_lbl;

    @FXML
    private ScrollPane postsSP;

    @FXML
    private VBox postsVbox;

    @FXML
    private ImageView prof;

    @FXML
    private ImageView prof1;

    @FXML
    private ImageView prof2;

    @FXML
    private ImageView prof3;

    @FXML
    private ImageView prof4;

    @FXML
    private ImageView prof5;

    @FXML
    private ImageView prof6;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLB.setText(AccountController.getAccountController().getCurrentAccount().getName());
        String path = Paths.get(AccountController.getAccountController().getCurrentAccount().getProfilePicture()).toAbsolutePath().toString();
        System.out.println(path);
        prof.setImage(new Image("file:" + path));
        bioLB.setText(AccountController.getAccountController().getCurrentAccount().getBio());
        name_lbl.setText(AccountController.getAccountController().getCurrentAccount().getName());
        for (Post post:AccountController.getAccountController().getCurrentAccount().getPosts()){
            Label description = new Label(post.getDescription());
            Image image = new Image(post.getFile());
            ImageView postCover = new ImageView(image);
            postCover.setFitWidth(200);
            postCover.setPreserveRatio(true);
            AnchorPane infoPane = new AnchorPane();
            postCover.setLayoutX(0);
            postCover.setLayoutY(0);
            description.setLayoutX(250);
            description.setLayoutY(20);
            infoPane.getChildren().addAll( postCover,description);
            postsVbox.getChildren().add(infoPane);

        }
    }


    @FXML
    void logoutClick(ActionEvent event) throws IOException {
        AccountController.setStage("loginPage.fxml");
    }
    @FXML
    void connect(){
        Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),name1.getText());

        Graph.getGraph().setProbability();
    }
    @FXML
    void addNewPost() throws IOException {
        AccountController.setStage("newPostPage.fxml");
    }
}
