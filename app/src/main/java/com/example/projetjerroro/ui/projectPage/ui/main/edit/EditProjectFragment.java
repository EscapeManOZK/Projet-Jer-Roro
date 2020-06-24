package com.example.projetjerroro.ui.projectPage.ui.main.edit;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.Domain.Enum.StatusValidationType;
import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.R;
import com.example.projetjerroro.Service.ProjectService;
import com.example.projetjerroro.Service.Result;
import com.example.projetjerroro.Service.UtilsService;
import com.example.projetjerroro.ui.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A placeholder fragment containing a simple view.
 */
public class EditProjectFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_PROJECT = "section_project";
    private static final String ARG_SECTION_PROJECT_ID = "section_project_ID";


    private EditViewModel pageViewModel;

    private EditText title;

    private EditText description;

    private EditText amount;

    private Project project;


    public static EditProjectFragment newInstance(int index, Project project) {
        EditProjectFragment fragment = new EditProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        System.out.println(project.toJSON());
        bundle.putString(ARG_SECTION_PROJECT, project.toString());
        bundle.putInt(ARG_SECTION_PROJECT_ID, project.getID());
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(EditViewModel.class);
        int index = 1;
        Project project = new Project();
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            try {
                project = UtilsService.getProjectFromJson(new JSONObject(getArguments().getString(ARG_SECTION_PROJECT)));
                pageViewModel.setmProject(project);
            } catch (JSONException e) {
                int id = getArguments().getInt(ARG_SECTION_PROJECT_ID);
                Result<Project> result = ProjectService.getOne(id);
                if (result instanceof Result.Success) {
                    project = ((Result.Success<Project>) result).getData();
                    pageViewModel.setmProject(project);
                }
            }
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_edit_project, container, false);

        title = root.findViewById(R.id.title_edit);
        description = root.findViewById(R.id.description_edit);
        amount = root.findViewById(R.id.amount_edit);

        pageViewModel.getmProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project s) {
                project = s;
                title.setText(s.getTitle());
                description.setText(s.getDescription());
                amount.setText(s.getAmount()+"");
            }
        });




        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataIsOk()) {
                    project.setTitle(title.getText().toString());
                    project.setDescription(description.getText().toString());
                    project.setAmount(Integer.parseInt(amount.getText().toString()));
                    Result<Project> result = pageViewModel.update(project);
                    if (result instanceof Result.Success) {
                        updateUiWithUser();
                        getActivity().setResult(Activity.RESULT_OK);
                        //Complete and destroy login activity once successful
                        getActivity().finish();
                    }else {
                        showLoginFailed(((Result.Error) result).getError().getMessage());
                    }
                } else {
                    if (!hasValue(title.getText())) {
                        title.setError(getString(R.string.fieldRequired));
                    }

                    if (!hasValue(amount.getText())) {
                        amount.setError(getString(R.string.fieldRequired));
                    }
                }
            }
        });


        return root;
    }

    private boolean dataIsOk() {
        return hasValue(title.getText()) && hasValue(amount.getText());
    }

    private boolean hasValue(Editable text) {
        return  text != null && text.toString().length() > 0;
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.projectUpdate) + " (" + project.getTitle() +")";
        // TODO : initiate successful logged in experience
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}