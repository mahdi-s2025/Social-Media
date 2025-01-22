package org.example.socialmedia.Views;

import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.example.socialmedia.Controller.AccountController;
import org.example.socialmedia.Controller.DataCenterController;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class homePageController implements Initializable {

    @FXML
    private Button id1;

    @FXML
    private Button id2;

    @FXML
    private Button id3;

    @FXML
    private Label lb1;

    @FXML
    private Label lb2;

    @FXML
    private Label lb3;

    @FXML
    private Button logout_btn;

    @FXML
    private Label name_lbl;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name_lbl.setText(AccountController.getCurrentAccount().getName());
        lb1.setText(DataCenterController.getInstance().getUsers().get(0).getUsername());
        lb2.setText(DataCenterController.getInstance().getUsers().get(1).getUsername());
        lb3.setText(DataCenterController.getInstance().getUsers().get(2).getUsername());
    }


    @FXML
    void logoutClick(ActionEvent event) throws IOException {
        Stage stage = HelloApplication.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void connect(){
        Graph.getGraph().addEdge(AccountController.getCurrentAccount().getUsername(),lb1.getText());
        Graph.getGraph().setProbability();
    }

}
