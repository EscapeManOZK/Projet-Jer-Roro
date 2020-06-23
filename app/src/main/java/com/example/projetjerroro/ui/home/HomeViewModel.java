package com.example.projetjerroro.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;

import java.util.HashMap;
import java.util.Map;


public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Map<ProjectStatusType, Integer>> mutableLiveData;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mutableLiveData = new MutableLiveData<>();
        mText.setValue("Bienvenu sur L' A.G.P");
        mutableLiveData.setValue(new HashMap<>());
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Map<ProjectStatusType, Integer>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getData() {
        Map<ProjectStatusType, Integer> projectStatusTypeIntegerMap = new HashMap<>();

        projectStatusTypeIntegerMap.put(ProjectStatusType.VALIDATION, 10);
        projectStatusTypeIntegerMap.put(ProjectStatusType.REFUSE, 2);
        projectStatusTypeIntegerMap.put(ProjectStatusType.EN_ATTENTE, 6);
        projectStatusTypeIntegerMap.put(ProjectStatusType.EN_COURS, 2);
        projectStatusTypeIntegerMap.put(ProjectStatusType.FINI, 100);

        this.mutableLiveData.setValue(projectStatusTypeIntegerMap);
    }
}