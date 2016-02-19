package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.view.ViewStub
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import ru.ulstu_team.ulstuschedule.ui.common.student.StudentScheduleFragment

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        configureNavigationView()

        contentLayout = R.layout.main_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = getString(R.string.app_name)
        setSupportActionBar(toolbar)

        val fm = fragmentManager
        if (fm.findFragmentByTag(StudentScheduleFragment.TAG) == null) {
            fm.beginTransaction().add(R.id.fragmentContainer,
                    StudentScheduleFragment(),
                    StudentScheduleFragment.TAG).commit()
        }
    }
}
