package org.example.socialmedia.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter

public class Comment {
    private Account writer;
    private String content;
    private String dateAndTime;
    public Comment(Account writer,String content){
        this.writer=writer;
        this.content=content;
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        dateAndTime= now.format(formatter);
    }
}
