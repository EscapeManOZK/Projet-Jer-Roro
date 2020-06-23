package com.example.projetjerroro;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.Service.Async.DownloadImageTask;
import com.example.projetjerroro.Service.UtilsService;
import com.example.projetjerroro.ui.login.LoginActivity;
import com.example.projetjerroro.ui.utils.Global;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    private User current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((Global) this.getApplication()).setCurrentUser(null);
        this.current_user = null;
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view_client);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_project_page, R.id.nav_add_project)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        openLogin(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void openLogin(MenuItem item) {
        ((Global) this.getApplication()).setCurrentUser(null);
        this.current_user = null;

        Intent myIntent = new Intent(MainActivity.this, LoginActivity.class);
        //myIntent.putExtra("key", value); //Optional parameters
        MainActivity.this.startActivityForResult(myIntent, 0);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        this.current_user = ((Global) this.getApplication()).getCurrentUser();
        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(this.current_user.getImageUrl());
        TextView name = (TextView) findViewById(R.id.nameUser);
        TextView mail = (TextView) findViewById(R.id.emailUser);
        name.setText(this.current_user.getDisplayName());
        mail.setText(this.current_user.getEmail());

        NavigationView navigationView = findViewById(R.id.nav_view_client);
        navigationView.getMenu().clear();
        if (UtilsService.containsName(current_user.getRoles(), "CLIENT")) {
            navigationView.inflateMenu(R.menu.activity_main_drawer);
        } else {
            navigationView.inflateMenu(R.menu.activity_main_drawer2);
        }
    }

}