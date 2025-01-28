//    @FXML
//    void signupClick(ActionEvent event) throws Exception {
//
//        String name = name_txt.getText();
//        String username = username_txt.getText();
//        String email = "";
//        String password = password_txt.getText();
//
//        AccountController ac = AccountController.getAccountController();
//        boolean signup = ac.signup(name , username , email , password , file);
//
//        if (signup) {
//            AccountController.setStage("homePage.fxml");
//        }
//    }
//
//    @FXML
//    void loginClick(ActionEvent event) throws IOException {
//        Stage stage = HelloApplication.getStage();
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
//        Scene scene = new Scene(fxmlLoader.load());
//        stage.setTitle("Login");
//        stage.setScene(scene);
//        stage.show();
//    }

package org.example.socialmedia.Views;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.Models.Account;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class signupPageController implements Initializable {

    private final DataCenterController data = DataCenterController.getDataCenterController();
    private final Image green = new Image("file:src/main/resources/org/example/pictures/greenCheck.png");
    private final Image red = new Image("file:src/main/resources/org/example/pictures/redError.jpg");

    @FXML
    private ImageView check1;

    @FXML
    private ImageView check2;

    @FXML
    private ImageView check3;

    @FXML
    private Label error_lbl;

    @FXML
    private TextField name_txt;

    @FXML
    private Button next_btn;

    @FXML
    private PasswordField password_txt;

    @FXML
    private AnchorPane root;

    @FXML
    private TextField username_txt;

    @FXML
    private Circle profPhoto;

    @FXML
    void nextClick(ActionEvent event) {

    }

    @FXML
    void onChooseImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select an Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.bmp", "*.gif")
        );


        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            String file = selectedFile.toURI().toString();
            Image image = new Image(file);
            ImagePattern imagePattern = new ImagePattern(image);
            profPhoto.setFill(new ImagePattern(image));
//            profPhoto.setImage(image);
//            profPhoto.setFitWidth(400);
//            profPhoto.setPreserveRatio(true);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        profPhoto.setStroke(Color.SEAGREEN);
        Image image = new Image("file:src/main/resources/org/example/pictures/account.png");
        profPhoto.setFill(new ImagePattern(image));
        profPhoto.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));

        root.setOnMouseClicked(event -> {
            if (username_txt.isFocused() || password_txt.isFocused() || name_txt.isFocused()) {
                root.requestFocus();
            }
        });


        UnaryOperator<TextFormatter.Change> usernameFilter = getUsernameFilter();
        TextFormatter<String> formatter = new TextFormatter<>(usernameFilter);
        username_txt.setTextFormatter(formatter);

        UnaryOperator<TextFormatter.Change> passwordFilter = getPasswordFilter();
        TextFormatter<String> formatter2 = new TextFormatter<>(passwordFilter);
        password_txt.setTextFormatter(formatter2);

        UnaryOperator<TextFormatter.Change> nameFilter = getNameFilter();
        TextFormatter<String> formatter3 = new TextFormatter<>(nameFilter);
        name_txt.setTextFormatter(formatter3);



        BooleanBinding fieldsEmpty = check1.imageProperty().isEqualTo(red)
                .or(check2.imageProperty().isEqualTo(red).or(check3.imageProperty().isEqualTo(red)));

        next_btn.disableProperty().bind(fieldsEmpty);



    }

    private UnaryOperator<TextFormatter.Change> getUsernameFilter() {

        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z0-9_]+") || text.isEmpty()) {

                Account tmp = data.findByUsername(text);

                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check2.setImage(red);
                }

                else if (tmp != null) {
                    check2.setImage(red);
                    error_lbl.setText("Username already exists!");
                }


                else if (text.length() < 3) {
                    error_lbl.setText("Username cannot be less than 3 characters.");
                    check2.setImage(red);
                }

                else {
                    check2.setImage(green);
                    error_lbl.setText("");
                }

                return change;
            }
            error_lbl.setText("You can use only a-z, A-Z,\n0-9 and '_' in username.");

            return null;
        };
    }

    private UnaryOperator<TextFormatter.Change> getPasswordFilter() {
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z0-9_!@#$%^&*()]+") || text.isEmpty()) {


                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check3.setImage(red);
                }

                else if (text.length() < 8) {
                    error_lbl.setText("Password cannot be less than 8 characters.");
                    check3.setImage(red);
                }

                else if (AccountController.passwordStrength(text) < 4) {
                    error_lbl.setText("The password is weak. Its strength is " + AccountController.passwordStrength(text)
                            + " out of 4.\n You must use 0-9, a-z, A-Z, and at least\n" +
                            "one special character: ! @ # % ^ & * ( )");

                    check3.setImage(red);
                }


                else {
                    check3.setImage(green);
                    error_lbl.setText("");
                }

                return change;
            }
            error_lbl.setText("You can use only a-z, A-Z, 0-9 and\n" +
                    "special character: ! @ # % ^ & * ( ) in password.");

            return null;
        };
    }


    private UnaryOperator<TextFormatter.Change> getNameFilter() {
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z ]+") || text.isEmpty()) {


                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check1.setImage(red);
                }

                else if (text.length() < 3) {
                    error_lbl.setText("Name cannot be less than 3 characters.");
                    check1.setImage(red);
                }

                else {
                    check1.setImage(green);
                    error_lbl.setText("");
                }

                return change;
            }
            error_lbl.setText("You can use only a-z, A-Z,\n and space in name.");

            return null;
        };
    }
}

