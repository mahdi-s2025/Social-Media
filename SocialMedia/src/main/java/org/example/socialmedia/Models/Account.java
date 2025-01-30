package org.example.socialmedia.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Account {
    private int ID;
    private String name;
    private String username;
    private String email;
    private String password;
    private String profilePicture;
    private ArrayList<Post> posts;

    public Account(String name , String username , String email , String password , String profilePicture){
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.posts = new ArrayList<>();
        this.profilePicture=profilePicture;
    }
}
