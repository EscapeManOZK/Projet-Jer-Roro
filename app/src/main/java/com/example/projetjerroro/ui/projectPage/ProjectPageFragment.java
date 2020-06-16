package com.example.projetjerroro.ui.projectPage;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.R;
import com.example.projetjerroro.Service.ProjectService;
import com.example.projetjerroro.ui.home.HomeViewModel;
import com.example.projetjerroro.ui.utils.Global;

import java.util.ArrayList;
import java.util.List;

public class ProjectPageFragment extends Fragment {

    private User currentUser;

    private List<Project> projects = new ArrayList<>();

    private ListView listView;

    private ProjectPageViewModel projectPageViewModel;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.projectPageViewModel =
                ViewModelProviders.of(this).get(ProjectPageViewModel.class);

        View root = inflater.inflate(R.layout.fragment_project_page, container, false);

        listView = root.findViewById(R.id.TabProjects);

        this.projectPageViewModel.getProjects().observe(getViewLifecycleOwner(), new Observer<List<Project>>() {
            @Override
            public void onChanged(@Nullable List<Project> s) {
                projects = s;
                listView.setAdapter(new ProjectItemAdapter(getContext(), projects));
            }
        });

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {

        super.onResume();

        this.currentUser =  ((Global) this.getActivity().getApplication()).getCurrentUser();

        this.projectPageViewModel.setUser(this.currentUser);
        this.projectPageViewModel.setContext(getContext());
        this.projectPageViewModel.getProjetData();

    }
}