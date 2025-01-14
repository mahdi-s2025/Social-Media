package org.example.socialmedia.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SQLConnection {
    private String URL = "jdbc:mysql://localhost/social_media";
    private String USERNAME = "root";
    private String PASSWORD = "root";

    public boolean SQLExecute(String SQLCommand){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL  , USERNAME , PASSWORD);
            Statement statement = connection.prepareStatement(SQLCommand);
            return statement.execute(SQLCommand);
        } catch (Exception e) {
            return false ;
        }

    }
    public ResultSet SQlExecuteQuery(String SQLCommand) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(URL  , USERNAME , PASSWORD);
            Statement statement = connection.prepareStatement(SQLCommand);
            return statement.executeQuery(SQLCommand);
        }
        catch (Exception e){
            return null ;
        }


    }

}
