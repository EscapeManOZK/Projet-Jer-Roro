package fr.esnl.projetjerroro.ui.projectPage;

import android.os.Build;
import android.os.Bundle;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.ui.utils.Global;

import com.google.android.material.tabs.TabLayout;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

import fr.esnl.projetjerroro.ui.projectPage.ui.main.SectionsPagerAdapter;

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