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
    private Button editPost;

    @FXML
    private Label emailLB;

    @FXML
    private Button logout_btn;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLB.setText(AccountController.getAccountController().getCurrentAccount().getName());
        Image profile = new Image(AccountController.getAccountController().getCurrentAccount().getProfilePicture());
        prof.setImage(profile);
        emailLB.setText(AccountController.getAccountController().getCurrentAccount().getEmail());
        name_lbl.setText(AccountController.getAccountController().getCurrentAccount().getName());
        for (Post post : AccountController.getAccountController().getCurrentAccount().getPosts()) {
            Label description = new Label(post.getDescription());
            Image image = new Image(post.getFile());
            ImageView postCover = new ImageView(image);
            Button editPostBT = new Button("Edit");
            int postIndex = AccountController.getAccountController().getCurrentAccount().getPosts().indexOf(post);
            editPostBT.setId(String.valueOf(postIndex));
            postCover.setFitWidth(200);
            postCover.setPreserveRatio(true);
            AnchorPane infoPane = new AnchorPane();
            postCover.setLayoutX(0);
            postCover.setLayoutY(0);
            description.setLayoutX(250);
            description.setLayoutY(20);

            editPostBT.setLayoutX(350);
            editPostBT.setLayoutY(80);
            infoPane.getChildren().addAll(postCover, description, editPostBT);
            postsVbox.getChildren().add(infoPane);
            editPostBT.setOnAction(event -> {
                EditPostPageController.event = event;
                try {
                    AccountController.setStage("EditPostPage.fxml");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

        }

        setConnectionList(AccountController.getAccountController().getCurrentAccount().getUsername());

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
                nameLB.setText(user.getName());
                Image profilePic = new Image(user.getProfilePicture());
                prof.setImage(profilePic);
                emailLB.setText(user.getEmail());
                name_lbl.setText(user.getName());
                connectionVbox.getChildren().clear();
                for (Post post:user.getPosts()) {
                    Label description = new Label(post.getDescription());
                    Image postImage = new Image(post.getFile());
                    ImageView postCover = new ImageView(postImage);
                    postCover.setFitWidth(200);
                    postCover.setPreserveRatio(true);
                    AnchorPane infoPane = new AnchorPane();
                    postCover.setLayoutX(0);
                    postCover.setLayoutY(0);
                    description.setLayoutX(250);
                    description.setLayoutY(20);

                    infoPane.getChildren().addAll(postCover, description);
                    postsVbox.getChildren().add(infoPane);
                }

                if (user != AccountController.getAccountController().getCurrentAccount()) {
                    editPost.setVisible(false);
                    deleteAccount.setVisible(false);
                    logout_btn.setVisible(false);
                }
                else {
                    editPost.setVisible(true);
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
        AccountController.setStage("loginPage.fxml");
    }

    @FXML
    void logoutClick(ActionEvent event) throws IOException {
        AccountController.setStage("loginPage.fxml");
    }

    @FXML
    void editProfile() throws IOException {
        AccountController.setStage("EditProfilePage.fxml");
    }

    @FXML
    void backToHome() throws IOException {
        AccountController.setStage("homePage.fxml");
    }
}
