package ru.ulstu_team.ulstuschedule.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.adapters.FacultiesAdapter;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;
import ulstu.schedule.api.Schedule;
import ulstu.schedule.api.ScheduleReceiver;
import ulstu.schedule.api.UlstuScheduleAPI;
import ru.ulstu_team.ulstuschedule.data.model.Faculty;

public class FacultiesActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScheduleReceiver<List<Faculty>> {

    private RecyclerView rvFaculties;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);
        configureNavigationView();

        rvFaculties = (RecyclerView) findViewById(R.id.rvFaculties);
        rvFaculties.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }

    @Nullable
    @Override
    public void onDataReceived(List<Faculty> data) {
        data = data != null ? data : new ArrayList<Faculty>();

        FacultiesAdapter adapter = new FacultiesAdapter(data);
        rvFaculties.setAdapter(adapter);
    }
}
