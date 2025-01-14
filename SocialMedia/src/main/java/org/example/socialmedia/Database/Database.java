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

}