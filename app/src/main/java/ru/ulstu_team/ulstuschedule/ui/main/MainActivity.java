package ru.ulstu_team.ulstuschedule.ui.main;

import android.app.FragmentManager;
import android.os.Bundle;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;
import ru.ulstu_team.ulstuschedule.ui.common.TeacherScheduleFragment;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_main);
        configureNavigationView();

        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(TeacherScheduleFragment.TAG) == null) {

            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer,
                            new TeacherScheduleFragment(),
                            TeacherScheduleFragment.TAG)
                    .commit();
        }
    }
}
