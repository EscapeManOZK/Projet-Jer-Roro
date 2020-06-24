package fr.esnl.projetjerroro.ui.utils;

import android.app.Application;

import fr.esnl.projetjerroro.Domain.User;

public class Global extends Application {

    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
