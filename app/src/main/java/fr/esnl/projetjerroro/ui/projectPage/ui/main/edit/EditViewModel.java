package fr.esnl.projetjerroro.ui.projectPage.ui.main.edit;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.Service.ProjectService;
import fr.esnl.projetjerroro.Service.Result;

public class EditViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private MutableLiveData<Project> mProject = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "Hello world from section edit: " + input;
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

    public Result<Project> update(Project project) {
        Result<Project> projectResult = ProjectService.update(project);
        return projectResult;
    }
}