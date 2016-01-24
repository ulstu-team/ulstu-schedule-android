package ru.ulstu_team.ulstuschedule.ui.main;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.adapters.StickyListTeacherAdapter;
import ru.ulstu_team.ulstuschedule.data.model.Lesson;
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import ulstu.schedule.api.ScheduleReceiver;

public class MainActivity extends BaseActivity
        implements ScheduleReceiver<List<Lesson>> {


    private StickyListHeadersListView slTeacherLessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configureNavigationView();

        slTeacherLessons = (StickyListHeadersListView) findViewById(R.id.slTeacherLessons);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDataReceived(List<Lesson> data) {
        List<Lesson> lessons = data == null ? new ArrayList<Lesson>() : data;

        StickyListTeacherAdapter teacherAdapter = new StickyListTeacherAdapter(this, lessons);
        slTeacherLessons.setAdapter(teacherAdapter);
    }
}
