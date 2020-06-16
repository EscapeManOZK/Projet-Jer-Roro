package com.example.projetjerroro.Service;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.Service.Async.ProjectServiceAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProjectService {
    public static String PROJECT_URL_RESSOURCE = "https://site-de-romain-owczarek.fr/CB_WEBSERVICE/Project/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static List<Project> getAll(User user,Context ctx) {
        List<Project> projects = new ArrayList<>();
        try {
            projects = new ProjectServiceAsync(PROJECT_URL_RESSOURCE + "ALL.php", ctx).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return projects;
    }
}
