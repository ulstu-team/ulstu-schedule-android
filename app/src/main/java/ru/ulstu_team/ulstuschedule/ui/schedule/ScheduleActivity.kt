package ru.ulstu_team.ulstuschedule.ui.schedule



import android.app.Fragment
import android.os.Bundle
import kotlinx.android.synthetic.main.toolbar.*
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.ui.base.BaseActivity
import ru.ulstu_team.ulstuschedule.ui.common.group.GroupScheduleFragment
import ru.ulstu_team.ulstuschedule.ui.common.teacher.TeacherScheduleFragment

class ScheduleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_layout)
        configureNavigationView()

        val owner = intent.getStringExtra("owner")
        val ownerName = intent.getStringExtra("ownerName")
        val id = intent.getIntExtra("id", 0)
        val fragment: Fragment? = when(owner){
            "teacher" -> TeacherScheduleFragment(id)
            "group" -> GroupScheduleFragment(id)
            else -> null
        }

        contentLayout = R.layout.main_content
        toolbarLayout = R.layout.toolbar
        toolbar.title = ownerName
        setSupportActionBar(toolbar)

        val fragmentTag = "ScheduleActivity"
        val fm = fragmentManager
        if (fm.findFragmentByTag(fragmentTag) == null) {
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, fragment, fragmentTag)
                    .commit()
        }
    }
}
