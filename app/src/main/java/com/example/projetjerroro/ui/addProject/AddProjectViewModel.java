package com.example.projetjerroro.ui.addProject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.R;
import com.example.projetjerroro.Service.ProjectService;
import com.example.projetjerroro.Service.Result;

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