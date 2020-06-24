package fr.esnl.projetjerroro.Service.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.UtilsService;

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

public class ProjectsServiceAsync extends AsyncTask<String, Void, List<Project>> {

    private Exception exception;
    private Context ctx;
    private String TAG = "ProjectServiceAsync.java";
    private String url;
    private ProgressDialog p;
    private ListView listView;
    private LayoutInflater inflater;
    private View view;


    public ProjectsServiceAsync(String fileurl, Context ctx) {
        this.url = fileurl;
        this.ctx = ctx;
        this.p = new ProgressDialog(ctx);
        this.inflater = LayoutInflater.from(ctx);
        this.view = inflater.inflate(R.layout.fragment_project_page, null);
        this.listView = this.view.findViewById(R.id.TabProjects);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        p.setMessage(ctx.getResources().getString(R.string.getProjectValue));
        p.setIndeterminate(false);
        p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        p.setCancelable(false);
        p.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected List<Project> doInBackground(String... strings) {
        List<Project> projects = new ArrayList<>();
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
                    JSONArray jsonArray = new JSONArray(line);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        projects.add(UtilsService.getProjectFromJson(obj));
                    }
                }
            }
        } catch (Exception e) {
            this.exception = e;

            return null;
        } finally {
            // Close Stream and disconnect HTTPS connection.

            if (connection != null) {
                connection.disconnect();
            }
        }
        return projects;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<Project> projects) {

        p.dismiss();
        if (projects != null) {
            // Do something awesome here
            System.out.println(projects.toString());
            Toast.makeText(ctx, ctx.getResources().getString(R.string.getProjectValueComplete), Toast.LENGTH_SHORT).show();
        } else {
            projects = new ArrayList<>();
            Toast.makeText(ctx, ctx.getResources().getString(R.string.getProjectValueFail), Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(projects);

    }
}
