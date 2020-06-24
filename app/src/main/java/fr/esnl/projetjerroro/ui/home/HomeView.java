package fr.esnl.projetjerroro.ui.home;

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;

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