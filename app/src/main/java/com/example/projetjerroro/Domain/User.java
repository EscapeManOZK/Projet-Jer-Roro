package com.example.projetjerroro.Domain;

import java.util.ArrayList;
import java.util.List;

public class User {
    private int ID;
    private String Login;
    private String Password;
    private String Email;
    private String ImageUrl;
    private String FirstName;
    private String LastName;
    private List<Role> Roles;

    public User() {
        this.Roles = new ArrayList<>();
    }

    public User(int ID, String Login, String Password, String Email, String ImageUrl, String FirstName, String LastName, List<Role> Roles) {
        this.ID = ID;
        this.Login = Login;
        this.Password = Password;
        this.Email= Email;
        this.ImageUrl = ImageUrl;
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.Roles = Roles;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public List<Role> getRoles() {
        return Roles;
    }

    public void setRoles(List<Role> roles) {
        Roles = roles;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
