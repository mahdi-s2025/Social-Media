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
//        String path = Paths.get(AccountController.getAccountController().getCurrentAccount().getProfilePicture()).toAbsolutePath().toString();
//        prof.setImage(new Image("file:" + path));
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
            Label likes=new Label(String.valueOf(post.getLikeCounts()));
            Label dateAndTime=new Label();
            Label subject=new Label();
            ImageView likeImage=new ImageView();
            String path = Paths.get("src/main/resources/org/example/pictures/like.jpg").toAbsolutePath().toString();
            likeImage.setImage(new Image("file:" + path));
            likeImage.setPreserveRatio(true);
            likeImage.setFitWidth(25);
            likes.setText(String.valueOf(post.getLikeCounts()));
            dateAndTime.setText(post.getDateAndTime());
            subject.setText(post.getSubject());
            Button comments=new Button("Comments");
            comments.setId(String.valueOf(pstIndex));
            Image image = new Image(post.getFile());
            ImageView postCover = new ImageView(image);
            postCover.setFitWidth(170);
            postCover.setPreserveRatio(true);
            AnchorPane infoPane = new AnchorPane();
            ImageView posterPhoto=new ImageView(new Image(post.getPoster().getProfilePicture()));
            posterPhoto.setLayoutX(0);
            posterPhoto.setLayoutY(0);
            posterPhoto.setFitWidth(40);
            posterPhoto.setPreserveRatio(true);
            Label posterName=new Label(post.getPoster().getUsername());
            posterName.setLayoutX(45);
            posterName.setLayoutY(5);

            postCover.setLayoutX(50);
            postCover.setLayoutY(40);
            description.setLayoutX(240);
            description.setLayoutY(60);
            subject.setLayoutX(290);
            subject.setLayoutY(50);
            dateAndTime.setLayoutX(240);
            dateAndTime.setLayoutY(200);
            comments.setLayoutX(510);
            comments.setLayoutY(200);
            likeImage.setLayoutX(435);
            likeImage.setLayoutY(200);
            likes.setLayoutX(450);
            likes.setLayoutY(200);

            infoPane.getChildren().addAll( postCover,description,posterName,posterPhoto,likes,subject,dateAndTime,comments,likeImage);
            postsVbox.getChildren().add(infoPane);
            likeImage.setOnMouseClicked(event -> {
                post.setLikeCounts(post.getLikeCounts()+1);
                likes.setText(String.valueOf(post.getLikeCounts()));
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

//        suggestionGrid.getChildren().clear();
//
//        for (int i = 0 ; i < suggestions.size() ; i++){
//
//            VBox vBox = new VBox();
//
//            Image image = new Image(suggestions.get(i).getProfilePicture());
//            ImageView imageView = new ImageView(image);
//            imageView.setFitWidth(100);
//            imageView.setFitHeight(100);
//
//            Label username = new Label(suggestions.get(i).getUsername());
//
//            Button connect = new Button("Connect");
//
//            connect.setOnAction(event -> {
//                String username_lbl = ((Control) event.getSource()).getId();
//                Graph.getGraph().addEdge(AccountController.getAccountController().getCurrentAccount().getUsername(),username_lbl);
//            });
//
//            vBox.getChildren().add(imageView);
//            vBox.getChildren().add(username);
//            vBox.getChildren().add(connect);
//
//            suggestionGrid.add(vBox , i , 0);
//        }


        prof1.setImage(new Image(suggestions.get(0).getProfilePicture()));
        prof1.setFitHeight(80);
        prof1.setFitWidth(75);

        prof2.setImage(new Image(suggestions.get(1).getProfilePicture()));
        prof2.setFitHeight(50);
        prof2.setFitWidth(50);

        prof3.setImage(new Image(suggestions.get(2).getProfilePicture()));
        prof3.setFitHeight(50);
        prof3.setFitWidth(50);

        prof4.setImage(new Image(suggestions.get(3).getProfilePicture()));
        prof4.setFitHeight(50);
        prof4.setFitWidth(50);

        prof5.setImage(new Image(suggestions.get(4).getProfilePicture()));
        prof5.setFitHeight(50);
        prof5.setFitWidth(50);

        prof6.setImage(new Image(suggestions.get(5).getProfilePicture()));
        prof6.setFitHeight(50);
        prof6.setFitWidth(50);

        name1.setText(suggestions.get(0).getUsername());
        name2.setText(suggestions.get(1).getUsername());
        name3.setText(suggestions.get(2).getUsername());
        name4.setText(suggestions.get(3).getUsername());
        name5.setText(suggestions.get(4).getUsername());
        name6.setText(suggestions.get(5).getUsername());


        btn1.setId(suggestions.get(0).getUsername());
        btn2.setId(suggestions.get(1).getUsername());
        btn3.setId(suggestions.get(2).getUsername());
        btn4.setId(suggestions.get(3).getUsername());
        btn5.setId(suggestions.get(4).getUsername());
        btn6.setId(suggestions.get(5).getUsername());
//

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
