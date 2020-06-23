package com.example.projetjerroro.Service.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.R;
import com.example.projetjerroro.Service.Result;
import com.example.projetjerroro.Service.UtilsService;
import com.example.projetjerroro.ui.projectPage.ProjectItemAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

// doc : https://developer.android.com/reference/android/os/AsyncTask.html
// https://stackoverflow.com/questions/6343166/how-to-fix-android-os-networkonmainthreadexception

public class UserLoginServiceAsync extends AsyncTask<String, Void, Result<User>> {

    private Exception exception;
    private String TAG = "ProjectServiceAsync.java";
    private String url;


    public UserLoginServiceAsync(String fileurl) {
        this.url = fileurl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Result<User> doInBackground(String... strings) {
        User user = new User();
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
                    JSONObject jsonObject = new JSONObject(line);
                    user = UtilsService.getUserFromJson(jsonObject);
                }
            }return new Result.Success<>(user);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        } finally {
            // Close Stream and disconnect HTTPS connection.

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute( Result<User> user) {

        super.onPostExecute(user);

    }
}
