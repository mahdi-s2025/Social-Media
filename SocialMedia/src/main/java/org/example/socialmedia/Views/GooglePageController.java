package org.example.socialmedia.Views;

import javafx.application.Platform;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.SendMail;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class GooglePageController implements Initializable {

    private final DataCenterController data = DataCenterController.getDataCenterController();
    private final Image green = new Image("file:src/main/resources/org/example/pictures/greenCheck.png");
    private final Image red = new Image("file:src/main/resources/org/example/pictures/redError.jpg");

    @FXML
    private ImageView check1;

    @FXML
    private ImageView check2;

    @FXML
    private TextField code_txt;

    @FXML
    private TextField email_txt;

    @FXML
    private Label error_lbl;

    @FXML
    private AnchorPane root;

    @FXML
    private Button send_btn;

    @FXML
    private Button login_btn;

    private BooleanBinding fieldsEmpty1;

    @FXML
    void sendClick(ActionEvent event) throws Exception {
        String code = AccountController.getAccountController().generateVerificationCode();
        data.getEmailValidCode().put(email_txt.getText() , code);

        SendMail sendMail = new SendMail();

        sendMail.send("verify" , "Welcome to Yougram\n\n" +
                "Your verification code : " + code , email_txt.getText());

        email_txt.setEditable(false);
        send_btn.disableProperty().unbind();
        send_btn.setDisable(true);

        new Thread(() -> {
            try {
                disable();
                data.getEmailValidCode().remove(email_txt.getText());
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
            Thread.sleep(1000);
            Platform.runLater(() -> {
                send_btn.setText("SEND");
                send_btn.disableProperty().bind(fieldsEmpty1);
                email_txt.setEditable(true);
                check2.setImage(red);
            });


        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    @FXML
    void loginClick(ActionEvent event) throws IOException {

        AccountController ac = AccountController.getAccountController();
        ac.login(email_txt.getText());

        loginPageController.getGoogleStage().close();
        AccountController.setScene("homePage.fxml", "Home");

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("googlePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        loginPageController.getGoogleStage().setScene(scene);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        root.setOnMouseClicked(event -> {
            if (email_txt.isFocused() || code_txt.isFocused()) {
                root.requestFocus();
            }
        });

        UnaryOperator<TextFormatter.Change> emailFilter = getEmailFilter();
        TextFormatter<String> formatter1 = new TextFormatter<>(emailFilter);
        email_txt.setTextFormatter(formatter1);

        UnaryOperator<TextFormatter.Change> codeFilter = getCodeFilter();
        TextFormatter<String> formatter2 = new TextFormatter<>(codeFilter);
        code_txt.setTextFormatter(formatter2);

        fieldsEmpty1 = check1.imageProperty().isEqualTo(red);
        send_btn.disableProperty().bind(fieldsEmpty1);

        BooleanBinding fieldsEmpty2 = check2.imageProperty().isEqualTo(red).
                or(check2.imageProperty().isEqualTo(red));
        login_btn.disableProperty().bind(fieldsEmpty2);
    }

    private UnaryOperator<TextFormatter.Change> getEmailFilter() {
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^[a-zA-Z0-9.@_%+-]+$") || text.isEmpty()) {

                if (text.isEmpty()) {
                    error_lbl.setText("");
                    check1.setImage(red);
                }

                else if (!text.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    error_lbl.setText("E-mail is invalid.");
                    check1.setImage(red);
                }

                else {
                    Account tmp = data.findByEmail(text);
                    if (tmp == null) {
                        check1.setImage(red);
                        error_lbl.setText("User with this e-mail not found!");
                    }
                    else {
                        check1.setImage(green);
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
        return change -> {

            String text = change.getControlNewText();

            if (text.matches("^\\d{1,5}$") || text.isEmpty()) {

                if(!send_btn.isDisable()) {
                    error_lbl.setText("");
                    check2.setImage(red);
                }

                else if (text.isEmpty()) {
                    error_lbl.setText("");
                    check2.setImage(red);
                }


                else if (text.length() < 5) {
                    error_lbl.setText("");
                    check2.setImage(red);
                }

                else if (data.getEmailValidCode().isEmpty() || !data.getEmailValidCode().get(email_txt.getText()).equals(text)){
                    error_lbl.setText("");
                    check2.setImage(red);
                }
                else {
                    error_lbl.setText("");
                    check2.setImage(green);

                }

                return change;
            }
            return null;
        };
    }

}
