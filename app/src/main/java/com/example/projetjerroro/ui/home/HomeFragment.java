package com.example.projetjerroro.ui.home;

import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private Map<ProjectStatusType, Integer> projectStatusTypeIntegerMap = new HashMap<>();

    private PieChart mPieChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        projectStatusTypeIntegerMap.put(ProjectStatusType.VALIDATION, 10);
        projectStatusTypeIntegerMap.put(ProjectStatusType.REFUSE, 2);
        projectStatusTypeIntegerMap.put(ProjectStatusType.EN_ATTENTE, 6);
        projectStatusTypeIntegerMap.put(ProjectStatusType.EN_COURS, 2);
        projectStatusTypeIntegerMap.put(ProjectStatusType.FINI, 100);

        for (ProjectStatusType statusType : ProjectStatusType.values()) {
            new PieEntry(projectStatusTypeIntegerMap.get(statusType), statusType);
        }

        mPieChart = root.findViewById(R.id.barChart);

        mPieChart.setVisibility(View.VISIBLE);
        mPieChart.animateXY(2000,2000);
        List<PieEntry>pieEntries = new ArrayList<>();
        for (ProjectStatusType type : ProjectStatusType.values()) {
            pieEntries.add(new PieEntry(projectStatusTypeIntegerMap.get(type), getStatus(type)));
        }
        PieDataSet pieDataSet=new PieDataSet(pieEntries,"Project Data");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        mPieChart.setData(new PieData(pieDataSet));




        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    private String getStatus(ProjectStatusType type) {
        switch (type) {
            case VALIDATION:
                return getResources().getString(R.string.VALIDATION);
            case REFUSE:
                return getResources().getString(R.string.REFUSE);
            case EN_ATTENTE:
                return getResources().getString(R.string.EN_ATTENTE);
            case EN_COURS:
                return getResources().getString(R.string.EN_COURS);
            case FINI:
                return getResources().getString(R.string.FINI);

            default:
                return type.toString();


        }
    }


}