package org.example.socialmedia.Exceptions;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(){
        super("Password Is Wrong");
    }

    public WrongPasswordException(String message){
        super(message);
    }
}
