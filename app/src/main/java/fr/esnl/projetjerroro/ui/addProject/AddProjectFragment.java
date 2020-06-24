package fr.esnl.projetjerroro.ui.addProject;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;
import fr.esnl.projetjerroro.Domain.Enum.StatusValidationType;
import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.ui.utils.Global;

public class AddProjectFragment extends Fragment {

    private AddProjectViewModel addProjectViewModel;

    private EditText title;

    private EditText description;

    private EditText amount;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addProjectViewModel =
                ViewModelProviders.of(this).get(AddProjectViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_project, container, false);
        addProjectViewModel.getCreateResult().observe(getViewLifecycleOwner(), new Observer<CreateResult>() {
            @Override
            public void onChanged(@Nullable CreateResult s) {
                if (s == null) {
                    return;
                }
                if (s.getError() != null) {
                    showLoginFailed(s.getError());
                }
                if (s.getSuccess() != null) {
                    updateUiWithUser(s.getSuccess());
                    title.setText("");
                    description.setText("");
                    amount.setText("");
                }
            }
        });

        title = root.findViewById(R.id.title_edit);
        description = root.findViewById(R.id.description_edit);
        amount = root.findViewById(R.id.amount_edit);

        Button button = root.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataIsOk()) {
                    Project project = new Project();
                    project.setTitle(title.getText().toString());
                    project.setDescription(description.getText().toString());
                    project.setAmount(Integer.parseInt(amount.getText().toString()));
                    project.setStatusValidation(StatusValidationType.WAIT);
                    project.setStatus(ProjectStatusType.VALIDATION);
                    project.setInChargeUser(((Global) getActivity().getApplication()).getCurrentUser());
                    addProjectViewModel.create(project);
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

    private void updateUiWithUser(CreateView model) {
        String welcome = getString(R.string.projectcreate) + " (" + model.getDisplayName() +")";
        // TODO : initiate successful logged in experience
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}