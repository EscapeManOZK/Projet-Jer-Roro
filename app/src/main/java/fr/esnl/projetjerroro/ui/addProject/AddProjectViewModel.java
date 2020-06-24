package fr.esnl.projetjerroro.ui.addProject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.ProjectService;
import fr.esnl.projetjerroro.Service.Result;

public class AddProjectViewModel extends ViewModel {

    private MutableLiveData<CreateResult> createResult = new MutableLiveData<>();

    public AddProjectViewModel() {
        createResult = new MutableLiveData<>();

    }

    public LiveData<CreateResult> getCreateResult () { return createResult; }

    public void create (Project project) {
        Result<Project> result = ProjectService.create(project);

        if (result instanceof Result.Success) {
            Project data = ((Result.Success<Project>) result).getData();
            createResult.setValue(new CreateResult(new CreateView(data.getTitle(), data)));
        } else {
            createResult.setValue(new CreateResult(R.string.createfail));
        }
    }
}