package com.example.projetjerroro.ui.home;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class HomeResult {
    @Nullable
    private HomeView success;
    @Nullable
    private Integer error;

    HomeResult(@Nullable Integer error) {
        this.error = error;
    }

    HomeResult(@Nullable HomeView success) {
        this.success = success;
    }

    @Nullable
    HomeView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}