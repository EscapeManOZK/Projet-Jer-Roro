package fr.esnl.projetjerroro.Service.Async;

import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.Service.Result;
import fr.esnl.projetjerroro.Service.UtilsService;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ProjectUpdateServiceAsync extends AsyncTask<String, Void, Result<Project>> {

    private String url;

    public ProjectUpdateServiceAsync(String url) {
        this.url = url;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Result<Project> doInBackground(String... strings) {
        Project data = new Project();
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(this.url);
            connection = (HttpsURLConnection) url.openConnection();
            // Timeout for reading InputStream arbitrarily set to 3000ms.
            connection.setReadTimeout(3000);
            // Timeout for connection.connect() arbitrarily set to 3000ms.
            connection.setConnectTimeout(3000);
            // For this use case, set HTTP method to GET.
            connection.setRequestMethod("GET");
            // Already true by default but setting just in case; needs to be true since this request
            // is carrying an input (response) body.
            connection.setDoInput(true);
            // Open communications link (network traffic occurs here).
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            // Retrieve the response body as an InputStream.
            InputStream stream = connection.getInputStream();
            if (stream != null) {
                String line;
                BufferedReader r = new BufferedReader(new InputStreamReader(stream));
                while ((line = r.readLine()) != null) {
                    data = UtilsService.getProjectFromJson(new JSONObject(line));
                }
            }return new Result.Success<>(data);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error in update project", e));
        } finally {
            // Close Stream and disconnect HTTPS connection.

            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
