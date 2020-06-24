package fr.esnl.projetjerroro.Service;

import fr.esnl.projetjerroro.Domain.User;
import fr.esnl.projetjerroro.Service.Async.UserLoginServiceAsync;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

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