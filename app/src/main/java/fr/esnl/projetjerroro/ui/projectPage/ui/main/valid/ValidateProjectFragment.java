package fr.esnl.projetjerroro.ui.projectPage.ui.main.valid;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.esnl.projetjerroro.Domain.Enum.StatusValidationType;
import fr.esnl.projetjerroro.Domain.Project;

import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.ProjectService;
import fr.esnl.projetjerroro.Service.Result;
import fr.esnl.projetjerroro.Service.UtilsService;
import fr.esnl.projetjerroro.ui.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.Instant;


/**
 * A placeholder fragment containing a simple view.
 */
public class ValidateProjectFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_PROJECT = "section_project";
    private static final String ARG_SECTION_PROJECT_ID = "section_project_ID";


    private ValidateViewModel pageViewModel;

    private Project project;

    private TextView title;

    private RadioGroup radioGroup;

    private RadioButton valid, refuse;

    private EditText comment;

    private Button button;


    public static ValidateProjectFragment newInstance(int index, Project project) {
        ValidateProjectFragment fragment = new ValidateProjectFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putString(ARG_SECTION_PROJECT, project.toString());
        bundle.putInt(ARG_SECTION_PROJECT_ID, project.getID());
        fragment.setArguments(bundle);
        return fragment;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(ValidateViewModel.class);
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
        View root = inflater.inflate(R.layout.fragment_valid_project, container, false);

        title = root.findViewById(R.id.validTitle);
        radioGroup = root.findViewById(R.id.radioGroup);
        valid = root.findViewById(R.id.radioValid);
        refuse = root.findViewById(R.id.radioRefuse);
        comment = root.findViewById(R.id.commentaire);
        button = root.findViewById(R.id.button2);

        radioGroup.check(valid.getId());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.radioValid && comment.getError() != null) {
                    comment.setError(null);
                }
            }

        });

        pageViewModel.getmProject().observe(this, new Observer<Project>() {
            @Override
            public void onChanged(@Nullable Project s) {
                project = s;
                title.setText(getResources().getString(R.string.Validate) + " " + project.getTitle());
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if (dataIsOk()) {
                    project.setStatusValidation(getStatus());
                    project.setCommentValidation(comment.getText().toString());
                    project.setValidationDate(Instant.now());
                    project.setValidator(((Global) getActivity().getApplication()).getCurrentUser());
                    Result<Project> projectResult = ProjectService.valide(project);
                    if (projectResult instanceof Result.Success) {
                        updateUiWithUser();
                        getActivity().setResult(Activity.RESULT_OK);
                        //Complete and destroy login activity once successful
                        getActivity().finish();
                    }else {
                        showLoginFailed(((Result.Error) projectResult).getError().getMessage());
                    }
                }
            }
        });
        return root;
    }

    private StatusValidationType getStatus() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radioValid:
                return StatusValidationType.VALID;
            case R.id.radioRefuse:
                return StatusValidationType.REFUSE;
            default:
                return StatusValidationType.WAIT;
        }
    }

    private boolean dataIsOk() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        boolean valid = true;
        if (selectedId == refuse.getId() && !hasValue(comment.getText())) {
            valid = false;
            comment.setError(getResources().getString(R.string.fieldRequired));
        }
        return valid;
    }

    private boolean hasValue(Editable text) {
        return  text != null && text.toString().length() > 0;
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.projectUpdate) + " (" + project.getTitle() +")";
        // TODO : initiate successful logged in experience
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }
}