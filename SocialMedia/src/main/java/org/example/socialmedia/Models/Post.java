package org.example.socialmedia.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Setter
@Getter
public class Post {

    private static int idNumber = 1;
    private int ID;
    private int likeCounts;
    private String file;
    private String description;
    private String subject;
    private String dateAndTime;
    private ArrayList<Comment> comments;

    public Post(String file , String description){
        this.file = file;
        this.description = description;
        comments=new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        dateAndTime= now.format(formatter);
    }
}
