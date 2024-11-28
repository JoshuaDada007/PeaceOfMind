package org.example.peaceofmind;

import jakarta.persistence.*;

import java.awt.image.CropImageFilter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String token;
    private String email;
    //creates a separate table for the favquote in the db and associating the value in the list with a primary key
    @ElementCollection
    private List<String> favQuotes = new ArrayList<>();
@ElementCollection
private List<String> myAlert = new ArrayList<>();
    public User() {
    }

    public User(String username, String token, String email, List<String> alert) {
        this.username = username;
        this.token = token;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> addFavQuotes() {
        return favQuotes;
    }

    public List<String> myAlert(){
        return myAlert;
    }



}
