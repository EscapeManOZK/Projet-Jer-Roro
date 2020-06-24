package fr.esnl.projetjerroro.ui.home;

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

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;
import fr.esnl.projetjerroro.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private PieChart mPieChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mPieChart = root.findViewById(R.id.barChart);

        mPieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String welcome = getString(R.string.il) + " " + (int) e.getY() + " " + getString(R.string.projet) + "  \"" + getStatus(getStatusFromPos((int)h.getX())).toLowerCase() + "\"";
                // TODO : initiate successful logged in experience
                Toast.makeText(getContext(), welcome, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });

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
                    mPieChart.setEntryLabelColor(R.color.black);
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

    private ProjectStatusType getStatusFromPos(int pos) {
        switch (pos) {
            case 0:
                return ProjectStatusType.VALIDATION;
            case 1:
                return ProjectStatusType.EN_ATTENTE;
            case 2:
                return ProjectStatusType.EN_COURS;
            case 3:
                return ProjectStatusType.FINI;
            case 4:
                return ProjectStatusType.REFUSE;
            default:
                return null;


        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {

        super.onResume();
        homeViewModel.setmText(getString(R.string.welcomeOnAGP));
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