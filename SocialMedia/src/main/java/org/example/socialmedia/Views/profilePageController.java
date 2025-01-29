package org.example.socialmedia.Views;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
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
            Label subject = new Label(post.getSubject());
            Label description = new Label(post.getDescription());
            Label likes = new Label(String.valueOf(post.getLikes().size()));

            ImageView likeImage = new ImageView();
            String path = Paths.get("src/main/resources/org/example/pictures/like.jpg").toAbsolutePath().toString();
            likeImage.setImage(new Image("file:" + path));
            likeImage.setPreserveRatio(true);
            likeImage.setFitWidth(25);
            likes.setText(String.valueOf(post.getLikes().size()));

            Image image = new Image(post.getFile());
            ImageView postCover = new ImageView(image);
            Button editPostBT = new Button("Edit");
            int postIndex = user.getPosts().indexOf(post);

            editPostBT.setId(String.valueOf(postIndex));
            postCover.setFitWidth(200);
            postCover.setPreserveRatio(true);
            AnchorPane infoPane = new AnchorPane();
            postCover.setLayoutX(0);
            postCover.setLayoutY(0);

            subject.setLayoutX(250);
            subject.setLayoutY(20);

            description.setLayoutX(250);
            description.setLayoutY(50);

            editPostBT.setLayoutX(350);
            editPostBT.setLayoutY(80);

            likeImage.setLayoutX(250);
            likeImage.setLayoutY(150);
            likes.setLayoutX(300);
            likes.setLayoutY(150);

            likeImage.setOnMouseClicked(event -> {
                String username = user.getUsername();
                if (post.getLikes().contains(username)){
                    post.getLikes().remove(username);
                }
                else {
                    post.getLikes().add(user.getUsername());
                }
                likes.setText(String.valueOf(post.getLikes().size()));
            });

            infoPane.getChildren().addAll(postCover, subject , description, editPostBT , likes , likeImage);

            postsVbox.getChildren().add(infoPane);
            editPostBT.setOnAction(event -> {
                EditPostPageController.event = event;
                try {
                    AccountController.setScene("EditPostPage.fxml", "Edit Post");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
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
