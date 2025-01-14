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
    private String password;
    private String bio;
    private String profilePicture;
    private ArrayList<Account> followers;
    private ArrayList<Account> followings;
    private ArrayList<Post> posts;

    public Account(String name , String username , String password){
        this.name = name;
        this.username = username;
        this.password = password;
        this.followers = new ArrayList<>();
        this.followings = new ArrayList<>();
        this.posts = new ArrayList<>();
    }
}
