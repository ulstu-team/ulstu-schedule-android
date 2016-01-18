package ru.ulstu_team.ulstuschedule;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Arrays;

import ru.ulstu_team.ulstuschedule.adapters.StickyListTeacherAdapter;
import ru.ulstu_team.ulstuschedule.adapters.TeacherAdapter;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;
import ulstu.schedule.api.Schedule;
import ulstu.schedule.api.ScheduleReceiver;
import ulstu.schedule.api.UlstuScheduleAPI;
import ulstu.schedule.models.TeacherLessons;
import ulstu.schedule.storage.PrefsKeys;
import ulstu.schedule.storage.PrefsManager;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ScheduleReceiver<TeacherLessons> {

    private HeaderViewManager headerViewManager;
    private StickyListHeadersListView slTeacherLessons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UlstuScheduleAPI.makeRequest(Schedule.TEACHER_LESSONS, 133)
                .setReceiver(this)
                .request();

        slTeacherLessons = (StickyListHeadersListView) findViewById(R.id.slTeacherLessons);

//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        PrefsManager prefsManager = new PrefsManager(this);
        prefsManager.putString(PrefsKeys.USER_NAME, "Новосельцева Н. Н.");

        headerViewManager = new HeaderViewManager(this);
        headerViewManager.start();
        navigationView.addHeaderView(headerViewManager.getNavHeaderView());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_schedule) {
            Toast.makeText(this, getString(R.string.my_schedule), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_favorites) {
            Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDataReceived(TeacherLessons data) {
        StickyListTeacherAdapter teacherAdapter = new StickyListTeacherAdapter(this, Arrays.asList(data.Lessons));
        slTeacherLessons.setAdapter(teacherAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
