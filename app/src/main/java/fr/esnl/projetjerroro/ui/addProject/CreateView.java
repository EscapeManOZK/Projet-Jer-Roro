package fr.esnl.projetjerroro.ui.addProject;

import fr.esnl.projetjerroro.Domain.Project;

/**
 * Class exposing authenticated user details to the UI.
 */
class CreateView {
    private String displayName;
    private Project project;
    //... other data fields that may be accessible to the UI

    CreateView(String displayName, Project project) {
        this.displayName = displayName;
        this.project = project;
    }

    String getDisplayName() {
        return displayName;
    }

    public Project getProject() {
        return project;
    }
}