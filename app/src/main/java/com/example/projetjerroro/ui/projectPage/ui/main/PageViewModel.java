package com.example.projetjerroro.ui.projectPage.ui.main;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Service.ProjectService;
import com.example.projetjerroro.Service.Result;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<Project> mProject = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section: " + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Project> getmProject() {
        return mProject;
    }

    public void setmProject(Project project) {
        this.mProject.setValue(project);
    }

    public Result<String> delete(int id) {
        return ProjectService.delete(id);
    }
}