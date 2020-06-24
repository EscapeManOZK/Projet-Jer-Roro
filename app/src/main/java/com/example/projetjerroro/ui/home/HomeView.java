package com.example.projetjerroro.ui.home;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.Domain.Project;

import java.util.Map;

/**
 * Class exposing authenticated user details to the UI.
 */
class HomeView {
    private Map<ProjectStatusType, Integer> map;
    //... other data fields that may be accessible to the UI

    HomeView(Map<ProjectStatusType, Integer> map) {
        this.map = map;
    }

    public Map<ProjectStatusType, Integer> getProject() {
        return map;
    }
}