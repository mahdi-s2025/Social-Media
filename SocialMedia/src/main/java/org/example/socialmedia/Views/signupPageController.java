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
//            AccountController.setScene("homePage.fxml");
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

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.SendMail;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class signupPageController implements Initializable {

    private final DataCenterController data = DataCenterController.getDataCenterController();
    private final Image green = new Image("file:src/main/resources/org/example/pictures/greenCheck.png");
    private final Image red = new Image("file:src/main/resources/org/example/pictures/redError.jpg");


    @FXML
    private ImageView back_btn;

    @FXML
    private ImageView check1;

    @FXML
    private ImageView check2;

    @FXML
    private ImageView check3;

    @FXML
    private VBox form1;

    @FXML
    private VBox form2;

    @FXML
    private Button send_btn;

    @FXML
    private Button signup_btn;

    @FXML
    private ImageView check4;

    @FXML
    private ImageView check5;

    @FXML
    private TextField code_txt;

    @FXML
    private TextField email_txt;

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

    private BooleanBinding fieldsEmpty2;


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
            if (username_txt.isFocused() || password_txt.isFocused() || name_txt.isFocused()
            || email_txt.isFocused() || code_txt.isFocused()) {
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

        UnaryOperator<TextFormatter.Change> emailFilter = getEmailFilter();
        TextFormatter<String> formatter4 = new TextFormatter<>(emailFilter);
        email_txt.setTextFormatter(formatter4);

        UnaryOperator<TextFormatter.Change> codeFilter = getCodeFilter();
        TextFormatter<String> formatter5 = new TextFormatter<>(codeFilter);
        code_txt.setTextFormatter(formatter5);



        BooleanBinding fieldsEmpty = check1.imageProperty().isEqualTo(red)
                .or(check2.imageProperty().isEqualTo(red).or(check3.imageProperty().isEqualTo(red)));

        next_btn.disableProperty().bind(fieldsEmpty);

        fieldsEmpty2 = check4.imageProperty().isEqualTo(red);
        send_btn.disableProperty().bind(fieldsEmpty2);

        BooleanBinding fieldsEmpty3 = check4.imageProperty().isEqualTo(red).
                or(check5.imageProperty().isEqualTo(red));
        signup_btn.disableProperty().bind(fieldsEmpty3);



    }

    private UnaryOperator<TextFormatter.Change> getUsernameFilter() {

        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z0-9_]+") || text.isEmpty()) {



                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check2.setImage(red);
                }


                else if (text.length() < 3) {
                    error_lbl.setText("Username cannot be less than 3 characters.");
                    check2.setImage(red);
                }

                else {
                    Account tmp = data.findByUsername(text);
                    if (tmp != null) {
                        check2.setImage(red);
                        error_lbl.setText("Username already exists!");
                    }
                    else {
                        check2.setImage(green);
                        error_lbl.setText("");
                    }

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

    private UnaryOperator<TextFormatter.Change> getEmailFilter() {
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z0-9.@_%+-]+$") || text.isEmpty()) {

                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check4.setImage(red);
                }

                else if (!text.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    error_lbl.setText("E-mail is invalid.");
                    check4.setImage(red);
                }

                else {
                    Account tmp = data.findByEmail(text);
                    if (tmp != null) {
                        check4.setImage(red);
                        error_lbl.setText("User with this e-mail already exists!");
                    }
                    else {
                        check4.setImage(green);
                        error_lbl.setText("");
                    }
                }

                return change;
            }
            error_lbl.setText("You cannot use invalid characters in e-mail.");

            return null;
        };
    }

    private UnaryOperator<TextFormatter.Change> getCodeFilter() {
        DataCenterController ds = DataCenterController.getDataCenterController();
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^\\d{1,5}$") || text.isEmpty()) {

                if(!send_btn.isDisable()) {
                    error_lbl.setText("");
                    check5.setImage(red);
                }

                else if (text.isEmpty()) {
                    error_lbl.setText("");
                    check5.setImage(red);
                }



                else if (text.length() < 5) {
                    error_lbl.setText("");
                    check5.setImage(red);
                }

                else if (!ds.getEmailValidCode().get(email_txt.getText()).equals(text)){
                    error_lbl.setText("");
                    check5.setImage(red);
                }
                else {
                    error_lbl.setText("");
                    check5.setImage(green);

                }

                return change;
            }
            return null;
        };
    }

    private void switchForm(boolean forward) {
        TranslateTransition firstAnim = new TranslateTransition(Duration.seconds(0.5), form1);
        TranslateTransition secondAnim = new TranslateTransition(Duration.seconds(0.5), form2);

        if (forward) {
            firstAnim.setToX(-430);
            secondAnim.setToX(-430);
            //nextButton.setVisible(false);
            back_btn.setVisible(true);
        } else {
            firstAnim.setToX(0);
            secondAnim.setToX(0);
            //nextButton.setVisible(true);
            back_btn.setVisible(false);
        }

        firstAnim.play();
        secondAnim.play();
    }

    @FXML
    void backClick(MouseEvent event) {
        switchForm(false);
    }

    @FXML
    void sendClick(ActionEvent event) throws Exception {

        DataCenterController ds = DataCenterController.getDataCenterController();

        String code = AccountController.getAccountController().generateVerificationCode();


        ds.getEmailValidCode().put(email_txt.getText() , code);

        SendMail sendMail = new SendMail();

        sendMail.send("subject" , "verification code : " + code , email_txt.getText());

        email_txt.setEditable(false);
        send_btn.disableProperty().unbind();
        send_btn.setDisable(true);
        new Thread(() -> {
            try {
                disable();
                ds.getEmailValidCode().remove(email_txt.getText());
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }).start();
    }

    private synchronized void disable() {
        try {
            for (int i = 59; i >= 0; i--) {
                int finalI = i;
                Platform.runLater(() -> send_btn.setText(String.valueOf(finalI)));
                Thread.sleep(1000);
            }
            Thread.sleep(2000);
            Platform.runLater(() -> {
                send_btn.setText("SEND");
                send_btn.disableProperty().bind(fieldsEmpty2);
                email_txt.setEditable(true);
            });


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @FXML
    void signupClick(ActionEvent event) {

    }

    @FXML
    void nextClick(ActionEvent event) {
        switchForm(true);
    }
}

