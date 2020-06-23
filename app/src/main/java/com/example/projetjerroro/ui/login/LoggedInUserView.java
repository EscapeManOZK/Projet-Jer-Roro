package com.example.projetjerroro.ui.login;

import com.example.projetjerroro.Domain.User;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView {
    private String displayName;
    private User user;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, User user) {
        this.displayName = displayName;
        this.user = user;
    }

    String getDisplayName() {
        return displayName;
    }

    public User getUser() {
        return user;
    }
}