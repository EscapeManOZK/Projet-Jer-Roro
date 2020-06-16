package com.example.projetjerroro.ui.utils;

import android.app.Application;

import com.example.projetjerroro.Domain.User;

public class Global extends Application {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
