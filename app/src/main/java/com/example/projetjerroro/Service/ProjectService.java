package com.example.projetjerroro.Service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.Role;
import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.Service.Async.ProjectCountServiceAsync;
import com.example.projetjerroro.Service.Async.ProjectCreateServiceAsync;
import com.example.projetjerroro.Service.Async.ProjectDeleteServiceAsync;
import com.example.projetjerroro.Service.Async.ProjectGetOneServiceAsync;
import com.example.projetjerroro.Service.Async.ProjectUpdateServiceAsync;
import com.example.projetjerroro.Service.Async.ProjectsServiceAsync;
import com.example.projetjerroro.Service.Async.UserLoginServiceAsync;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProjectService {
    public static String PROJECT_URL_RESSOURCE = "https://site-de-romain-owczarek.fr/CB_WEBSERVICE/Project/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Project> getAll(User user,Context ctx) {
        List<Project> projects = new ArrayList<>();
        try {
            if (UtilsService.containsName(user.getRoles(),"CLIENT")) {
                projects = new ProjectsServiceAsync(PROJECT_URL_RESSOURCE + "ALL.php?id=" + user.getID(), ctx).execute().get();
            } else {
                projects = new ProjectsServiceAsync(PROJECT_URL_RESSOURCE + "ALL.php", ctx).execute().get();
            }
            Collections.sort(projects);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public static Result<Project> create (Project project) {
        try {
            return new ProjectCreateServiceAsync(PROJECT_URL_RESSOURCE + "create.php?" + project.toParams()).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in create project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in create project", e));
        }
    }

    public static Result<String> delete(int id) {
        try {
            return new ProjectDeleteServiceAsync(PROJECT_URL_RESSOURCE + "delete.php?id=" + id).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in delete project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in delete project", e));
        }
    }

    public static Result<Project> update(Project project) {
        try {
            return new ProjectUpdateServiceAsync(PROJECT_URL_RESSOURCE + "update.php?" + project.toParams()).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in update project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in update project", e));
        }
    }

    public static Result<Project> valide(Project project) {
        try {
            return new ProjectUpdateServiceAsync(PROJECT_URL_RESSOURCE + "valide.php?" + project.toParamsToValid()).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in update project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in update project", e));
        }
    }

    public static Result<Project> getOne(int id) {
        try {
            return new ProjectGetOneServiceAsync(PROJECT_URL_RESSOURCE + "get.php?id=" + id).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in get project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in get project", e));
        }
    }

    public static Result<Map<ProjectStatusType, Integer>> count() {
        try {
            return new ProjectCountServiceAsync(PROJECT_URL_RESSOURCE + "count.php").execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error in count project", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error in count project", e));
        }
    }
}
