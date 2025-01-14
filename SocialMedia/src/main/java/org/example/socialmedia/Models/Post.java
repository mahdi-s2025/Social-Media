package org.example.socialmedia.Models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Post {

    private static int idNumber = 1;
    private int ID;
    private String file;
    private String description;

    public Post(String file , String description){
        this.file = file;
        this.description = description;
    }
}
