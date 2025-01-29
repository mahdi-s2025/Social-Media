package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
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
import java.util.ResourceBundle;

public class profilePageController implements Initializable {
    @FXML
    private VBox connectionVbox;

    @FXML
    private ScrollPane connectionsSP;

    @FXML
    private Button deleteAccount;

    @FXML
    private Button editProfile;

    @FXML
    private Label email_lbl;

    @FXML
    private ImageView logout_btn;

    @FXML
    private Label name_lbl1;

    @FXML
    private ScrollPane postsSP;

    @FXML
    private VBox postsVbox;

    @FXML
    private ImageView prof;

    @FXML
    private AnchorPane root;

    @FXML
    private Label username_lbl;

    @FXML
    private Label logout_lbl;

    public static Account userProfile = AccountController.getAccountController().getCurrentAccount();


    private void setInformation(Account user){

        connectionVbox.getChildren().clear();
        postsVbox.getChildren().clear();

        name_lbl1.setText(user.getName());
        Image profile = new Image(user.getProfilePicture());
        prof.setImage(profile);
        email_lbl.setText(user.getEmail());
        username_lbl.setText(user.getUsername());


        for (Post post : user.getPosts()) {
            int postIndex = user.getPosts().indexOf(post);
            FXMLLoader fxml;
            if (user.equals(AccountController.getAccountController().getCurrentAccount())){
                fxml = new FXMLLoader(HelloApplication.class.getResource("post2.fxml"));
            }
            else {
                fxml = new FXMLLoader(HelloApplication.class.getResource("post.fxml"));
            }



            HBox mainHbox;

            try {

                mainHbox = fxml.load();

                VBox firstSection = (VBox) mainHbox.getChildren().get(0);

                HBox userSection = (HBox) firstSection.getChildren().get(0);

                Circle profilePhoto = (Circle) userSection.getChildren().get(0);
                Image image = new Image(user.getProfilePicture());
                profilePhoto.setFill(new ImagePattern(image));

                profilePhoto.setCursor(Cursor.HAND);

                profilePhoto.setOnMouseClicked(event -> {
                    try {
                        profilePageController.userProfile = user;
                        AccountController.setScene("ProfilePage.fxml", "Profile");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                VBox userInfo = (VBox) userSection.getChildren().get(1);
                Label username = (Label) userInfo.getChildren().get(0);
                Label name = (Label) userInfo.getChildren().get(1);
                username.setText(user.getUsername());
                name.setText(user.getName());

                if (user.equals(AccountController.getAccountController().getCurrentAccount())){
                    Button editPostBT = (Button) userSection.getChildren().get(2);
                    editPostBT.setId(String.valueOf(post.getID()));
                    editPostBT.setCursor(Cursor.HAND);

                    editPostBT.setOnAction(event -> {
                        EditPostPageController.event = event;
                        try {
                            AccountController.setScene("EditPostPage.fxml", "Edit Post");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }

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

                if ((post.getLikes().contains(user.getUsername()))) {
                    like.setImage(likeImage);
                } else {
                    like.setImage(notLikeImage);
                }

                Label likeCount = (Label) action.getChildren().get(1);
                likeCount.setText(String.valueOf(post.getLikes().size()));

                like.setCursor(Cursor.HAND);

                like.setOnMouseClicked(event -> {
                    String username1 = user.getUsername();

                    if (post.getLikes().contains(username1)){
                        like.setImage(notLikeImage);

                        post.getLikes().remove(username1);
                    }
                    else {

                        like.setImage(likeImage);

                        post.getLikes().add(user.getUsername());
                    }
                    likeCount.setText(String.valueOf(post.getLikes().size()));
                });

                ImageView comment = (ImageView) action.getChildren().get(2);
                String id = postIndex + "-" + user.getUsername();
                comment.setId(id);

                Label date = (Label) action.getChildren().get(3);
                date.setText(post.getDateAndTime());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            postsVbox.getChildren().add(mainHbox);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setInformation(userProfile);


        setConnectionList(userProfile.getUsername());

    }

    private void setConnectionList(String username){
        ArrayList<Account> connections = Graph.getGraph().findUserConnections(username);

        connectionVbox.getChildren().clear();


        for (Account user : connections){
            if (user == connections.get(0)){
                continue;
            }
            HBox hBox = new HBox();
            Label username_lbl = new Label(user.getUsername());
            Image image = new Image(user.getProfilePicture());
            ImageView profileImage = new ImageView(image);
            profileImage.setFitWidth(100);
            profileImage.setFitHeight(100);
            hBox.getChildren().add(profileImage);
            hBox.getChildren().add(username_lbl);

            connectionVbox.getChildren().add(hBox);

            username_lbl.setOnMouseClicked(event -> {

                setInformation(user);

                if (!user.equals(AccountController.getAccountController().getCurrentAccount())) {
                    logout_lbl.setVisible(false);
                    editProfile.setVisible(false);
                    deleteAccount.setVisible(false);
                    logout_btn.setVisible(false);
                }
                else {
                    logout_lbl.setVisible(true);
                    editProfile.setVisible(true);
                    deleteAccount.setVisible(true);
                    logout_btn.setVisible(true);
                }

                setConnectionList(username_lbl.getText());
            });
        }

    }

    @FXML
    void deleteAccount(ActionEvent event) throws IOException {
        DataCenterController.getDataCenterController().deleteUser(AccountController.getAccountController().getCurrentAccount());
        AccountController.showAlert("Successful", Alert.AlertType.INFORMATION, "Your Account deleted Successfully!");
        AccountController.setScene("loginPage.fxml", "Login");
    }

    @FXML
    void logoutClick(MouseEvent event) throws IOException {
        AccountController.setScene("loginPage.fxml", "Login");
    }

    @FXML
    void editProfile() throws IOException {
        AccountController.setScene("EditProfilePage.fxml", "Edit Profile");
    }

    @FXML
    void backToHome() throws IOException {
        AccountController.setScene("homePage.fxml", "Home");
    }
}
