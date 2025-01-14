package org.example.socialmedia.Database;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Database {
    private static Database database ;
    private SQLConnection sqlConnection ;
    public static Database getDatabase() {
        if (database == null){
            database = new Database();
        }
        return database;
    }
    private Database(){
        this.sqlConnection = new SQLConnection();
    }

    public void addNewAccount(String name , String username , String password){
        String command = String.format("INSERT INTO accounts (name, username, password) VALUES ('%s', '%s', '%s');"  , name , username , password);
        sqlConnection.SQLExecute(command);
    }
    public ResultSet getUserPassword(String username){
        String command = String.format("SELECT password FROM accounts WHERE Username = '%s'" , username);
        return sqlConnection.SQlExecuteQuery(command);
    }

    public ResultSet checkUsernameExist(String username){
        String command = String.format("SELECT 1 FROM accounts WHERE username = '%s'" , username);
        return sqlConnection.SQlExecuteQuery(command);
    }
}