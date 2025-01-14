package org.example.socialmedia.Exceptions;

public class UsernameExistException extends RuntimeException{

    public UsernameExistException(){
        super("Username Already exist");
    }
    public UsernameExistException(String message){
        super(message);
    }
}
