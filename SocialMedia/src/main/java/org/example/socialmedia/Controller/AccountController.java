package org.example.socialmedia.Controller;

import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;
import org.example.socialmedia.Exceptions.*;
import org.example.socialmedia.Models.Account;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class AccountController {
    DataCenterController data = DataCenterController.getDataCenterController();

    private Account currentAccount;

    private static AccountController accountController;

    private AccountController(){}

    public static AccountController getAccountController(){
        if (accountController == null){
            accountController = new AccountController();
        }
        return accountController;
    }
    public void signup(String name , String username , String email , String password) throws Exception {

        if (name.isEmpty()){
            showAlert("Name Can Not Be Empty");
            //throw new EmptyFieldException("Name Can Not Be Empty");
        }
        else if (username.isEmpty()){
            showAlert("Username Can Not Be Empty");
            //throw new EmptyFieldException("Username Can Not Be Empty");
        }
        else if (password.isEmpty()){
            showAlert("Password Can Not Be Empty");
            //throw new EmptyFieldException("Password Can Not Be Empty");
        }
        else {
            //checkUsername(username);
            //checkPassword(password);
            currentAccount = new Account(name, username, email , password);
            data.addUser(currentAccount);
            System.out.println("Signup :)");
        }
    }

    public void login(String username , String password) throws Exception {

        if (username.isEmpty()) {
            showAlert("Username Can Not Be Empty");
            //throw new EmptyFieldException("Username Can Not Be Empty");
        } else if (password.isEmpty()) {
            showAlert("Password Can Not Be Empty");
            //throw new EmptyFieldException("Password Can Not Be Empty");
        } else {
            Account tmp = data.findByUsername(username);
            if (tmp == null || !tmp.getPassword().equals(password))
                showAlert("Username or password is invalid!");
                //throw new Exception("Username or password is invalid!");
            currentAccount = tmp;
            System.out.println("Login :)");
        }

    }


    /**
     * Hi there again! Or if you are visiting me for the first time, Hello! Also, this is {@code Mahdi} again.
     * These methods in blow, check the password strength and username validity.
     * Before any changing, {@code PLEASE} keep me on trace.
     * Love you!
     *
     *
     * @param username
     * @throws Exception
     */

    public void checkUsername(String username) throws Exception {
        if (data.findByUsername(username) != null)
            throw new Exception("Username already exists!");
        Pattern usernamePattern = Pattern.compile("^[A-Za-z1-9]+(_)?[A-Za-z1-9]+$");
        Matcher usernameMatcher = usernamePattern.matcher(username);
        if (!usernameMatcher.matches()) {
            throw new Exception("Username is invalid!");
        }
    }

    /**
     *
     * @param password
     * @throws Exception
     */

    public void checkPassword(String password) throws Exception {
        switch (passwordStrength(password)) {
            case 0 -> throw new Exception("The password is too short.\nIt must have more than 8 characters");
            case 1 -> throw new Exception("""
                    The password is weak. Its strength is 1 out of 4.\

                    You must use 0-9, a-z, A-Z, and at least one
                    special character: ! @ # % ^ & * ( )""");
            case 2 -> throw new Exception("""
                    The password is weak. Its strength is 2 out of 4.\

                    You must use 0-9, a-z, A-Z, and at least one
                    special character: ! @ # % ^ & * ( )""");
            case 3 -> throw new Exception("""
                    The password is weak. Its strength is 3 out of 4.\

                    You must use 0-9, a-z, A-Z, and at least one
                    special character: ! @ # % ^ & * ( )""");
            case 5 -> throw new Exception("""
                    The password is too long. \
                    It shouldn't have more than 30 characters""");
            case 6 -> throw new Exception("""
                    The password has invalid character. \
                    You must use 0-9, a-z, A-Z, and at least one special character: ! @ # % ^ & * ( )""");
            default -> {}
        }
    }

    private int passwordStrength(String password) {
        int strength = 0;
        if (password.length() < 8)
            return 0;

        if (password.length() > 30)
            return 5;

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

        if (strength == 0) return 6;
        return strength;
    }


    public void editAccount(String name , String email , String password , String bio , String profilePicture){
        currentAccount.setName(name);
        currentAccount.setEmail(email);
        currentAccount.setPassword(password);
        currentAccount.setBio(bio);
        currentAccount.setProfilePicture(profilePicture);
    }

    public void deleteAccount(){
        DataCenterController.getDataCenterController().deleteUser(currentAccount);
    }

    public void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ٌWarning");
        alert.setHeaderText("Error");
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
