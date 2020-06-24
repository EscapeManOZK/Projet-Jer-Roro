package fr.esnl.projetjerroro.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;

import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.ProjectService;
import fr.esnl.projetjerroro.Service.Result;

import java.util.Map;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<HomeResult> homeResult;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Bienvenu sur L' A.G.P");
        homeResult = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void setmText(String s) {mText.setValue(s);}


    public LiveData<HomeResult> getHomeResult() { return homeResult; }

    public void getData() {
        Result<Map<ProjectStatusType, Integer>> result = ProjectService.count();

        if (result instanceof Result.Success) {
            Map<ProjectStatusType, Integer> data = ((Result.Success<Map<ProjectStatusType, Integer>>) result).getData();
            homeResult.setValue(new HomeResult(new HomeView(data)));
        } else {
            homeResult.setValue(new HomeResult(R.string.countFail));
        }
    }
}