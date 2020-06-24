package fr.esnl.projetjerroro.ui.projectPage.ui.main;

import android.content.Context;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fr.esnl.projetjerroro.Domain.Enum.ProjectStatusType;
import fr.esnl.projetjerroro.Domain.Project;
import fr.esnl.projetjerroro.Domain.User;
import fr.esnl.projetjerroro.R;
import fr.esnl.projetjerroro.Service.UtilsService;
import fr.esnl.projetjerroro.ui.projectPage.ui.main.edit.EditProjectFragment;
import fr.esnl.projetjerroro.ui.projectPage.ui.main.valid.ValidateProjectFragment;


/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private Project project;
    private User user;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SectionsPagerAdapter(Context context, Project project, User user, FragmentManager fm) {
        super(fm);
        mContext = context;
        this.project = project;
        this.user = user;
        if (!project.getStatus().equals(ProjectStatusType.VALIDATION)) {
            TAB_TITLES = new int[]{R.string.tab_text_1};
        } else if (!UtilsService.containsName(user.getRoles(),"CLIENT")) {
            TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_3};
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0 :
                return DetailProjectFragment.newInstance(position + 1, project);
            case 1:
            default:
                if (!UtilsService.containsName(user.getRoles(),"CLIENT")) {
                    return ValidateProjectFragment.newInstance(position + 1, project);
                }else {
                    return EditProjectFragment.newInstance(position + 1, project);
                }
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public int getCount() {
        // Show 2 total pages.
        if (!project.getStatus().equals(ProjectStatusType.VALIDATION)) {
            return 1;
        } else {
            return 2;
        }
    }
}