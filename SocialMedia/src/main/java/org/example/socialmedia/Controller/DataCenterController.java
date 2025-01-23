package org.example.socialmedia.Controller;

import lombok.Getter;
import lombok.Setter;
import org.example.socialmedia.Models.Account;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Hi there! This is {@code Mahdi} commenting.
 * This class stores all users and handles all operations on them.
 * This is {@code NOT} the best implementation, just for test.
 * Each method might implement in the better way.
 * Before any changing in this class, {@code PLEASE} keep me posted via Telegram.
 */

@Setter
@Getter
public class DataCenterController {
    private static DataCenterController dataCenterController;
    private Map<String , Account> users;
    private Map<String , String> usersMail;


    private DataCenterController(){
        users = new HashMap<>();
        usersMail = new HashMap<>();
    }

    public static DataCenterController getDataCenterController() {
        if (dataCenterController == null){
            dataCenterController = new DataCenterController();
        }
        return dataCenterController;
    }

    public void addUser(Account user){
        users.put(user.getUsername() , user);
        usersMail.put(user.getEmail() , user.getUsername());
    }

    public void deleteUser(Account user){
        usersMail.remove(user.getEmail());
        users.remove(user.getUsername());
    }
    public Account findByUsername(String username){
        return users.get(username);
    }

    public Account findByEmail(String email){
        String username = usersMail.get(email);
        return users.get(username);
    }
}
