package org.example.socialmedia.Models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

@Setter
@Getter
public class Post {
    private Account poster;
    private static int idNumber = 1;
    private int ID;
    private Set<String> likes;
    private String file;
    private String description;
    private String subject;
    private String dateAndTime;
    private ArrayList<Comment> comments;

    public Post(Account poster,String file ,String subject, String description){
        this.file = file;
        this.poster=poster;
        this.subject = subject;
        this.description = description;
        comments=new ArrayList<>();
        likes = new TreeSet<>();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        dateAndTime= now.format(formatter);
    }
}
