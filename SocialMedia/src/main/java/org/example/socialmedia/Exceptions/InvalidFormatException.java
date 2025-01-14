package org.example.socialmedia.Exceptions;

public class InvalidFormatException extends RuntimeException{

    public InvalidFormatException(){
        super("Invalid Format");
    }

    public InvalidFormatException(String message){
        super(message);
    }
}
