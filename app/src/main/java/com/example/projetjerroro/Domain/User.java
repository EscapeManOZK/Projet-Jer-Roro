package com.example.projetjerroro.Domain;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class User implements Serializable {
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

    public User(String id, String Login) {
        this.ID = Integer.valueOf(id);
        this.Login = Login;
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

    public String getDisplayName() {
        return this.FirstName + " " + this.LastName.toUpperCase();
    }

    @Override
    public String toString() {
        return "{" +
                "ID=" + ID +
                ", Login='" + Login + '\'' +
                ", Password='" + Password + '\'' +
                ", Email='" + Email + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", FirstName='" + FirstName + '\'' +
                ", LastName='" + LastName + '\'' +
                ", Roles=" + Roles +
                '}';
    }

    public String toJSON() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ID", getID());
            jsonObject.put("Login", getLogin());
            jsonObject.put("Password", getPassword());
            jsonObject.put("Email", getEmail());
            jsonObject.put("ImageUrl", getImageUrl());
            jsonObject.put("FirstName", getFirstName());
            jsonObject.put("LastName", getLastName());
            jsonObject.put("Roles", getRoles());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
