package com.example.projetjerroro.ui.projectPage;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.Domain.User;
import com.example.projetjerroro.Service.ProjectService;

import java.util.ArrayList;
import java.util.List;

public class ProjectPageViewModel extends ViewModel {


    private MutableLiveData<List<Project>> mProjects;

    private User user;

    private Context context;

    public ProjectPageViewModel() {
        mProjects = new MutableLiveData<>();
        mProjects.setValue(new ArrayList<Project>());
    }

    public LiveData<List<Project>> getProjects() {
        return mProjects;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getProjetData() {
        mProjects.setValue(ProjectService.getAll(this.user,getContext()));
    }
}