package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    @FXML
    private Button addPostBT;
    @FXML
    private Button logout_btn;

    @FXML
    private Button btn1;

    @FXML
    private Button btn2;

    @FXML
    private Button btn3;

    @FXML
    private Button btn4;

    @FXML
    private Button btn5;

    @FXML
    private Button btn6;

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

    @FXML
    private GridPane suggestionGrid;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLB.setText(AccountController.getAccountController().getCurrentAccount().getName());
        Image profile=new Image(AccountController.getAccountController().getCurrentAccount().getProfilePicture());
        prof.setImage(profile);
        int pstIndex=0;
        name_lbl.setText(AccountController.getAccountController().getCurrentAccount().getName());
        List<Account> connections= Graph.getGraph().findUserConnections(AccountController.getAccountController().getCurrentAccount().getUsername());
        for (Account account:connections){
            if(account.getPosts().isEmpty()){
                pstIndex++;
                continue;
            }
            Post post=account.getPosts().getLast();
            Label description = new Label(post.getDescription());
            Label likes = new Label(String.valueOf(post.getLikes().size()));
            Label dateAndTime = new Label();
            Label subject = new Label();
            ImageView likeImage = new ImageView();
            String path = Paths.get("src/main/resources/org/example/pictures/like.jpg").toAbsolutePath().toString();
            likeImage.setImage(new Image("file:" + path));
            likeImage.setPreserveRatio(true);
            likeImage.setFitWidth(25);
            likes.setText(String.valueOf(post.getLikes().size()));
            dateAndTime.setText(post.getDateAndTime());
            subject.setText(post.getSubject());
            Button comments = new Button("Comments");
            comments.setId(String.valueOf(pstIndex));
            Image image = new Image(post.getFile());
            ImageView postCover = new ImageView(image);
            postCover.setFitWidth(170);
            postCover.setPreserveRatio(true);
            AnchorPane infoPane = new AnchorPane();
            ImageView posterPhoto = new ImageView(new Image(post.getPoster().getProfilePicture()));
            posterPhoto.setLayoutX(0);
            posterPhoto.setLayoutY(0);
            posterPhoto.setFitWidth(40);
            posterPhoto.setPreserveRatio(true);
            Label posterName = new Label(post.getPoster().getUsername());
            posterName.setLayoutX(45);
            posterName.setLayoutY(5);

            postCover.setLayoutX(50);
            postCover.setLayoutY(40);
            description.setLayoutX(290);
            description.setLayoutY(100);
            subject.setLayoutX(290);
            subject.setLayoutY(50);
            dateAndTime.setLayoutX(240);
            dateAndTime.setLayoutY(200);
            comments.setLayoutX(510);
            comments.setLayoutY(200);
            likeImage.setLayoutX(435);
            likeImage.setLayoutY(200);
            likes.setLayoutX(480);
            likes.setLayoutY(200);

            infoPane.getChildren().addAll( postCover,description,posterName,posterPhoto,likes,subject,dateAndTime,comments,likeImage);
            postsVbox.getChildren().add(infoPane);
            likeImage.setOnMouseClicked(event -> {
                String username = connections.get(0).getUsername();
                if (post.getLikes().contains(username)){
                    post.getLikes().remove(username);
                }
                else {
                    post.getLikes().add(connections.get(0).getUsername());
                }
                likes.setText(String.valueOf(post.getLikes().size()));
            });
            comments.setOnAction(event2 -> {
                try {
                    commentsPageController.event2=event2;
                    AccountController.setStage("commentsPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pstIndex++;
        }

        ArrayList<Account> suggestions = Graph.getGraph().getSuggestions();

        suggestionGrid.getChildren().clear();

        for (int i = 0 ; i < suggestions.size() ; i++){
            String username_lbl = suggestions.get(i).getUsername();

            VBox vBox = new VBox();

            Image image = new Image(suggestions.get(i).getProfilePicture());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Label username = new Label(suggestions.get(i).getUsername());

            Button connect = new Button("Connect");

            connect.setOnAction(event -> {
                Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username_lbl);
                try {
                    AccountController.setStage("homePage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            vBox.getChildren().add(imageView);
            vBox.getChildren().add(username);
            vBox.getChildren().add(connect);

            suggestionGrid.add(vBox , i , 0);
        }
    }


    @FXML
    void logoutClick(ActionEvent event) throws IOException {
        AccountController.setStage("loginPage.fxml");
    }
    @FXML
    void connect(ActionEvent event) {
        String username = ((Control) event.getSource()).getId();
        Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username);
        AccountController.showAlert("", Alert.AlertType.CONFIRMATION,"Connected to "+AccountController.getAccountController().getCurrentAccount().getUsername()+" Successfully!");
    }
    @FXML
    void addNewPost() throws IOException {
        AccountController.setStage("newPostPage.fxml");
    }

    @FXML
    void goToProfile() throws IOException {
        AccountController.setStage("ProfilePage.fxml");
    }
}
