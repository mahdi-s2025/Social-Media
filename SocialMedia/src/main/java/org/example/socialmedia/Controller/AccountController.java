package org.example.socialmedia.Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.example.socialmedia.HelloApplication;
import org.example.socialmedia.Models.Account;
import org.example.socialmedia.Models.Graph;

import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class AccountController {
    private final DataCenterController data = DataCenterController.getDataCenterController();


    private Account currentAccount;

    private static AccountController accountController;

    private AccountController() {}

    public static AccountController getAccountController(){
        if (accountController == null){
            accountController = new AccountController();
        }
        return accountController;
    }



    public void signup(String name , String username , String email , String password , String profilePhoto) throws Exception {

        currentAccount = new Account(name, username, email , password , profilePhoto);
        data.addUser(currentAccount);
        Graph.getGraph().addVertex(username);
        System.out.println("Signup :)");
    }



    public void login(String username , String password) throws Exception {
        Account tmp = data.findByUsername(username);
        if (tmp == null || !tmp.getPassword().equals(password)) {
            throw new Exception("Username or password is invalid!");
        }
        currentAccount = tmp;
    }

    public void login(String email) {
        currentAccount = data.findByEmail(email);
    }
    

    public static int passwordStrength(String password) {
        int strength = 0;

        Pattern smallLetterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[a-z]))[\\w\\d!@#$%^&*()]*$");
        Matcher smallLetterMatcher = smallLetterPattern.matcher(password);
        if (smallLetterMatcher.matches()) strength++;

        Pattern numberPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[0-9]))[\\w\\d!@#$%^&*()]*$");
        Matcher numberMatcher = numberPattern.matcher(password);
        if (numberMatcher.matches()) strength++;

        Pattern capitalLetterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[A-Z]))[\\w\\d!@#$%^&*()]*$");
        Matcher capitalLetterMatcher = capitalLetterPattern.matcher(password);
        if (capitalLetterMatcher.matches()) strength++;

        Pattern characterPattern = Pattern.compile("^(?=([\\w\\d!@#$%^&*()]*[!@#$%^&*()]))[\\w\\d!@#$%^&*()]*$");
        Matcher characterMatcher = characterPattern.matcher(password);
        if (characterMatcher.matches()) strength++;

        return strength;
    }


    public void editAccount(String name , String email , String password , String profilePicture){
        currentAccount.setName(name);
        currentAccount.setEmail(email);
        currentAccount.setPassword(password);
        currentAccount.setProfilePicture(profilePicture);
    }

    public void deleteAccount(){
        DataCenterController.getDataCenterController().deleteUser(currentAccount);
    }


    public String generateVerificationCode(){
        DataCenterController ds = DataCenterController.getDataCenterController();
        StringBuilder sb = new StringBuilder();

        Random random = new Random();

        do {
            for (int i = 0; i < 5; i++) {
                sb.append(random.nextInt(9));
            }
        }
        while (ds.getEmailValidCode().containsValue(sb.toString()));



        return sb.toString();
    }


    public static void showAlert(String msg,Alert.AlertType alertType,String header){
        Alert alert = new Alert(alertType);
        alert.setHeaderText(header);

        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void setScene(String page, String title) throws IOException {
        Stage currentStage = HelloApplication.getStage();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(page));
        Scene scene = new Scene(fxmlLoader.load());
        currentStage.setTitle(title);
        currentStage.setScene(scene);
        //currentStage.show();
    }
}

