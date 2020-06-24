package fr.esnl.projetjerroro.ui.projectPage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.Domain.User;

import fr.esnl.projetjerroro.R;

import fr.esnl.projetjerroro.ui.utils.Global;

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
                if (s == null) {
                    projects = new ArrayList<>();
                } else {
                    projects = s;
                }
                listView.setAdapter(new ProjectItemAdapter(getContext(), projects));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                Project selectedItem = (Project) parent.getItemAtPosition(position);
                Intent myIntent = new Intent(getActivity(), DetailProject.class);
                myIntent.putExtra("Project", selectedItem); //Optional parameters
                getActivity().startActivityForResult(myIntent, 0);
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