package org.example.socialmedia.Controller;

import lombok.Getter;
import org.example.socialmedia.Models.Account;

import java.util.ArrayList;

/**
 * Hi there! This is {@code Mahdi} commenting.
 * This class stores all users and handles all operations on them.
 * This is {@code NOT} the best implementation, just for test.
 * Each method might implement in the better way.
 * Before any changing in this class, {@code PLEASE} keep me posted via Telegram.
 */

public class DataCenterController {
    @Getter
    private static final DataCenterController instance = new DataCenterController(); // should change with singleton implementation.
    @Getter
    private final ArrayList<Account> users = new ArrayList<>();

    public Account getUser(String username) {
        for (Account user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
