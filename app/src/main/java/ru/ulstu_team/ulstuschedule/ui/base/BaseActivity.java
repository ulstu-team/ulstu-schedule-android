package ru.ulstu_team.ulstuschedule.ui.base;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import javax.inject.Inject;

import ru.ulstu_team.ulstuschedule.HeaderViewManager;
import ru.ulstu_team.ulstuschedule.R;
import ru.ulstu_team.ulstuschedule.activities.FacultiesActivity;
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Inject HeaderViewManager headerViewManager;

    @Override
    protected void onStart() {
        super.onStart();
        configureNavigationView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        headerViewManager.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        headerViewManager.stop();
    }

    protected void configureNavigationView() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_schedule) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_favorites) {
            Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_faculties) {
            Intent intent = new Intent(this, FacultiesActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
