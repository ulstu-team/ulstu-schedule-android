package ru.ulstu_team.ulstuschedule.ui.base

import android.content.Context
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import ru.ulstu_team.ulstuschedule.App
import ru.ulstu_team.ulstuschedule.HeaderViewManager
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.injection.component.ActivityComponent
import ru.ulstu_team.ulstuschedule.injection.component.DaggerActivityComponent
import ru.ulstu_team.ulstuschedule.injection.module.ActivityModule
import ru.ulstu_team.ulstuschedule.ui.cathedries.CathedriesActivity
import ru.ulstu_team.ulstuschedule.ui.faculties.FacultiesActivity
import ru.ulstu_team.ulstuschedule.ui.groups.GroupsActivity
import ru.ulstu_team.ulstuschedule.ui.main.MainActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    private var mActivityComponent: ActivityComponent? = null

    @Inject internal lateinit var headerViewManager: HeaderViewManager
    private var configured: Boolean = false

    override fun onStart() {
        super.onStart()
        if (!configured)
            configureNavigationView()
    }

    override fun onResume() {
        super.onResume()
        headerViewManager.start()
    }

    override fun onPause() {
        super.onPause()
        headerViewManager.stop()
    }

    protected fun configureNavigationView() {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.addHeaderView(headerViewManager.navHeaderView)
        configured = true
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_my_schedule) {
            startActivity(Intent(this, MainActivity::class.java))
        } else if (id == R.id.nav_favorites) {
            Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show()
        } else if (id == R.id.nav_faculties) {
            startActivity(Intent(this, FacultiesActivity::class.java))
        } else if (id == R.id.nav_cathedries) {
            startActivity(Intent(this, CathedriesActivity::class.java))
        } else if (id == R.id.nav_groups) {
            startActivity(Intent(this, GroupsActivity::class.java))
        }

        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    val activityComponent: ActivityComponent
        get() {
            if (mActivityComponent == null) {
                mActivityComponent = DaggerActivityComponent.builder()
                        .activityModule(ActivityModule(this))
                        .applicationComponent(App[this].component)
                        .build()
            }
            return mActivityComponent!!
        }
}
