package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
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
    private ImageView addPostBT;

    @FXML
    private ImageView btn1;

    @FXML
    private ImageView btn2;

    @FXML
    private ImageView btn3;

    @FXML
    private ImageView btn4;

    @FXML
    private ImageView btn5;

    @FXML
    private ImageView logout_btn;

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
    private Label name_lbl;

    @FXML
    private ScrollPane postsSP;

    @FXML
    private VBox postsVbox;

    @FXML
    private Circle prof;

    @FXML
    private Circle prof1;

    @FXML
    private Circle prof2;

    @FXML
    private Circle prof3;

    @FXML
    private Circle prof4;

    @FXML
    private Circle prof5;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane suggestionGrid;

    @FXML
    private Label user1;

    @FXML
    private Label user2;

    @FXML
    private Label user3;

    @FXML
    private Label user4;

    @FXML
    private Label user5;

    @FXML
    private Label username_lbl;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_lbl.setText(AccountController.getAccountController().getCurrentAccount().getName());
        username_lbl.setText(AccountController.getAccountController().getCurrentAccount().getName());

        Image profile = new Image(AccountController.getAccountController().getCurrentAccount().getProfilePicture());
        prof.setFill(new ImagePattern(profile));
        prof.setStroke(Color.SEAGREEN);
        prof.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        int pstIndex=0;
        List<Account> connections = Graph.getGraph().findUserConnections(AccountController.getAccountController().getCurrentAccount().getUsername());
        for (Account account : connections){

            if(account.getPosts().isEmpty()){
                pstIndex++;
                continue;
            }

            Post post = account.getPosts().getLast();
            Label subject = new Label(post.getSubject());
            Label description = new Label(post.getDescription());
            Label likes = new Label(String.valueOf(post.getLikes().size()));
            Label dateAndTime = new Label();

            ImageView likeImage = new ImageView();
            String path = Paths.get("src/main/resources/org/example/pictures/like.jpg").toAbsolutePath().toString();
            likeImage.setImage(new Image("file:" + path));
            likeImage.setPreserveRatio(true);
            likeImage.setFitWidth(25);
            likes.setText(String.valueOf(post.getLikes().size()));

            dateAndTime.setText(post.getDateAndTime());

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
                    commentsPageController.event2 = event2;
                    AccountController.setStage("commentsPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            pstIndex++;
        }

        ArrayList<Account> suggestions = Graph.getGraph().getSuggestions();

        ArrayList<Circle> profiles = new ArrayList<>();
        profiles.add(prof1);
        profiles.add(prof2);
        profiles.add(prof3);
        profiles.add(prof4);
        profiles.add(prof5);

        ArrayList<Label> usernames = new ArrayList<>();
        usernames.add(user1);
        usernames.add(user2);
        usernames.add(user3);
        usernames.add(user4);
        usernames.add(user5);

        ArrayList<Label> names = new ArrayList<>();
        names.add(name1);
        names.add(name2);
        names.add(name3);
        names.add(name4);
        names.add(name5);

        ArrayList<ImageView> connectButtons = new ArrayList<>();
        connectButtons.add(btn1);
        connectButtons.add(btn2);
        connectButtons.add(btn3);
        connectButtons.add(btn4);
        connectButtons.add(btn5);


        for (int i = 0 ; i < suggestions.size() ; i++){
            String username_lbl = suggestions.get(i).getUsername();

            Image image = new Image(suggestions.get(i).getProfilePicture());
            profiles.get(i).setFill(new ImagePattern(image));

            usernames.get(i).setText(suggestions.get(i).getUsername());

            names.get(i).setText(suggestions.get(i).getName());

            connectButtons.get(i).setVisible(true);
            connectButtons.get(i).setOnMouseClicked(event -> {
                Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username_lbl);
                try {
                    AccountController.setStage("homePage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }



    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        AccountController.setStage("loginPage.fxml");
    }

    @FXML
    void connect(MouseEvent event) {
        String username = ((Control) event.getSource()).getId();
        Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username);
        AccountController.showAlert("", Alert.AlertType.CONFIRMATION,"Connected to "+AccountController.getAccountController().getCurrentAccount().getUsername()+" Successfully!");
    }

    @FXML
    void addNewPost(MouseEvent event) throws IOException {
        AccountController.setStage("newPostPage.fxml");
    }
    @FXML
    void profClick(MouseEvent event) throws IOException {
        AccountController.setStage("ProfilePage.fxml");
    }
}
