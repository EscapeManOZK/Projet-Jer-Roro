package com.example.projetjerroro.Service;

import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.Service.Async.UserLoginServiceAsync;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public static String USER_URL_RESSOURCE = "https://site-de-romain-owczarek.fr/CB_WEBSERVICE/User/";


    public Result<User> login(String username, String password) {

        try {
            return new UserLoginServiceAsync(USER_URL_RESSOURCE + "Login.php?id=" + username + "&&mdp=" + password).execute().get();
        } catch (ExecutionException e) {
            return new Result.Error(new IOException("Error logging in", e));
        } catch (InterruptedException e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}