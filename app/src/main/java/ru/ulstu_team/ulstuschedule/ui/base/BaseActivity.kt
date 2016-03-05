package ru.ulstu_team.ulstuschedule.ui.base

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.ViewStub
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.toolbar.*
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
import ru.ulstu_team.ulstuschedule.ui.teachers.TeachersActivity
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener {

    private var mActivityComponent: ActivityComponent? = null

    @Inject internal lateinit var headerViewManager: HeaderViewManager
    private var configured: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
    }

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

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_my_schedule ->
                startActivity(Intent(this, MainActivity::class.java))
            R.id.nav_favorites ->
                Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show()
            R.id.nav_faculties ->
                startActivity(Intent(this, FacultiesActivity::class.java))
            R.id.nav_cathedries ->
                startActivity(Intent(this, CathedriesActivity::class.java))
            R.id.nav_groups ->
                startActivity(Intent(this, GroupsActivity::class.java))
            R.id.nav_teachers ->
                startActivity(Intent(this, TeachersActivity::class.java))
            R.id.nav_feedback -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO,
                                Uri.fromParts("mailto", "we.are.mere.team@gmail.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT,
                                "[" + SimpleDateFormat("dd.MM.yyyy").format(Date()) + "] Ваш отзыв:")
                startActivity(Intent.createChooser(emailIntent, "Оставить ваш отзыв..."))
            }
        }
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    protected fun configureNavigationView() {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        navigationView.addHeaderView(headerViewManager.navHeaderView)
        configured = true
    }

    @LayoutRes
    protected var contentLayout: Int = 0
        set(layoutId) {
            if (layoutId == 0) return

            val vsContent = findViewById(R.id.vsContent) as ViewStub
            vsContent.layoutResource = layoutId
            vsContent.inflate()
        }

    @LayoutRes
    protected var toolbarLayout: Int = 0
        set(layoutId) {
            if (layoutId == 0) return

            val vsToolbar = findViewById(R.id.vsToolbar) as ViewStub
            vsToolbar.layoutResource = layoutId
            vsToolbar.inflate()
            toolbar.setTitleTextColor(R.color.colorHeaderText)
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
