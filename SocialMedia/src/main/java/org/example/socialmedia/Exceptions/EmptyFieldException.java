package org.example.socialmedia.Exceptions;

public class EmptyFieldException extends RuntimeException{

    public EmptyFieldException(){
        super("Field Can Not Be Empty");
    }
    public EmptyFieldException(String message){
        super(message);
    }
}
