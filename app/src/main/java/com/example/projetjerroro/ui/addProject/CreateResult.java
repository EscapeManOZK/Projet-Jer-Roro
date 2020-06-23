package com.example.projetjerroro.ui.addProject;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class CreateResult {
    @Nullable
    private CreateView success;
    @Nullable
    private Integer error;

    CreateResult(@Nullable Integer error) {
        this.error = error;
    }

    CreateResult(@Nullable CreateView success) {
        this.success = success;
    }

    @Nullable
    CreateView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}