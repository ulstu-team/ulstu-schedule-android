package ru.ulstu_team.ulstuschedule.ui.base

import android.app.Fragment
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.ViewStub

import ru.ulstu_team.ulstuschedule.App
import ru.ulstu_team.ulstuschedule.R
import ru.ulstu_team.ulstuschedule.injection.component.DaggerStudentScheduleComponent
import ru.ulstu_team.ulstuschedule.injection.component.DaggerTeacherScheduleComponent
import ru.ulstu_team.ulstuschedule.injection.component.StudentScheduleComponent
import ru.ulstu_team.ulstuschedule.injection.component.TeacherScheduleComponent
import ru.ulstu_team.ulstuschedule.injection.module.StudentScheduleModule
import ru.ulstu_team.ulstuschedule.injection.module.TeacherScheduleModule
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

abstract class BaseFragment : Fragment() {

    private var teacherScheduleComponent: TeacherScheduleComponent? = null
    private var studentScheduleComponent: StudentScheduleComponent? = null

    fun getTeacherScheduleComponent(): TeacherScheduleComponent {
        if (teacherScheduleComponent == null) {
            teacherScheduleComponent = DaggerTeacherScheduleComponent.builder()
                    .teacherScheduleModule(TeacherScheduleModule(this))
                    .applicationComponent(App[this.activity].component)
                    .build()
        }
        return teacherScheduleComponent!!
    }

    fun getStudentScheduleComponent(): StudentScheduleComponent {
        if (studentScheduleComponent == null) {
            studentScheduleComponent = DaggerStudentScheduleComponent.builder()
                    .studentScheduleModule(StudentScheduleModule(this))
                    .applicationComponent(App[this.activity].component)
                    .build()
        }
        return studentScheduleComponent!!
    }

    override fun onAttach(context: Context) {
        super.onAttach(CalligraphyContextWrapper.wrap(context))
    }
}
