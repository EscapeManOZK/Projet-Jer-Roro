package fr.esnl.projetjerroro.ui.projectPage.ui.main;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;
import fr.esnl.projetjerroro.Domain.Enum.StatusValidationType;
import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.Domain.User;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.ProjectService;
import fr.esnl.projetjerroro.Service.Result;
import fr.esnl.projetjerroro.Service.UtilsService;
import fr.esnl.projetjerroro.ui.utils.Global;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailProjectFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_SECTION_PROJECT = "section_project";
    private static final String ARG_SECTION_PROJECT_ID = "section_project_ID";

    private PageViewModel pageViewModel;

    private Project project;

    public static DetailProjectFragment newInstance(int index, Project project) {
        DetailProjectFragment fragment = new DetailProjectFragment();
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
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;
        Project project = new Project();
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            try {
                String arg = getArguments().getString(ARG_SECTION_PROJECT);
                project = UtilsService.getProjectFromJson(new JSONObject(arg));
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
        View root = inflater.inflate(R.layout.fragment_detail_project, container, false);
        final TextView title = root.findViewById(R.id.titleDetail);
        final TextView description = root.findViewById(R.id.descriptionDetail);
        final TextView amount = root.findViewById(R.id.amountDetail);
        final TextView status = root.findViewById(R.id.statusDetail);
        final TextView startDate = root.findViewById(R.id.startDateDetail);
        final TextView endDate = root.findViewById(R.id.endDateDetail);
        final TextView userIC = root.findViewById(R.id.userInCharge);
        final TextView validateur = root.findViewById(R.id.validatorDetail);
        final TextView statusVal = root.findViewById(R.id.statusValDetail);
        final TextView comment = root.findViewById(R.id.commentValDetail);
        final TextView dateVal = root.findViewById(R.id.dateValidationDetail);
        final Button button = root.findViewById(R.id.delete);
        User user = ((Global) getActivity().getApplication()).getCurrentUser();

        pageViewModel.getmProject().observe(this, new Observer<Project>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onChanged(@Nullable Project s) {

                if (!UtilsService.containsName(user.getRoles(),"CLIENT") || !s.getStatus().equals(ProjectStatusType.VALIDATION)) {
                    button.setVisibility(View.INVISIBLE);
                }
                project = s;
                DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.systemDefault());
                title.setText(s.getTitle());
                description.setText(s.getDescription());
                amount.setText(s.getAmount()+"");
                status.setText(getStatus(s.getStatus()));
                if (s.getStartDate() != null) {
                    startDate.setText(DATE_TIME_FORMATTER.format(s.getStartDate()));
                }
                if (s.getEndDate() != null) {
                    endDate.setText(DATE_TIME_FORMATTER.format(s.getEndDate()));
                }
                userIC.setText(s.getInChargeUser().getDisplayName());
                if (s.getValidator() != null) {
                    validateur.setText(s.getValidator().getDisplayName());
                }
                if (s.getValidationDate() != null) {
                    dateVal.setText(DATE_TIME_FORMATTER.format(s.getValidationDate()));
                }
                if (s.getCommentValidation() != null && !s.getCommentValidation().equals("null")) {
                    comment.setText(s.getCommentValidation());
                }
                if (s.getStatusValidation() != null) {
                    statusVal.setText(getStatusVal(s.getStatusValidation()));
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Result<String> result = pageViewModel.delete(project.getID());
                if (result instanceof Result.Success) {
                    updateUiWithUser();
                    getActivity().setResult(Activity.RESULT_OK);
                    //Complete and destroy login activity once successful
                    getActivity().finish();
                }else {
                    showLoginFailed(((Result.Error) result).getError().getMessage());
                }
            }
        });
        return root;
    }

    private String getStatusVal(StatusValidationType statusValidation) {
        switch (statusValidation) {
            case WAIT:
                return getContext().getResources().getString(R.string.WAIT);
            case REFUSE:
                return getContext().getResources().getString(R.string.REFUSE);
            case VALID:
                return getContext().getResources().getString(R.string.VALID);
            default:
                return "";
        }
    }

    private String getStatus(ProjectStatusType status) {
        switch (status) {
            case VALIDATION:
                return getContext().getResources().getString(R.string.VALIDATION);
            case REFUSE:
                return getContext().getResources().getString(R.string.REFUSE);
            case EN_ATTENTE:
                return getContext().getResources().getString(R.string.EN_ATTENTE);
            case EN_COURS:
                return getContext().getResources().getString(R.string.EN_COURS);
            case FINI:
                return getContext().getResources().getString(R.string.FINI);
            default:
                return "";
        }
    }

    private void showLoginFailed(String errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void updateUiWithUser() {
        String welcome = getString(R.string.projectDelete) + " (" + project.getTitle() +")";
        // TODO : initiate successful logged in experience
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }
}