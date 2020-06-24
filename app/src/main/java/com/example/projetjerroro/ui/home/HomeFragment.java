package com.example.projetjerroro.ui.home;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.projetjerroro.Domain.Enum.ProjectStatusType;
import com.example.projetjerroro.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private PieChart mPieChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPieChart = root.findViewById(R.id.barChart);

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        homeViewModel.getHomeResult().observe(getViewLifecycleOwner(), new Observer<HomeResult>() {
            @Override
            public void onChanged(@Nullable HomeResult s) {
                if (s == null) {
                    return;
                }
                if (s.getError() != null) {
                    showLoginFailed(s.getError());
                }
                if (s.getSuccess() != null) {
                    updateUiWithUser(s.getSuccess());
                    mPieChart.setVisibility(View.VISIBLE);
                    mPieChart.animateXY(2000,2000);
                    List<PieEntry>pieEntries = new ArrayList<>();
                    for (ProjectStatusType type : ProjectStatusType.values()) {
                        if (s.getSuccess().getProject().containsKey(type)) {
                            pieEntries.add(new PieEntry(s.getSuccess().getProject().get(type), getStatus(type)));
                        }
                    }
                    PieDataSet pieDataSet=new PieDataSet(pieEntries,getResources().getString(R.string.projectData));
                    pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    mPieChart.setData(new PieData(pieDataSet));
                }
            }
        });

        return root;
    }

    private String getStatus(ProjectStatusType type) {
        switch (type) {
            case VALIDATION:
                return getResources().getString(R.string.VALIDATION_HOME);
            case REFUSE:
                return getResources().getString(R.string.REFUSE_HOME);
            case EN_ATTENTE:
                return getResources().getString(R.string.EN_ATTENTE_HOME);
            case EN_COURS:
                return getResources().getString(R.string.EN_COURS_HOME);
            case FINI:
                return getResources().getString(R.string.FINI_HOME);

            default:
                return type.toString();


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {

        super.onResume();
        homeViewModel.getData();

    }

    private void updateUiWithUser(HomeView model) {
        String welcome = getString(R.string.getProjectValueComplete);
        // TODO : initiate successful logged in experience
        Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}