package com.example.projetjerroro.ui.projectPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projetjerroro.Domain.Project;
import com.example.projetjerroro.R;

import java.util.List;

public class ProjectItemAdapter extends BaseAdapter {

    private Context context;
    private List<Project> projects;
    private LayoutInflater inflater;

    public  ProjectItemAdapter (Context context, List<Project> projects) {
        this.context = context;
        this.projects = projects;
        this.inflater = LayoutInflater.from(context);
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @Override
    public int getCount() {
        return this.projects.size();
    }

    @Override
    public Project getItem(int position) {
        return this.projects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.projects.get(position).getID();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.adapter_project_item, null);

        Project project = getItem(position);

        String fileName = "item_" + project.getStatus().toString().toLowerCase() + "_icon";

        ImageView imageView = convertView.findViewById(R.id.ProjectStatusIcon);
        int imgId = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
        imageView.setImageResource(imgId);

        TextView textViewTitle = convertView.findViewById(R.id.ProjectTitle);
        textViewTitle.setText(project.getTitle());

        TextView textViewClient = convertView.findViewById(R.id.ProjectCreator);
        textViewClient.setText( this.context.getResources().getString(R.string.par) + " " + project.getInChargeUser().getFirstName() + " " + project.getInChargeUser().getLastName().toUpperCase());

        TextView textViewAmount = convertView.findViewById(R.id.ProjectAmount);
        textViewAmount.setText( this.context.getResources().getString(R.string.pour) + " " + project.getAmount() + " K€");

        return convertView;
    }
}
