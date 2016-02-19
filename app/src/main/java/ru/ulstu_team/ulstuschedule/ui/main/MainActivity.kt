package ru.ulstu_team.ulstuschedule.ui.main

import android.os.Bundle
import android.view.ViewStub
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import ru.ulstu_team.ulstuschedule.ui.common.student.StudentScheduleFragment

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        configureNavigationView()

        val viewStub = findViewById(R.id.vsContent) as ViewStub
        viewStub.layoutResource = R.layout.main_content
        viewStub.inflate()

        val fm = fragmentManager
        if (fm.findFragmentByTag(StudentScheduleFragment.TAG) == null) {
            fm.beginTransaction().add(R.id.fragmentContainer,
                    StudentScheduleFragment(),
                    StudentScheduleFragment.TAG).commit()
        }
    }
}
