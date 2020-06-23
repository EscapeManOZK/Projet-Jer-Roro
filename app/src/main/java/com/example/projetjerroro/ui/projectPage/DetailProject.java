package com.example.projetjerroro.ui.projectPage;

import android.os.Build;
import android.os.Bundle;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.R;
import com.example.projetjerroro.ui.utils.Global;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.projetjerroro.ui.projectPage.ui.main.SectionsPagerAdapter;

public class DetailProject extends AppCompatActivity {

    Project project;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.project = (Project) getIntent().getSerializableExtra("Project");
        setContentView(R.layout.activity_detail_project);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, project,  ((Global) this.getApplication()).getCurrentUser(),getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        TextView textView = findViewById(R.id.title);
        textView.setText(getApplicationContext().getResources().getString(R.string.detail) + " "+ project.getTitle());
    }


}