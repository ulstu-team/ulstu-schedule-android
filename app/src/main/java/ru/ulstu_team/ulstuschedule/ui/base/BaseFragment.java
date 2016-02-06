package ru.ulstu_team.ulstuschedule.ui.base;

import android.app.Fragment;
import android.content.Context;

import ru.ulstu_team.ulstuschedule.App;
import ru.ulstu_team.ulstuschedule.injection.component.DaggerStudentScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.component.DaggerTeacherScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.component.StudentScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.component.TeacherScheduleComponent;
import ru.ulstu_team.ulstuschedule.injection.module.StudentScheduleModule;
import ru.ulstu_team.ulstuschedule.injection.module.TeacherScheduleModule;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseFragment extends Fragment {

    private TeacherScheduleComponent teacherScheduleComponent;
    private StudentScheduleComponent studentScheduleComponent;

    public TeacherScheduleComponent getTeacherScheduleComponent() {
        if (teacherScheduleComponent == null) {
            teacherScheduleComponent = DaggerTeacherScheduleComponent.builder()
                    .teacherScheduleModule(new TeacherScheduleModule(this))
                    .applicationComponent(App.get(this.getActivity()).getComponent())
                    .build();
        }
        return teacherScheduleComponent;
    }

    public StudentScheduleComponent getStudentScheduleComponent() {
        if (studentScheduleComponent == null) {
            studentScheduleComponent = DaggerStudentScheduleComponent.builder()
                    .studentScheduleModule(new StudentScheduleModule(this))
                    .applicationComponent(App.get(this.getActivity()).getComponent())
                    .build();
        }
        return studentScheduleComponent;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(CalligraphyContextWrapper.wrap(context));
    }
}
