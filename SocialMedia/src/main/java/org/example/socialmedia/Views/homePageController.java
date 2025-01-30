package org.example.socialmedia.Views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;
import org.example.socialmedia.Models.Post;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    private static final Stage currentStage = HelloApplication.getStage();

    @Getter
    private static final Stage commentStage = new Stage();

    @Getter
    private static final Stage postStage = new Stage();

    static {
        commentStage.setResizable(false);
        commentStage.initOwner(currentStage);
        commentStage.initModality(Modality.WINDOW_MODAL);
        commentStage.initStyle(StageStyle.TRANSPARENT);

        postStage.setResizable(false);
        postStage.initModality(Modality.WINDOW_MODAL);
        postStage.initOwner(currentStage);
        postStage.initStyle(StageStyle.TRANSPARENT);
    }

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

        int pstIndex;
        List<Account> connections = Graph.getGraph().findUserConnections(AccountController.getAccountController().getCurrentAccount().getUsername());
        for (Account account : connections){

            if(account.getPosts().isEmpty()){
                continue;
            }

            Post post = account.getPosts().getLast();

            pstIndex = account.getPosts().indexOf(post);

            FXMLLoader fxml = new FXMLLoader(HelloApplication.class.getResource("post.fxml"));

            HBox mainHbox;

            try {

                mainHbox = fxml.load();

                VBox firstSection = (VBox) mainHbox.getChildren().get(0);

                HBox user = (HBox) firstSection.getChildren().get(0);

                Circle profilePhoto = (Circle) user.getChildren().get(0);
                Image image = new Image(account.getProfilePicture());
                profilePhoto.setFill(new ImagePattern(image));

                profilePhoto.setCursor(Cursor.HAND);

                profilePhoto.setOnMouseClicked(event -> {
                    try {
                        profilePageController.userProfile = account;
                        AccountController.setScene("ProfilePage.fxml", "Profile");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                VBox userInfo = (VBox) user.getChildren().get(1);
                Label username = (Label) userInfo.getChildren().get(0);
                Label name = (Label) userInfo.getChildren().get(1);
                username.setText(account.getUsername());
                name.setText(account.getName());


                VBox postInfo = (VBox) firstSection.getChildren().get(1);

                Label subject = (Label) postInfo.getChildren().get(0);
                Label description = (Label) postInfo.getChildren().get(1);

                subject.setText(post.getSubject());
                description.setText(post.getDescription());

                VBox secondSection = (VBox) mainHbox.getChildren().get(1);


                Rectangle postPhoto = (Rectangle) secondSection.getChildren().get(0);
                Image image2 = new Image(post.getFile());
                postPhoto.setFill(new ImagePattern(image2));


                HBox action = (HBox) secondSection.getChildren().get(1);

                ImageView like = (ImageView) action.getChildren().get(0);

                String likePath = Paths.get("src/main/resources/org/example/pictures/red heart.png").toAbsolutePath().toString();
                String notLikePath = Paths.get("src/main/resources/org/example/pictures/heart.png").toAbsolutePath().toString();
                Image likeImage = new Image("file:" + likePath);
                Image notLikeImage = new Image("file:" + notLikePath);

                if ((post.getLikes().contains(connections.get(0).getUsername()))) {
                    like.setImage(likeImage);
                } else {
                    like.setImage(notLikeImage);
                }

                Label likeCount = (Label) action.getChildren().get(1);
                likeCount.setText(String.valueOf(post.getLikes().size()));

                like.setCursor(Cursor.HAND);

                like.setOnMouseClicked(event -> {
                    String username1 = connections.getFirst().getUsername();

                    if (post.getLikes().contains(username1)){
                        like.setImage(notLikeImage);

                        post.getLikes().remove(username1);
                    }
                    else {

                        like.setImage(likeImage);

                        post.getLikes().add(connections.getFirst().getUsername());
                    }
                    likeCount.setText(String.valueOf(post.getLikes().size()));
                });

                ImageView comment = (ImageView) action.getChildren().get(2);
                String id = pstIndex + "-" + account.getUsername();
                comment.setId(id);

                Label date = (Label) action.getChildren().get(3);
                date.setText(post.getDateAndTime());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            postsVbox.getChildren().add(mainHbox);
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
                    AccountController.setScene("homePage.fxml", "Home");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }
    }



    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        AccountController.setScene("loginPage.fxml", "Login");
    }

    @FXML
    void connect(MouseEvent event) {
        String username = ((Control) event.getSource()).getId();
        Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username);
//        AccountController.showAlert("", Alert.AlertType.CONFIRMATION,"Connected to "+AccountController.getAccountController().getCurrentAccount().getUsername()+" Successfully!");
    }

    @FXML
    void addNewPost(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("newPostPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        postStage.setScene(scene);
        postStage.show();
    }
    @FXML
    void profClick(MouseEvent event) throws IOException {
        AccountController.setScene("ProfilePage.fxml", "Profile");
    }
}
