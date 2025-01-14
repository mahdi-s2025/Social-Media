package org.example.socialmedia.Controller;

import org.example.socialmedia.Database.Database;
import org.example.socialmedia.Exceptions.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class AccountController {

    private Database database = Database.getDatabase();



    public void signup(String name , String username , String password) throws SQLException {

        if (name.isEmpty()){
            throw new EmptyFieldException("Name Can Not Be Empty");
        }
        else if (username.isEmpty()){
            throw new EmptyFieldException("Username Can Not Be Empty");
        }
        else if (password.isEmpty()){
            throw new EmptyFieldException("Password Can Not Be Empty");
        }
        else {
            ResultSet usernameCheck = database.checkUsernameExist(username);
            if (usernameCheck.next()){
                throw new UsernameExistException();
            }
            else if(!(Pattern.matches("(?=[a-zA-Z0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*[a-zA-Z])(?=.*\\d).{8,}$" , password))){
                throw new InvalidFormatException("Your Password is weak . Enter a stronger Password");
            }
            else {
                database.addNewAccount(name , username , password);
                System.out.println("Signup :)");
            }
        }
    }

    public void login(String username , String password) throws SQLException {

        if (username.isEmpty()){
            throw new EmptyFieldException("Username Can Not Be Empty");
        }
        else if (password.isEmpty()){
            throw new EmptyFieldException("Password Can Not Be Empty");
        }
        else {
            ResultSet userPassword = database.getUserPassword(username);
            boolean user = userPassword.next();

            if (!user) {
                throw new UserNotFoundException();
            } else if (!password.equals(userPassword.getString("password"))) {
                throw new WrongPasswordException();
            } else {
                System.out.println("Login :)");
            }
        }
    }
}
