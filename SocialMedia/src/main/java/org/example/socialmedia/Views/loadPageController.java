package org.example.socialmedia.Views;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.socialmedia.HelloApplication;


import java.net.URL;
import java.util.ResourceBundle;

public class loadPageController implements Initializable {

    private final Stage stage = HelloApplication.getStage();

    @FXML
    private Label log_lbl;

    public synchronized void load() {
        try {
            Platform.runLater(() -> log_lbl.setText("connect: https://yougeram.com/maps"));
            Thread.sleep(1000);

            Platform.runLater(() -> log_lbl.setText("checking proxy..."));
            Thread.sleep(2000);

            Platform.runLater(() -> log_lbl.setText("connect: https://yougeram.com/welcome"));
            Thread.sleep(1000);


            Platform.runLater(() -> {
                try {
                    stage.close();
                    Thread.sleep(500);
                    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("welcomePage.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            });



        } catch (Exception e) {
            e.printStackTrace(System.out);
        }


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(() -> {
            try {
                load();
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }

        }).start();

    }

}
